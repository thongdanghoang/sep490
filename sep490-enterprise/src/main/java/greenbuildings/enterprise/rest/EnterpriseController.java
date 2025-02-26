package greenbuildings.enterprise.rest;

import greenbuildings.enterprise.mappers.EnterpriseMapper;
import greenbuildings.enterprise.services.EnterpriseService;
import greenbuildings.commons.api.security.UserRole;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/enterprise")
@RequiredArgsConstructor
@RolesAllowed({
        UserRole.RoleNameConstant.ENTERPRISE_OWNER
})
public class EnterpriseController {
    
    private final EnterpriseService service;
    private final EnterpriseMapper mapper;
    
}
