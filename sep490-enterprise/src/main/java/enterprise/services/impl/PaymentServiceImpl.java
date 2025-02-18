package enterprise.services.impl;

import commons.springfw.impl.utils.SecurityUtils;
import enterprise.dtos.PaymentCriteriaDTO;
import enterprise.entities.CreditPackageEntity;
import enterprise.entities.EnterpriseEntity;
import enterprise.entities.PaymentEntity;
import enterprise.entities.WalletEntity;
import enterprise.mappers.PaymentMapper;
import enterprise.repositories.CreditPackageRepository;
import enterprise.repositories.EnterpriseRepository;
import enterprise.repositories.PaymentRepository;
import enterprise.repositories.WalletRepository;
import enterprise.services.PaymentService;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import green_buildings.commons.api.enums.PaymentStatus;
import green_buildings.commons.api.exceptions.TechnicalException;
import green_buildings.commons.api.utils.EnumUtil;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;
import vn.payos.PayOS;
import vn.payos.type.CheckoutResponseData;
import vn.payos.type.ItemData;
import vn.payos.type.PaymentData;
import vn.payos.type.PaymentLinkData;

import java.util.UUID;

@Service
@Slf4j
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository payRepo;
    private final CreditPackageRepository packageRepo;
    private final EnterpriseRepository enterpriseRepo;
    private final WalletRepository walletRepository;
    private final PaymentMapper mapper;
    private final PayOS payOS;
    
    @Value("${payment.payos.returnPath}")
    private String returnPath;
    
    @Value("${payment.payos.cancelPath}")
    private String cancelPath;
    
    @Value("${spring.application.homepage}")
    private String homepage;
    
    private String returnUrl;
    private String cancelUrl;
    
    public static final String CREDIT_ITEM = "Credit";
    
    @PostConstruct
    public void setUrls() {
        returnUrl = UriComponentsBuilder.fromUriString(homepage)
                                        .path(returnPath)
                                        .build()
                                        .toUriString();
        cancelUrl = UriComponentsBuilder.fromUriString(homepage)
                                        .path(cancelPath)
                                        .build()
                                        .toUriString();
    }
    
    @Override
    public Page<PaymentEntity> search(SearchCriteriaDTO<PaymentCriteriaDTO> searchCriteria, Pageable pageable) {
        UUID enterpriseId = SecurityUtils.getCurrentUserEnterpriseId().orElseThrow();
        return payRepo.findByEnterpriseId(enterpriseId, pageable);
    }
    
    @Override
    public PaymentEntity createPayment(UUID creditPackageUUID) {
        try {
            // Prepare
            CreditPackageEntity creditPackageEntity = packageRepo.findById(creditPackageUUID).orElseThrow();
            UUID enterpriseUUID = SecurityUtils.getCurrentUserEnterpriseId().orElseThrow();
            EnterpriseEntity enterpriseEntity = enterpriseRepo.findById(enterpriseUUID).orElseThrow();
            
            // Build info
            long orderCode = System.currentTimeMillis();
            
            ItemData itemData = buildItemData(creditPackageEntity);
            PaymentData paymentData = getPaymentData(orderCode, creditPackageEntity, enterpriseEntity, itemData);
            
            // Perform PayOS API
            CheckoutResponseData payOSResult = payOS.createPaymentLink(paymentData);
            
            // Store to DB
            PaymentEntity paymentEntity = preparePaymentEntity(enterpriseEntity, payOSResult, creditPackageEntity);
            
            return payRepo.save(paymentEntity);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new TechnicalException("Failed to create payment link", e);
        }
    }
    
    private PaymentEntity preparePaymentEntity(EnterpriseEntity enterpriseEntity, CheckoutResponseData payOSResult,
                                                  CreditPackageEntity creditPackageEntity) {
        PaymentEntity paymentEntity = new PaymentEntity();
        paymentEntity.setEnterprise(enterpriseEntity);
        paymentEntity.setStatus(PaymentStatus.PENDING);
        paymentEntity.setAmount(payOSResult.getAmount());
        paymentEntity.setNumberOfCredits(creditPackageEntity.getNumberOfCredits());
        mapper.updatePaymentFromCheckoutData(payOSResult, paymentEntity);
        return paymentEntity;
    }
    
    @Override
    public void updatePaymentInfo(Long orderCode) {
        UUID uuid = SecurityUtils.getCurrentUserEnterpriseId().orElseThrow();
        PaymentEntity paymentEntity = payRepo.findByOrderCodeAndEnterpriseId(orderCode, uuid).orElseThrow();
        try {
            PaymentLinkData paymentLinkInformation = payOS.getPaymentLinkInformation(orderCode);
            PaymentStatus newStatus =
                    EnumUtil.getCodeFromString(PaymentStatus.class, paymentLinkInformation.getStatus())
                            .orElseThrow(() -> new TechnicalException("Error in getting payment status from PayOS"));
            updateWallet(newStatus, paymentEntity, uuid);
            paymentEntity.setStatus(newStatus);
            payRepo.save(paymentEntity);
        } catch (TechnicalException e) {
            throw e;
        } catch (Exception e) {
            throw new TechnicalException(e);
        }
    }
    
    private void updateWallet(PaymentStatus newStatus, PaymentEntity paymentEntity, UUID uuid) {
        if (newStatus == PaymentStatus.PAID && paymentEntity.getStatus() != PaymentStatus.PAID) {
            WalletEntity wallet = walletRepository.findByEnterpriseId(uuid);
            wallet.deposit(paymentEntity.getNumberOfCredits());
            walletRepository.save(wallet);
        }
    }
    
    private PaymentData getPaymentData(long orderCode,
                                       CreditPackageEntity creditPackageEntity,
                                       EnterpriseEntity enterpriseEntity,
                                       ItemData itemData) {
        return PaymentData.builder()
                          .orderCode(orderCode)
                          .amount((int) creditPackageEntity.getPrice())
                          .description("Credit purchase") // max 25 chars
                          .buyerName(enterpriseEntity.getName())
                          .buyerEmail(enterpriseEntity.getEmail())
                          .buyerPhone(enterpriseEntity.getHotline())
                          .item(itemData)
                          .returnUrl(returnUrl)
                          .cancelUrl(cancelUrl)
                          .build();
    }
    
    private ItemData buildItemData(CreditPackageEntity creditPackageEntity) {
        return ItemData.builder()
                       .name(CREDIT_ITEM)
                       .quantity(creditPackageEntity.getNumberOfCredits())
                       .price(0)
                       .build();
    }
    
}
