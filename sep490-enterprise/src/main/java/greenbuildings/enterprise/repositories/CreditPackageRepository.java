package greenbuildings.enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import greenbuildings.enterprise.entities.CreditPackageEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CreditPackageRepository extends AbstractBaseRepository<CreditPackageEntity> {
    
    List<CreditPackageEntity> findAllByOrderByNumberOfCreditsAsc();
    
}
