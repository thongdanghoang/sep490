package sep490.idp.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import sep490.idp.dto.UserAuthenticationToken;
import sep490.idp.entity.UserEntity;

@Component
public class LoginService {
    private final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();

    private final HttpServletRequest request;

    private final HttpServletResponse response;

    public LoginService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public void login(UserEntity user) {
        var auth = new UserAuthenticationToken(user);
        var newContext = SecurityContextHolder.createEmptyContext();
        newContext.setAuthentication(auth);
        SecurityContextHolder.setContext(newContext);
        securityContextRepository.saveContext(newContext, request, response);
    }
}
