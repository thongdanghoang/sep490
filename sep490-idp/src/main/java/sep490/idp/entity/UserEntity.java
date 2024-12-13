package sep490.idp.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import sep490.common.api.utils.CommonConstant;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class UserEntity extends AbstractAuditableEntity {

    @Pattern(regexp = CommonConstant.EMAIL_PATTERN)
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 72)
    private String password;

    @Column(name = "email_verified")
    private boolean emailVerified;
    
    @Column(name = "phone", length = 16)
    private String phone;
    
    @Column(name = "phone_verified")
    private boolean phoneVerified;
    
    @Column(name = "first_name", length = 50)
    private String firstName;
    
    @Column(name = "last_name", length = 100)
    private String lastName;
    
}
