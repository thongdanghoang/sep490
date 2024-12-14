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
    public String getValidateFirstMessage(SignupDTO signupDTO) {
        if (!passwordsMatch(signupDTO)) {
            return "validation.password.invalid.notmatch";
        }
        if (userRepo.existsByEmail(signupDTO.getEmail())) {
            return "validation.email.invalid.exist";
        }

        return null;
    }

    private boolean passwordsMatch(SignupDTO signupDTO) {
        return signupDTO.getPassword().equals(signupDTO.getConfirmPassword());
    }


}
