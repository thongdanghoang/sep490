package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sep490.idp.dto.SignupDTO;
import sep490.idp.dto.SignupResult;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserRepository;
import sep490.idp.service.UserService;
import sep490.idp.validation.Validator;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("signupValidator")
    private final Validator<SignupDTO> validator;

    @Override
    public SignupResult signup(SignupDTO signupDTO, Model model) {
        SignupResult result = validateSignupDTO(signupDTO);

        if (result.isSuccess()) {
            var user = createUser(signupDTO);
            userRepo.save(user);
            result.setSuccess(true);
        }

        return result;
    }

    private SignupResult validateSignupDTO(SignupDTO signupDTO) {
        SignupResult result = new SignupResult();
        result.setRedirectUrl("redirect:/login");
        result.setSuccess(true);

        validator.getValidateFirstMessage(signupDTO).ifPresent(msg -> {
            result.setSuccess(false);
            result.setErrorMessage(msg);
            result.setRedirectUrl("signup");
        });

        return result;
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
