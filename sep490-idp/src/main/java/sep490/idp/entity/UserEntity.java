package sep490.idp.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;
import sep490.common.api.utils.CommonConstant;

import java.util.HashSet;
import java.util.Set;

@NamedEntityGraph(
        name = UserEntity.USER_PERMISSIONS_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode("permissions")
        }
)
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends AbstractAuditableEntity {
    
    public static final String USER_PERMISSIONS_ENTITY_GRAPH = "user-permissions-entity-graph";
    
    @Pattern(regexp = CommonConstant.EMAIL_PATTERN)
    @Column(name = "email", nullable = false, unique = true)
    private String email;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_role")
    private UserRole role;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "user_scope")
    private UserScope scope;
    
    @OneToMany(mappedBy = "id.user", cascade = CascadeType.PERSIST)
    private Set<BuildingPermissionEntity> permissions = new HashSet<>();
    
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
    
    @Column(name = "is_deleted")
    private boolean isDeleted;
    
    public static UserEntity register(
            String email,
            boolean emailVerified,
            UserRole role,
            UserScope scope,
            String firstName,
            String lastName,
            String phone,
            boolean phoneVerified,
            String password) {
        UserEntity user = new UserEntity();
        user.setEmail(email);
        user.setEmailVerified(emailVerified);
        user.setRole(role);
        user.setScope(scope);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setPhoneVerified(phoneVerified);
        user.setPassword(password);
        return user;
    }
    
}
