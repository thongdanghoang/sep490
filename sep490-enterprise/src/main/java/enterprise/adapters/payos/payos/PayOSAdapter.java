package enterprise.adapters.payos.payos;

import enterprise.entities.CreditPackageEntity;
import enterprise.entities.EnterpriseEntity;
import enterprise.entities.PaymentEntity;
import green_buildings.commons.api.enums.PaymentStatus;

import java.util.Optional;

public interface PayOSAdapter {
    
    PaymentEntity newPayment(CreditPackageEntity creditPackageEntity, EnterpriseEntity enterpriseEntity);
    
    Optional<PaymentStatus> getPaymentStatus(Long orderCode);
}
