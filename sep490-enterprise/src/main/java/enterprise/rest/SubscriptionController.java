package enterprise.rest;


import enterprise.dtos.CreditConvertRatioDTO;
import enterprise.dtos.SubscribeRequestDTO;
import enterprise.mappers.CreditConvertRatioMapper;
import enterprise.services.SubscriptionService;
import green_buildings.commons.api.security.UserRole;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/subscription")
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
    
}
