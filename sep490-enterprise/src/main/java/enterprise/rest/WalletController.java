package enterprise.rest;

import commons.springfw.impl.securities.UserContextData;
import enterprise.dtos.BuildingDTO;
import green_buildings.commons.api.exceptions.BusinessException;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Objects;

@RestController
@RequestMapping("/wallet")
@RequiredArgsConstructor
public class WalletController {
    
    @GetMapping("/balance")
    public ResponseEntity<Void> getBalance(@AuthenticationPrincipal UserContextData userContextData) {
//TODO: Must Authentication => get Id Enterpirse => get Wallet => get Balence
        if (Objects.nonNull(userContextData.getEnterpriseId())) {
            throw new BusinessException(StringUtils.EMPTY, "error.user.already.belongs.to.enterprise", Collections.emptyList());
        }
        return ResponseEntity.ok().build();
    }
}
