package sep490.idp.service.impl;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
        AuthenticationException exception) throws IOException, ServletException {

        if (exception.getClass().isAssignableFrom(UsernameNotFoundException.class)
            || exception.getClass().isAssignableFrom(BadCredentialsException.class)) {
            String errorKey = "login.error.badCredentials";

            response.sendRedirect("/login?error=" + errorKey);
        }

    }
}
