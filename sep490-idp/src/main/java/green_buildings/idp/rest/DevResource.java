package green_buildings.idp.rest;

import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import green_buildings.commons.api.exceptions.BusinessException;
import green_buildings.commons.api.exceptions.TechnicalException;
import green_buildings.commons.api.security.UserRole;
import green_buildings.idp.dto.EnterpriseUserDTO;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
@RolesAllowed({
        UserRole.RoleNameConstant.SYSTEM_ADMIN
})
public class DevResource {
    
    @GetMapping("/test")
    public ResponseEntity<String> test(Principal principal) {
        return ResponseEntity.ok(principal.getClass().getName());
    }
    
    @PostMapping("/test")
    public ResponseEntity<String> testPost(Principal principal, @RequestBody EnterpriseUserDTO payload) {
        return ResponseEntity.ok(principal.getClass().getName());
    }
    
    @PostMapping("/technical")
    public ResponseEntity<Void> throwTechnicalException() {
        throw new TechnicalException("Technical exception");
    }
    
    @PostMapping("/business")
    public ResponseEntity<Void> throwBusinessException() {
        throw new BusinessException("field", "i18nKey", Collections.emptyList());
    }
    
    @GetMapping("/secure/{buildingId}")
    public ResponseEntity<String> secure(@PathVariable UUID buildingId) {
        return ResponseEntity.ok(buildingId.toString());
    }
    
}
