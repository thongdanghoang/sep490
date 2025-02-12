package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.BuildingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BuildingRepository extends AbstractBaseRepository<BuildingEntity> {
    
    Page<BuildingEntity> findByEnterpriseId(UUID enterpriseId, Pageable pageable);
    
}
