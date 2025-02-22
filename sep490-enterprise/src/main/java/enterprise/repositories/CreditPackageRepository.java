package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.CreditPackageEntity;
import enterprise.entities.PaymentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CreditPackageRepository extends AbstractBaseRepository<CreditPackageEntity> {
    
    List<CreditPackageEntity> findAllByOrderByNumberOfCreditsAsc();
    
}
