package sep490.idp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;
import java.util.UUID;

/**
 * An abstract base class for entities with a UUID identifier.
 * This class provides common functionality for entity equality and hashing.
 */
@MappedSuperclass
@Setter
@Getter
public abstract class AbstractReferenceEntity {

    /**
     * The unique identifier for this entity.
     * <p>
     * This UUID is externally generated and assigned by other services.
     * It should not be manually initialized within this system.
     * </p>
     */
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (!(o instanceof AbstractReferenceEntity that)) { return false; }
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
