package enterprise.entities;

import commons.springfw.impl.entities.AbstractAuditableEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "enterprises")
@Getter
@Setter
@NoArgsConstructor
public class EnterpriseEntity extends AbstractAuditableEntity {
    
    @NotNull
    @OneToOne(mappedBy = "enterprise", cascade = CascadeType.ALL, optional = false)
    private WalletEntity wallet;
    
}
