package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sep490.idp.dto.SignupDTO;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserRepository;
import sep490.idp.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public String signup(SignupDTO signupDTO, Model model) {
        if (!passwordsMatch(signupDTO)) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }

        if (userRepo.existsByEmail(signupDTO.getEmail())) {
            model.addAttribute("error", "Email is already registered");
            return "signup";
        }

        var user = createUser(signupDTO);
        userRepo.save(user);

        return "redirect:/login";
    }

    private boolean passwordsMatch(SignupDTO signupDTO) {
        return signupDTO.getPassword().equals(signupDTO.getConfirmPassword());
    }

    private UserEntity createUser(SignupDTO signupDTO) {
        return UserEntity.builder()
                .email(signupDTO.getEmail())
                .emailVerified(false)
                .firstName(signupDTO.getFirstName())
                .lastName(signupDTO.getLastName())
                .phone(signupDTO.getPhone())
                .phoneVerified(false)
                .password(passwordEncoder.encode(signupDTO.getPassword()))
                .build();
    }
}
