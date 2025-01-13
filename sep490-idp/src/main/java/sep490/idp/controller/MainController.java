package sep490.idp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import sep490.idp.dto.*;
import sep490.idp.repository.UserAuthenticatorRepository;
import sep490.idp.security.UserContextData;
import sep490.idp.service.UserService;
import sep490.idp.utils.IMessageUtil;

import java.util.UUID;


@Controller
@RequiredArgsConstructor
@Slf4j
@SessionAttributes("challenge")
public class MainController {

    public static final String ERROR_MSG = "errorMsg";
    private final IMessageUtil messageUtil;
    private final UserService userService;
    private final UserAuthenticatorRepository authenticatorRepository;


    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, @RequestParam(value = "message", required = false) String message, Model model) {
        model.addAttribute("challenge", UUID.randomUUID().toString());
        model.addAttribute("loginDTO", new LoginDTO());
        if (error != null) {
            model.addAttribute("errorKey", error);
        }
        if (message != null) {
            model.addAttribute("message", messageUtil.getMessage(message));
        }
        return "login";
    }

    @GetMapping("/signup")
    public String signUpPage(Model model) {
        model.addAttribute("signupDTO", new SignupDTO());
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute("signupDTO") SignupDTO signupDTO, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "signup";
        }
        SignupResult signupResult = userService.signup(signupDTO, model);
        model.addAttribute(ERROR_MSG, signupResult.getErrorMessage());
        return signupResult.getRedirectUrl();
    }

    @GetMapping("/success")
    public String success() {
        return "redirect:/account";
    }

    @GetMapping("/account")
    public String accountPage(@AuthenticationPrincipal UserContextData userContextData, Model model) {
        model.addAttribute("challenge", UUID.randomUUID().toString());
        var authenticators = authenticatorRepository.findUserAuthenticatorByUser(userContextData.getUserEntity());
        model.addAttribute("userId", userContextData.getUserEntity().getId());
        model.addAttribute("email", userContextData.getUserEntity().getEmail());
        model.addAttribute("authenticators", authenticators);

        return "account-page";
    }
}
