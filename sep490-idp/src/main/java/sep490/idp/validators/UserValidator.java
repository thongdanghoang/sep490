package sep490.idp.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sep490.common.api.exceptions.BusinessException;
import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserRepository;

import java.util.Objects;


@Component
@RequiredArgsConstructor
public class UserValidator {
    
    private final UserRepository userRepository;
    
    public void validateEnterpriseOwnerManageEmployees(UserEntity employee) {
        // TODO: replace with i18n
        if (employee.getEnterprise().getRole() != UserRole.ENTERPRISE_EMPLOYEE) {
            throw new BusinessException("role", "business.role.enterpriseOnlyAddEmployee");
        }
        if (employee.getBuildingPermissions().isEmpty()) {
            throw new BusinessException("buildingPermissions", "business.buildingPermissions.notEmpty");
        }
        if (employee.getEnterprise().getScope() == UserScope.BUILDING) {
            if (employee.getBuildingPermissions().stream()
                        .map(BuildingPermissionEntity::getBuilding)
                        .anyMatch(Objects::isNull)) {
                throw new BusinessException("buildingPermissions", "business.buildingPermissions.shouldHave");
            }
            if (employee.getBuildingPermissions().stream()
                        .map(BuildingPermissionEntity::getBuilding)
                        .distinct()
                        .count() != employee.getBuildingPermissions().size()) {
                throw new BusinessException("buildingPermissions", "business.buildingPermissions.shouldHaveUnique");
            }
        }
        if (employee.getEnterprise().getScope() == UserScope.ENTERPRISE) {
            if (employee.getBuildingPermissions().size() != 1
                || employee.getBuildingPermissions().stream()
                           .map(BuildingPermissionEntity::getBuilding)
                           .anyMatch(Objects::nonNull)) {
                throw new BusinessException("buildingPermissions",
                                            "business.buildingPermissions.idBuildingIsNull");
            }
        }
        // validators need query should be last
        if (Objects.isNull(employee.getId())
            && userRepository.existsByEmail(employee.getEmail())) {
            throw new BusinessException("email", "business.email.exist");
        }
    }
    
}

