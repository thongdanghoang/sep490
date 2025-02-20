package enterprise.rest;

import enterprise.services.EmissionCoefficientService;
import green_buildings.commons.api.security.UserRole;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/emission-coefficient")
@RequiredArgsConstructor
public class EmissionCoefficientController {
    
    private final EmissionCoefficientService emissionCoefficientService;
    
    
    @PostMapping("/upload")
    @RolesAllowed({UserRole.RoleNameConstant.SYSTEM_ADMIN})
    public void handleFileUpload(@RequestParam("file") MultipartFile file) {
        emissionCoefficientService.uploadCoefficientExcel(file);
    }


}
