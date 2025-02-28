package greenbuildings.enterprise.services.impl;

import commons.springfw.impl.utils.SecurityUtils;
import greenbuildings.enterprise.dtos.CreditConvertRatioDTO;
import greenbuildings.enterprise.dtos.SubscribeRequestDTO;
import greenbuildings.enterprise.entities.BuildingEntity;
import greenbuildings.enterprise.entities.CreditConvertRatioEntity;
import greenbuildings.enterprise.entities.CreditConvertType;
import greenbuildings.enterprise.entities.SubscriptionEntity;
import greenbuildings.enterprise.entities.TransactionEntity;
import greenbuildings.enterprise.entities.TransactionType;
import greenbuildings.enterprise.entities.WalletEntity;
import greenbuildings.enterprise.repositories.BuildingRepository;
import greenbuildings.enterprise.repositories.CreditConvertRatioRepository;
import greenbuildings.enterprise.repositories.SubscriptionRepository;
import greenbuildings.enterprise.repositories.WalletRepository;
import greenbuildings.enterprise.services.SubscriptionService;
import greenbuildings.commons.api.exceptions.BusinessException;
import greenbuildings.commons.api.exceptions.TechnicalException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
@Slf4j
public class SubscriptionServiceImpl implements SubscriptionService {
    
    private final CreditConvertRatioRepository creditConvertRatioRepository;
    private final BuildingRepository buildingRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final WalletRepository walletRepository;
    
    @Override
    public List<CreditConvertRatioEntity> getCreditConvertRatios() {
        return creditConvertRatioRepository.findAll();
    }
    
    @Override
    public void subscribe(SubscribeRequestDTO request) {
        // Prepare building
        UUID enterpriseId = SecurityUtils.getCurrentUserEnterpriseId().orElseThrow();
        BuildingEntity buildingEntity = buildingRepository.findByIdAndEnterpriseId(request.buildingId(), enterpriseId).orElseThrow();
        
        // Prepare Transaction
        double amount = calculateTransactionAmount(request);
        TransactionEntity transaction = createNewTransaction(request, buildingEntity, amount);
        
        // Handel wallet
        WalletEntity walletEntity = walletRepository.findByEnterpriseId(enterpriseId);
        try {
            walletEntity.withdraw((long) transaction.getAmount());
        } catch (IllegalArgumentException e) {
            throw new BusinessException(null, "You have not enough credit");
        }
        
        // Handel Subscription
        SubscriptionEntity subscription = null;
        if (request.type().equals(TransactionType.NEW_PURCHASE)) {
            subscription = createNewSubscription(transaction, buildingEntity, request);
        } else if (request.type().equals(TransactionType.UPGRADE)) {
            subscription = upgradeSubscription(transaction, request);
        } else {
            throw new TechnicalException("Unknown transaction type: " + request.type());
        }
        
        walletRepository.save(walletEntity);
        subscriptionRepository.save(subscription);
    }
    
    private static TransactionEntity createNewTransaction(SubscribeRequestDTO request, BuildingEntity buildingEntity, double amount) {
        TransactionEntity transaction = new TransactionEntity();
        transaction.setBuilding(buildingEntity);
        transaction.setEnterprise(buildingEntity.getEnterprise());
        transaction.setTransactionType(request.type());
        transaction.setAmount(amount);
        return transaction;
    }
    
    private SubscriptionEntity createNewSubscription(TransactionEntity transaction, BuildingEntity buildingEntity, SubscribeRequestDTO request) {
        SubscriptionEntity subscription = new SubscriptionEntity();
        subscription.setBuilding(buildingEntity);
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(LocalDate.now().plusMonths(request.months()));
        subscription.setMaxNumberOfDevices(request.numberOfDevices());
        subscription.addTransaction(transaction);
        
        return subscription;
    }
    
    private SubscriptionEntity upgradeSubscription(TransactionEntity transaction, SubscribeRequestDTO request) {
        List<SubscriptionEntity> allValidSubscriptions = subscriptionRepository.findAllValidSubscriptions(LocalDate.now());
        if (allValidSubscriptions.isEmpty()) {
            throw new BusinessException(null, "You do not have any valid subscriptions");
        } else if (allValidSubscriptions.size() > 1) {
            log.warn("Building {} has more than one valid subscriptions", request.buildingId());
        }
        SubscriptionEntity subscription = allValidSubscriptions.getFirst();
        subscription.setEndDate(subscription.getEndDate().plusMonths(request.months()));
        subscription.addTransaction(transaction);
        return subscription;
    }
    
    private double calculateTransactionAmount(SubscribeRequestDTO request) {
        List<CreditConvertRatioEntity> creditConvertRatios = getCreditConvertRatios();
        CreditConvertRatioEntity monthRatio = creditConvertRatios
                .stream()
                .filter(x -> x.getConvertType().equals(CreditConvertType.MONTH))
                .findFirst()
                .orElseThrow();
        CreditConvertRatioEntity noDevicesRatio = creditConvertRatios
                .stream()
                .filter(x -> x.getConvertType().equals(CreditConvertType.DEVICE))
                .findFirst()
                .orElseThrow();
        
        if (request.monthRatio() != monthRatio.getRatio() || request.deviceRatio() != noDevicesRatio.getRatio()) {
            throw new BusinessException("", "validation.business.buildings.notEnoughCredit");
        }
        return (monthRatio.getRatio() * request.months())
               * (noDevicesRatio.getRatio() * request.numberOfDevices());
    }

    @Override
    public CreditConvertRatioEntity getCreditConvertRatioDetail(UUID id) {
        return creditConvertRatioRepository.findById(id).orElseThrow();
    }

    public void updateCreditConvertRatio(CreditConvertRatioDTO creditConvertRatioDTO) {
           var creditConvertRatioEntity = getCreditConvertRatioDetail(creditConvertRatioDTO.id());
        creditConvertRatioEntity.setRatio(creditConvertRatioDTO.ratio());
        creditConvertRatioRepository.save(creditConvertRatioEntity);
    }
}
