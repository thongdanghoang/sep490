package enterprise.rest;

import green_buildings.commons.api.exceptions.BusinessErrorParam;
import green_buildings.commons.api.exceptions.BusinessException;
import green_buildings.commons.api.exceptions.TechnicalException;
import green_buildings.commons.api.security.UserRole;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
@RolesAllowed({
        UserRole.RoleNameConstant.SYSTEM_ADMIN,
})
@Slf4j
public class DevController {
    
    
    @PostMapping
    public String save(String body) {
        log.info(body);
        return body;
    }
    
    @PostMapping("/save-business-error")
    public String saveBusinessError(@RequestBody String body) {
        log.info(body);
        throw new DemoException("email", "demo", new BusinessErrorParam("name", "invalid_name"));
    }
    
    @PostMapping("/save-technical-error")
    public String saveTechnicalError(@RequestBody String body) {
        log.info(body);
        throw new TechnicalException("Demo technical error");
    }
    
    static class DemoException extends BusinessException {
        
        DemoException(String field, String i18nKey, BusinessErrorParam args) {
            super(field, i18nKey, List.of(args));
        }
    }
    
}
