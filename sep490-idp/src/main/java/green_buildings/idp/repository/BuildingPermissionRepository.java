package green_buildings.idp.repository;

import commons.springfw.impl.repositories.AbstractBaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import green_buildings.idp.entity.BuildingPermissionEntity;
import green_buildings.idp.entity.UserEntity;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BuildingPermissionRepository extends AbstractBaseRepository<BuildingPermissionEntity> {
    
    @Query(value = "select bp from BuildingPermissionEntity bp where bp.user.id = :userId")
    List<BuildingPermissionEntity> findAllByUserId(UUID userId);
    
    @Query("""
            select bp
            from BuildingPermissionEntity bp
            where bp.user = :user
            and bp.building in :buildings
            """)
    List<BuildingPermissionEntity> findAllByUserIdAndBuildings(UserEntity user, Set<UUID> buildings);
    
}
