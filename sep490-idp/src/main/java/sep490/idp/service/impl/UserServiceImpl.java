package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sep490.idp.dto.SignupDTO;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserRepository;
import sep490.idp.service.UserService;
import sep490.idp.validation.Validator;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    public static final String ERROR_MSG = "errorMsg";

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("signupValidator")
    private final Validator<SignupDTO> validator;

    @Override
    public String signup(SignupDTO signupDTO, Model model) {

        String errorMsg = validator.getValidateFirstMessage(signupDTO);
        if (errorMsg != null) {
            model.addAttribute(ERROR_MSG, errorMsg);
            return "signup";
        }

        var user = createUser(signupDTO);
        userRepo.save(user);

        return "redirect:/login";
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
