package enterprise.services;

import enterprise.entities.BuildingEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface BuildingService {
    
    BuildingEntity createBuilding(BuildingEntity building);
    
    Page<BuildingEntity> getEnterpriseBuildings(UUID enterpriseId, Pageable page);
}
