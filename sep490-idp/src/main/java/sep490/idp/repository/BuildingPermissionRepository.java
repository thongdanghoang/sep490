package sep490.idp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.BuildingPermissionId;

import java.util.List;
import java.util.UUID;

@Repository
public interface BuildingPermissionRepository extends JpaRepository<BuildingPermissionEntity, BuildingPermissionId> {

    @Query(value = "select bp from BuildingPermissionEntity bp where bp.id.user.id = :userId")
    List<BuildingPermissionEntity> findAllByUserId(UUID userId);


}
