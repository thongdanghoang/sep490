package sep490.idp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "enterprise")
@Getter
@Setter
public class EnterpriseEntity extends AbstractReferenceEntity {

    @OneToMany(mappedBy = "enterprise")
    private Set<BuildingEntity> buildings = new HashSet<>();

}
