package sep490.idp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sep490.idp.dto.CredentialsRegistration;
import sep490.idp.dto.CredentialsVerification;
import sep490.idp.security.UserContextData;
import sep490.idp.service.AuthenticatorService;
import sep490.idp.service.impl.LoginService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class PasskeyController {

    private final AuthenticatorService authenticatorService;
    private final LoginService loginService;

    @PostMapping("/passkey/login")
    public String login(@RequestBody CredentialsVerification verification, SessionStatus sessionStatus) {
        var user = authenticatorService.authenticate(verification);
        loginService.login(user);

        sessionStatus.setComplete(); //Remove challenge in session
        return "redirect:/account";
    }

    @PostMapping("/passkey/register")
    public String register(@RequestBody CredentialsRegistration credentials,
                           @AuthenticationPrincipal UserContextData user) {
        authenticatorService.saveCredentials(credentials, user.getUserEntity());
        return "redirect:/account";
    }

    @PostMapping("/passkey/delete")
    public String login(@NotNull @RequestParam("credential-id") String credentialId, @AuthenticationPrincipal UserContextData userContextData,
                        RedirectAttributes redirectAttributes) {
        if (authenticatorService.deleteCredential(userContextData.getUserEntity(), credentialId)) {
            redirectAttributes.addFlashAttribute("alert", "Passkey deleted.");
        }
        return "redirect:/account";
    }

}
