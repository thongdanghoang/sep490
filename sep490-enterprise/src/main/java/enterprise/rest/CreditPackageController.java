package enterprise.rest;

import enterprise.dtos.CreditPackageDTO;
import enterprise.mappers.CreditPackageMapper;
import enterprise.services.CreditPackageService;
import green_buildings.commons.api.security.UserRole;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/credit-package")
@RequiredArgsConstructor
public class CreditPackageController {
    
    private final CreditPackageService creditPackageService;
    private final CreditPackageMapper mapper;
    
    @GetMapping
    @RolesAllowed({UserRole.RoleNameConstant.SYSTEM_ADMIN, UserRole.RoleNameConstant.ENTERPRISE_OWNER})
    public List<CreditPackageDTO> findAll() {
        return creditPackageService.findAll()
                                   .stream()
                                   .map(mapper::entityToDTO)
                                   .toList();
    }
    
    @GetMapping("/{id}")
    @RolesAllowed({UserRole.RoleNameConstant.SYSTEM_ADMIN, UserRole.RoleNameConstant.ENTERPRISE_OWNER})
    public ResponseEntity<CreditPackageDTO> findById(@PathVariable UUID id) {
        CreditPackageDTO creditPackageDTO = creditPackageService.findById(id)
                                                                .map(mapper::entityToDTO)
                                                                .orElseThrow();
        return ResponseEntity.ok(creditPackageDTO);
    }
    
}
