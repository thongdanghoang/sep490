package enterprise.services.impl;

import enterprise.entities.BuildingEntity;
import enterprise.repositories.BuildingRepository;
import enterprise.services.BuildingService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional(rollbackOn = Throwable.class)
@RequiredArgsConstructor
public class BuildingServiceImpl implements BuildingService {
    
    private final BuildingRepository buildingRepository;
    
    @Override
    public BuildingEntity createBuilding(BuildingEntity building) {
        return buildingRepository.save(building);
    }
    
    @Override
    public Page<BuildingEntity> getEnterpriseBuildings(UUID enterpriseId, Pageable page) {
        return buildingRepository.findByEnterpriseId(enterpriseId, page);
    }
}
