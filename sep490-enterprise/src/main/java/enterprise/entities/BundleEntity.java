package enterprise.entities;


import commons.springfw.impl.entities.AbstractAuditableEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bundles")
@Getter
@Setter
@NoArgsConstructor
public class BundleEntity extends AbstractAuditableEntity {
}
