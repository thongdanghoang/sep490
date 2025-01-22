package sep490.idp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import sep490.idp.entity.BuildingEntity;

import java.util.List;
import java.util.UUID;

public interface BuildingRepository extends JpaRepository<BuildingEntity, UUID> {
    
    boolean existsAllByIdIn(List<UUID> ids);
    
}
