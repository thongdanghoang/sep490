package sep490.idp.rest;

import commons.springfw.impl.mappers.CommonMapper;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.dto.SearchResultDTO;
import sep490.common.api.security.UserRole;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.dto.EnterpriseUserDetailsDTO;
import sep490.idp.dto.UserCriteriaDTO;
import sep490.idp.entity.UserEntity;
import sep490.idp.mapper.EnterpriseUserMapper;
import sep490.idp.service.UserService;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/enterprise-user")
@RequiredArgsConstructor
@RolesAllowed({UserRole.RoleNameConstant.ENTERPRISE_OWNER})
public class EnterpriseUserRestController {
    
    private final UserService userService;
    private final EnterpriseUserMapper userMapper;
    
    @GetMapping("/{id}")
    public ResponseEntity<EnterpriseUserDetailsDTO> getEnterpriseUserDetail(@PathVariable("id") UUID id) {
        var userDetailsEntity = userService.getEnterpriseUserDetail(id);
        var userDetails = userMapper.userEntityToEnterpriseUserDetailDTO(userDetailsEntity);
        return ResponseEntity.ok(userDetails);
    }
    
    @PostMapping("/search")
    public ResponseEntity<SearchResultDTO<EnterpriseUserDTO>> searchEnterpriseUser(@RequestBody SearchCriteriaDTO<UserCriteriaDTO> searchCriteria) {
        var searchResults = userService.search(searchCriteria);
        return ResponseEntity.ok(
                CommonMapper.toSearchResultDTO(
                        searchResults,
                        userMapper::userEntityToEnterpriseUserDTO));
    }
    
    @PostMapping()
    public ResponseEntity<Void> createNewEnterpriseUser(@RequestBody EnterpriseUserDetailsDTO enterpriseUserDTO) {
        if (Objects.isNull(enterpriseUserDTO.id())) { // create new user
            var newEnterpriseUser = userMapper.createNewEnterpriseUser(enterpriseUserDTO);
            return saveUserAndReturnResponse(newEnterpriseUser, HttpStatus.CREATED);
        }
        
        var user = userService.getEnterpriseUserDetail(enterpriseUserDTO.id());
        userMapper.updateEnterpriseUser(user, enterpriseUserDTO);
        return saveUserAndReturnResponse(user, HttpStatus.OK);
    }
    
    private ResponseEntity<Void> saveUserAndReturnResponse(UserEntity user, HttpStatus status) {
        associateUserWithEntities(user);
        userService.createOrUpdateEnterpriseUser(user);
        return ResponseEntity.status(status).build();
    }
    
    private void associateUserWithEntities(UserEntity user) {
        user.getEnterprise().setUser(user);
        user.getBuildingPermissions().forEach(bp -> bp.setUser(user));
    }
    
    @DeleteMapping
    public ResponseEntity<Void> deleteUsers(@RequestBody Set<UUID> userIds) {
        userService.deleteUsers(userIds);
        return ResponseEntity.noContent().build();
    }
    
}
