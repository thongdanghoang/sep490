package sep490.idp.mapper.decoratos;

import commons.springfw.impl.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sep490.common.api.dto.auth.BuildingPermissionDTO;
import sep490.idp.dto.EnterpriseUserDetailsDTO;
import sep490.idp.entity.BuildingPermissionEntity;
import sep490.idp.entity.UserEntity;
import sep490.idp.mapper.EnterpriseUserMapper;
import sep490.idp.repository.BuildingPermissionRepository;
import sep490.idp.repository.UserRepository;

import java.util.stream.Collectors;

@Component
public abstract class EnterpriseUserMapperDecorator implements EnterpriseUserMapper {
    
    @Autowired
    @Qualifier("delegate")
    private EnterpriseUserMapper delegate;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BuildingPermissionRepository buildingPermissionRepository;

    @Override
    public UserEntity createNewEnterpriseUser(EnterpriseUserDetailsDTO dto) {
        UserEntity user = delegate.createNewEnterpriseUser(dto);
        user.getEnterprise().setEnterprise(SecurityUtils.getCurrentUserEnterpriseId().orElseThrow());
        return user;
    }
    
    @Override
    public void updateEnterpriseUser(UserEntity user, EnterpriseUserDetailsDTO dto) {
        delegate.updateEnterpriseUser(user, dto);
        var buildings = dto
                .buildingPermissions().stream()
                .map(BuildingPermissionDTO::buildingId)
                .collect(Collectors.toSet());
        var buildingPermissions = buildingPermissionRepository
                .findAllByUserIdAndBuildings(user, buildings)
                .stream()
                .collect(Collectors.toMap(BuildingPermissionEntity::getBuilding, BuildingPermissionEntity::getId));
        user.getBuildingPermissions().stream()
            .filter(bp -> buildingPermissions.containsKey(bp.getBuilding()))
            .forEach(bp -> {
                bp.setId(buildingPermissions.get(bp.getBuilding()));
            });
    }
    
    private UserEntity getCurrentUser() {
        return SecurityUtils
                .getCurrentUserEmail()
                .flatMap(email -> userRepository.findByEmail(email))
                .orElseThrow();
    }
}
