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
    
    /**
     * Retrieves the class name of the authenticated user.
     *
     * @param principal the authenticated user's principal object
     * @return a ResponseEntity containing the fully qualified class name of the principal
     */
    @GetMapping("/test")
    public ResponseEntity<String> test(Principal principal) {
        return ResponseEntity.ok(principal.getClass().getName());
    }
    
    /**
     * Handles a POST request to the "/test" endpoint and returns the authenticated user's principal class name.
     *
     * @param principal The authenticated user's principal object representing their security context
     * @param payload An optional Enterprise User Data Transfer Object sent in the request body
     * @return A ResponseEntity containing the fully qualified class name of the principal
     */
    @PostMapping("/test")
    public ResponseEntity<String> testPost(Principal principal, @RequestBody EnterpriseUserDTO payload) {
        return ResponseEntity.ok(principal.getClass().getName());
    }
    
    /**
     * Throws a technical exception for testing error handling purposes.
     *
     * @return ResponseEntity with no content, as the method is designed to throw an exception
     * @throws TechnicalException always thrown with the message "Technical exception"
     */
    @PostMapping("/technical")
    public ResponseEntity<Void> throwTechnicalException() {
        throw new TechnicalException("Technical exception");
    }
    
    /**
     * Throws a business exception to simulate an error scenario.
     *
     * @return ResponseEntity with no content, as the method is designed to throw an exception
     * @throws BusinessException with a predefined field name, internationalization key, and empty list of parameters
     */
    @PostMapping("/business")
    public ResponseEntity<Void> throwBusinessException() {
        throw new BusinessException("field", "i18nKey", Collections.emptyList());
    }
    
    /**
     * Retrieves a secure endpoint for a specific building with authorization check.
     *
     * @param buildingId Unique identifier of the building to be accessed
     * @return ResponseEntity containing the string representation of the building ID
     * @throws AccessDeniedException if the user does not have permission to access the building
     */
    @GetMapping("/secure")
    @PreAuthorize("@securityCheckerBean.checkIfUserHasPermission(buildingId)")
    public ResponseEntity<String> secure(@PathVariable UUID buildingId) {
        return ResponseEntity.ok(buildingId.toString());
    }
    
}
