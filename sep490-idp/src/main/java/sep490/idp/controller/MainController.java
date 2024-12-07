package sep490.idp.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import sep490.idp.dto.LoginDTO;
import sep490.idp.dto.SignupDTO;
import sep490.idp.service.UserService;

@Controller
@RequiredArgsConstructor
public class MainController {

    private final UserService userService;

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error, Model model) {
        model.addAttribute("loginDTO", new LoginDTO());
        if (error != null) {
            model.addAttribute("errorMessage", error);
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
        return userService.signup(signupDTO, model);
    }

    @GetMapping("/success")
    public String success() {
        return "success";
    }
}
