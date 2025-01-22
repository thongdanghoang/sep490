package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.stereotype.Service;
import sep490.common.api.exceptions.TechnicalException;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserInfoService {
    
    private final UserRepository userRepository;
    
    public OidcUserInfo loadUser(String email) {
        UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new TechnicalException("User not exist"));
        return new OidcUserInfo(buildUserClaims(user));
    }
    
    private Map<String, Object> buildUserClaims(UserEntity user) {
        return OidcUserInfo.builder()
                           .subject(user.getEmail())
                           .givenName(user.getFirstName())
                           .familyName(user.getLastName())
                           .email(user.getEmail())
                           .emailVerified(user.isEmailVerified())
                           .phoneNumber(user.getPhone())
                           .phoneNumberVerified(false)
                           .build()
                           .getClaims();
    }
    
    
    public Map<String, Object> getCustomClaimsForJwtAuthenticationToken(String email) {
        Map<String, Object> claims = new HashMap<>();
        // UserEntity user = userRepository.findByEmail(email).orElseThrow(() -> new TechnicalException("User not exist"));
        
        claims.put("permissions", "only string accept here");
        // TODO: should we store user's permission in token
        
        return claims;
    }
}
