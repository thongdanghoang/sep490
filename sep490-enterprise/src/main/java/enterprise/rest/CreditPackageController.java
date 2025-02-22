package enterprise.rest;

import commons.springfw.impl.mappers.CommonMapper;
import commons.springfw.impl.securities.UserContextData;
import enterprise.dtos.BuildingDTO;
import enterprise.dtos.CreditPackageDTO;
import enterprise.entities.CreditPackageEntity;
import enterprise.mappers.CreditPackageMapper;
import enterprise.services.CreditPackageService;
import green_buildings.commons.api.dto.SearchCriteriaDTO;
import green_buildings.commons.api.dto.SearchResultDTO;
import green_buildings.commons.api.security.UserRole;
import jakarta.annotation.security.RolesAllowed;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/credit-package")
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

    @PostMapping()
    @RolesAllowed({UserRole.RoleNameConstant.SYSTEM_ADMIN})
    public ResponseEntity<Void> createCreditPackage(@RequestBody CreditPackageDTO creditPackageDTO) {
        if (Objects.isNull(creditPackageDTO.id())) {
            return createNewCreditPackage(creditPackageDTO);
        }

        return updateExistingCreditPackage(creditPackageDTO)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    private ResponseEntity<Void> createNewCreditPackage(CreditPackageDTO creditPackageDTO) {
        var creditPackageEntity = mapper.dtoToCreateCreditPackage(creditPackageDTO);
        return saveUserAndReturnResponse(creditPackageEntity, HttpStatus.CREATED);
    }

    private Optional<ResponseEntity<Void>> updateExistingCreditPackage(CreditPackageDTO creditPackageDTO) {
        return creditPackageService.findById(creditPackageDTO.id())
                .map(entity -> {
                  CreditPackageEntity creditPackageEntity =  mapper.dtoToUpdateCreditPackage(entity, creditPackageDTO);
                    return saveUserAndReturnResponse(creditPackageEntity, HttpStatus.OK);
                });
    }

    private ResponseEntity<Void> saveUserAndReturnResponse(CreditPackageEntity entity, HttpStatus status) {
        creditPackageService.createOrUpdate(entity);
        return ResponseEntity.status(status).build();
    }

    @DeleteMapping
    @RolesAllowed({UserRole.RoleNameConstant.SYSTEM_ADMIN})
    public ResponseEntity<Void> deleteCreditPackages(@RequestBody Set<UUID> packageIds) {
        creditPackageService.deletePackages(packageIds);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/search")
    @RolesAllowed({UserRole.RoleNameConstant.SYSTEM_ADMIN})
    public ResponseEntity<SearchResultDTO<CreditPackageDTO>> search(@RequestBody SearchCriteriaDTO<Void> searchCriteria ) {
        var pageable = CommonMapper.toPageable(searchCriteria.page(), searchCriteria.sort());
        var searchResults = creditPackageService.search(pageable);
        return ResponseEntity.ok(
                CommonMapper.toSearchResultDTO(
                        searchResults,
                        mapper::entityToDTO));
    }

    
}
