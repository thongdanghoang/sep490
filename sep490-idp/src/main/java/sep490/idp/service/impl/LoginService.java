package sep490.idp.service.impl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import sep490.idp.security.UserAuthenticationToken;
import sep490.idp.entity.UserEntity;
import sep490.idp.security.UserContextData;
import sep490.idp.utils.SecurityUtils;

@Component
@RequiredArgsConstructor
public class LoginService {

    private final HttpServletRequest request;
    private final HttpServletResponse response;

    public void login(UserEntity user) {
        var auth = new UserAuthenticationToken(new UserContextData(user));
        SecurityUtils.storeAuthenticationToContext(auth, request, response);
    }
}
