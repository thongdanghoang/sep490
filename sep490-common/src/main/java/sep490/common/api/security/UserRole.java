package sep490.common.api.security;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum UserRole {
    ENTERPRISE_OWNER(RoleNameConstant.ENTERPRISE_OWNER),
    SYSTEM_ADMIN(RoleNameConstant.SYSTEM_ADMIN);
    
    private final String roleValue;
    
    
    public static UserRole fromValue(String value) {
        return Arrays.stream(UserRole.values())
                     .filter(role -> StringUtils.equals(role.getRoleValue(), value))
                     .findAny()
                     .orElse(null);
    }
    
    public static final class RoleNameConstant {
        public static final String ENTERPRISE_OWNER = "sep390.EnterpriseOwner";
        public static final String SYSTEM_ADMIN = "sep390.SystemAdmin";
        
        private RoleNameConstant() {
        }
    }
}
