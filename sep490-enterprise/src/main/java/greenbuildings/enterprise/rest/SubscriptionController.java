package greenbuildings.enterprise.rest;


import greenbuildings.enterprise.dtos.CreditConvertRatioDTO;
import greenbuildings.enterprise.dtos.SubscribeRequestDTO;
import greenbuildings.enterprise.mappers.CreditConvertRatioMapper;
import greenbuildings.enterprise.services.SubscriptionService;
import greenbuildings.commons.api.security.UserRole;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/subscription")
@RequiredArgsConstructor
public class SubscriptionController {
    
    private final SubscriptionService subscriptionService;
    private final CreditConvertRatioMapper creditConvertRatioMapper;
    
    
    @GetMapping("/convert-ratio")
    public ResponseEntity<List<CreditConvertRatioDTO>> getCreditConvertRatio() {
        List<CreditConvertRatioDTO> list = subscriptionService.getCreditConvertRatios().stream().map(creditConvertRatioMapper::toDTO).toList();
        return ResponseEntity.ok(list);
    }
    
    @PostMapping
    @RolesAllowed(value = {UserRole.RoleNameConstant.ENTERPRISE_OWNER})
    public ResponseEntity<Void> subscribe(@RequestBody @Valid SubscribeRequestDTO request) {
        subscriptionService.subscribe(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/convert-ratio/{id}")
    public ResponseEntity<CreditConvertRatioDTO> getCreditConvertRatioDetail(@PathVariable UUID id) {
      var creditConvertRatio = subscriptionService.getCreditConvertRatioDetail(id);
        return ResponseEntity.ok(creditConvertRatioMapper.toDTO(creditConvertRatio));
    }

    @PostMapping("/convert-ratio/update")
    public ResponseEntity<Void> updateCreditConvertRatio(@RequestBody CreditConvertRatioDTO creditConvertRatioDTO) {
        subscriptionService.updateCreditConvertRatio(creditConvertRatioDTO);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
    
}
