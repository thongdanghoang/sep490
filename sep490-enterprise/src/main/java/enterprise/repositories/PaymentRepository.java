package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface PaymentRepository extends AbstractBaseRepository<PaymentEntity> {
    
    Page<PaymentEntity> findByEnterpriseId(UUID enterpriseId, Pageable pageable);
}
