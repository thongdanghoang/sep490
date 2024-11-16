package sep490.idp.common;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class AbstractBaseEntity {

    @Id
    @Column(name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Version
    @Column(name = "version", nullable = false)
    private int version;

    @Transient
    private boolean transientHashCodeLeaked;

    protected AbstractBaseEntity() {
        // Abstract class should not be instantiated
    }

    public boolean isPersisted() {
        return Optional.ofNullable(getId()).isPresent();
    }

    @Override
    public int hashCode() {
        if (!isPersisted()) { // is new or is in transient state.
            transientHashCodeLeaked = true;
            return -super.hashCode();
        }

        // Because hashcode has just been asked for when the object is in transient state at that time super.hashCode() is returned.
        // Now for consistency, we return the same value.
        if (transientHashCodeLeaked) {
            return -super.hashCode();
        }

        // The above mechanism obey the rule: if 2 objects are equal, their hashcode must be same.
        return getId().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        /*
         * The following is a solution that works for hibernate lazy loading proxies.
         */
        if (JpaUtils.getPersistenceClassWithoutInitializingProxy(this) != JpaUtils.getPersistenceClassWithoutInitializingProxy(obj)) {
            return false;
        }

        /*
         * To check whether the class of the argument is equal (or compatible) to the implementing class before
         * casting it
         * */
        if (getClass() != obj.getClass()) {
            return false;
        }

        AbstractBaseEntity other = (AbstractBaseEntity) obj;
        if (isPersisted() && other.isPersisted()) { // both entities are not new
            return getId().equals(other.getId());
        }
        return false;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" + getId() + ")";
    }
}