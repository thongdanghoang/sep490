package enterprise.rest.impl;

import enterprise.dtos.BuildingDTO;
import enterprise.rest.BuildingController;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep490.common.api.security.UserRole;

import java.util.List;

@RestController
@RequestMapping("/buildings")
@RequiredArgsConstructor
public class BuildingControllerImpl implements BuildingController {
    
    @GetMapping
    @Override
    @RolesAllowed(UserRole.RoleNameConstant.ENTERPRISE_OWNER)
    public ResponseEntity<List<BuildingDTO>> getEnterpriseBuildings() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return ResponseEntity.ok().build();
    }
}
