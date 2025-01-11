package sep490.idp.validation;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sep490.idp.dto.SignupDTO;
import sep490.idp.repository.UserRepository;


@Component
@Qualifier("signupValidator")
@RequiredArgsConstructor
public class SignupValidator implements Validator<SignupDTO> {

    private final UserRepository userRepo;


    @Override
    public void validate(SignupDTO dto) {
        if (!passwordsMatch(dto)) {
            dto.addError("password", "validation.password.invalid.notmatch");
        }
        if (userRepo.existsByEmail(dto.getEmail())) {
            dto.addError("password", "validation.email.invalid.exist");
        }
        checkValidated(dto);
    }

    private void checkValidated(SignupDTO dto) {
        if (!dto.getErrorMap().isEmpty()) {
            dto.setValidated(false);
        }
    }

    private boolean passwordsMatch(SignupDTO signupDTO) {
        return signupDTO.getPassword().equals(signupDTO.getConfirmPassword());
    }


}
