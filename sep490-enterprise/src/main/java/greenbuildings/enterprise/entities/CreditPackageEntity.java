package greenbuildings.enterprise.entities;

import commons.springfw.impl.entities.AbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "credit_packages")
@Getter
@Setter
@NoArgsConstructor
public class CreditPackageEntity extends AbstractAuditableEntity {

    @Column(name = "number_of_credits", nullable = false)
    @Min(0)
    private int numberOfCredits = 0;
    
    @Column(name = "price", nullable = false)
    @Min(0)
    private long price = 0;
    
}
