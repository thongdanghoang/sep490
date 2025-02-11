package green_buildings.idp.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import green_buildings.commons.api.exceptions.BusinessException;
import green_buildings.commons.api.security.UserRole;
import green_buildings.commons.api.security.UserScope;
import green_buildings.idp.entity.BuildingPermissionEntity;
import green_buildings.idp.entity.UserEntity;
import green_buildings.idp.repository.UserRepository;

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

