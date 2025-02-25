package enterprise.repositories;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import enterprise.entities.BuildingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface BuildingRepository extends AbstractBaseRepository<BuildingEntity> {
    
    Page<BuildingEntity> findByEnterpriseId(UUID enterpriseId, Pageable pageable);
    
    List<BuildingEntity> findAllByEnterpriseId(UUID enterpriseId);
    
}
