package sep490.idp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.util.Objects;
import java.util.UUID;

@Embeddable
public class BuildingPermissionId {

    @Column(name = "building_id", nullable = false)
    private UUID buildingId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof BuildingPermissionId that)) { return false; }
        return Objects.equals(buildingId, that.buildingId) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(buildingId, user);
    }
}
