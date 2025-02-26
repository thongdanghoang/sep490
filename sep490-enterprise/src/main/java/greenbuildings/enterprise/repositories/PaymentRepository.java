package greenbuildings.enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import greenbuildings.enterprise.entities.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;


public interface PaymentRepository extends AbstractBaseRepository<PaymentEntity> {
    
    Page<PaymentEntity> findByEnterpriseId(UUID enterpriseId, Pageable pageable);
    
    Optional<PaymentEntity> findByOrderCodeAndEnterpriseId(Long orderCode, UUID enterpriseId);
}
