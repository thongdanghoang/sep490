package sep490.idp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.SessionAttribute;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sep490.idp.dto.CredentialsRegistration;
import sep490.idp.dto.CredentialsVerification;
import sep490.idp.security.UserContextData;
import sep490.idp.service.impl.AuthenticatorService;
import sep490.idp.service.impl.LoginService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PasskeyController {

    private final AuthenticatorService authenticatorService;
    private final LoginService loginService;

    @PostMapping("/passkey/login")
    public String login(@RequestBody CredentialsVerification verification, SessionStatus sessionStatus,
                        @SessionAttribute("challenge") String challenge, HttpServletRequest request, HttpServletResponse response) {
        var user = authenticatorService.authenticate(verification, challenge);
        loginService.login(user);

        sessionStatus.setComplete();
        return "redirect:/account";
    }

    @PostMapping("/passkey/register")
    public String register(@RequestBody CredentialsRegistration credentials,
                           @AuthenticationPrincipal UserContextData user,
                           RedirectAttributes redirectAttributes) {
        authenticatorService.saveCredentials(credentials, user.getUserEntity());
        redirectAttributes.addFlashAttribute("alert", "You have registered a new passkey!");
        return "redirect:/account";
    }

}
