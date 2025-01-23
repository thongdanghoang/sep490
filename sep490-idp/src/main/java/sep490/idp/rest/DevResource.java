package sep490.idp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep490.common.api.exceptions.BusinessException;
import sep490.common.api.exceptions.TechnicalException;
import sep490.idp.dto.EnterpriseUserDTO;

import java.security.Principal;
import java.util.Collections;
import java.util.UUID;

@RestController
@RequestMapping("/api/dev")
@RequiredArgsConstructor
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
    
    @GetMapping("/secure")
    @PreAuthorize("@securityCheckerBean.checkIfUserHasPermission(buildingId)")
    public ResponseEntity<String> secure(@PathVariable UUID buildingId) {
        return ResponseEntity.ok(buildingId.toString());
    }
    
}
