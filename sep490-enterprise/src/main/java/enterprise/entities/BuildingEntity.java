package enterprise.entities;

import commons.springfw.impl.entities.AbstractAuditableEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "buildings")
@Getter
@Setter
@NoArgsConstructor
public class BuildingEntity extends AbstractAuditableEntity {
    
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "enterprise_id", nullable = false)
    private EnterpriseEntity enterprise;
    
    @NotBlank
    @Column(name = "name", nullable = false)
    private String name;
    
    @Min(value = 1)
    @Column(name = "floors", nullable = false)
    private int floors;
    
    @DecimalMin(value = "0.0", inclusive = true)
    @Column(name = "square_meters", nullable = false, precision = 15, scale = 3)
    private BigDecimal squareMeters;
}
