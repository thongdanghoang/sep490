package green_buildings.idp.entity;

import commons.springfw.impl.entities.AbstractAuditableEntity;
import green_buildings.commons.api.security.UserRole;
import green_buildings.commons.api.security.UserScope;
import green_buildings.commons.api.utils.CommonConstant;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.NamedAttributeNode;
import jakarta.persistence.NamedEntityGraph;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@NamedEntityGraph(
        name = UserEntity.WITH_ENTERPRISE_PERMISSIONS_ENTITY_GRAPH,
        attributeNodes = {
                @NamedAttributeNode("enterprise"),
                @NamedAttributeNode("buildingPermissions")
        }
)
@FilterDef(name = UserEntity.BELONG_ENTERPRISE_FILTER, parameters = @ParamDef(name = UserEntity.BELONG_ENTERPRISE_PARAM, type = UUID.class))
@Filter(name = UserEntity.BELONG_ENTERPRISE_FILTER,
        condition = "id IN (SELECT ue.user_id FROM enterprise_users ue WHERE ue.enterprise_id = :" + UserEntity.BELONG_ENTERPRISE_PARAM)
@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserEntity extends AbstractAuditableEntity {
    
    public static final String WITH_ENTERPRISE_PERMISSIONS_ENTITY_GRAPH = "user-permissions-entity-graph";
    public static final String BELONG_ENTERPRISE_FILTER = "belongEnterpriseFilter";
    public static final String BELONG_ENTERPRISE_PARAM = "enterpriseId";
    
    @NotNull
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, optional = false)
    private UserEnterpriseEntity enterprise;
    
    @OneToMany(mappedBy = "user",
               cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
               orphanRemoval = true)
    private Set<BuildingPermissionEntity> buildingPermissions = new LinkedHashSet<>();
    
    @NotNull
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
    
    @Column(name = "deleted")
    private boolean deleted;
    
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
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);
        user.setPhoneVerified(phoneVerified);
        user.setPassword(password);
        user.setEnterprise(UserEnterpriseEntity.of(user, role, scope));
        return user;
    }
    
}
