package sep490.idp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sep490.common.api.security.PermissionRole;

import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "building_permission")
@Getter
@NoArgsConstructor
public class BuildingPermissionEntity {

    @EmbeddedId
    private BuildingPermissionId id;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private PermissionRole role;
    
    public BuildingPermissionEntity(UUID buildingId, UserEntity user, PermissionRole role) {
        this.id = new BuildingPermissionId(buildingId, user);
        this.role = role;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof BuildingPermissionEntity that)) { return false; }
        return Objects.equals(id, that.id) && role == that.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
