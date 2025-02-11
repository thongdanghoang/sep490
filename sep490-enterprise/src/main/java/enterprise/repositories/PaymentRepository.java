package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface PaymentRepository extends AbstractBaseRepository<PaymentEntity> {
    
    @Query("""
            SELECT p
            FROM PaymentEntity p
            """
    )
    Page<PaymentEntity> findByName(String name, Pageable pageable);
}
