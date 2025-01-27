package sep490.idp.repository;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import sep490.idp.entity.BuildingEntity;

import java.util.List;
import java.util.UUID;

public interface BuildingRepository extends AbstractBaseRepository<BuildingEntity> {
    
    boolean existsAllByIdIn(List<UUID> ids);
    
}
