package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.CreditPackageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditPackageRepository extends AbstractBaseRepository<CreditPackageEntity> {
    
    List<CreditPackageEntity> findAllByOrderByNumberOfCreditsAsc();
    
}
