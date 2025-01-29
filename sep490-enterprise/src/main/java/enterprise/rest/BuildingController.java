package enterprise.rest;

import commons.springfw.impl.securities.UserContextData;
import enterprise.dtos.BuildingDTO;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep490.common.api.security.UserRole;

import java.util.List;

@RestController
@RequestMapping("/buildings")
@RequiredArgsConstructor
@RolesAllowed({
        UserRole.RoleNameConstant.ENTERPRISE_OWNER,
        UserRole.RoleNameConstant.ENTERPRISE_EMPLOYEE
})
public class BuildingController {
    
    @GetMapping
    @RolesAllowed(UserRole.RoleNameConstant.ENTERPRISE_OWNER)
    public ResponseEntity<List<BuildingDTO>> getEnterpriseBuildings(@AuthenticationPrincipal UserContextData userContextData) {
        return ResponseEntity.ok().build();
    }
    
}
