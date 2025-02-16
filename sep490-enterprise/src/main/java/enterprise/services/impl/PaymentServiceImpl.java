package enterprise.services.impl;

import commons.springfw.impl.utils.SecurityUtils;
import enterprise.dtos.CreditPurchaseDTO;
import enterprise.dtos.PaymentCriteriaDTO;
import enterprise.entities.PaymentEntity;
import enterprise.repositories.PaymentRepository;
import enterprise.services.PaymentService;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import green_buildings.commons.api.exceptions.TechnicalException;
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

import java.util.UUID;

@Service
@Slf4j
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository payRepo;
    private final PayOS payOS;
    
    @Value("${payment.payos.returnPath}")
    private String returnPath;
    
    @Value("${payment.payos.cancelPath}")
    private String cancelPath;
    
    @Value("${spring.application.homepage}")
    private String homepage;
    
    private String returnUrl;
    private String cancelUrl;
    
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
    
    public CheckoutResponseData getCheckoutData(CreditPurchaseDTO creditPurchaseItem) {
        try {
            log.info("Creating payment link for item: {}", creditPurchaseItem);
            PaymentData paymentData = createPaymentData(creditPurchaseItem);
            log.debug("Generated PaymentData: {}", paymentData);
            return payOS.createPaymentLink(paymentData);
        } catch (Exception ex) {
            log.error("Error creating payment link for item: {}", creditPurchaseItem);
            throw new TechnicalException("Error creating payment link for item", ex);
        }
    }
    
    private PaymentData createPaymentData(CreditPurchaseDTO creditPurchaseItem) {
        ItemData itemData = createItemData(creditPurchaseItem);
        return PaymentData.builder()
                          .orderCode(creditPurchaseItem.code())
                          .amount(creditPurchaseItem.price())
                          .item(itemData)
                          .returnUrl(returnUrl)
                          .cancelUrl(cancelUrl)
                          .build();
    }
    
    private ItemData createItemData(CreditPurchaseDTO creditPurchaseItem) {
        return ItemData.builder()
                       .name("Credit Purchase")
                       .quantity(creditPurchaseItem.amount())
                       .price(creditPurchaseItem.price())
                       .build();
    }
}
