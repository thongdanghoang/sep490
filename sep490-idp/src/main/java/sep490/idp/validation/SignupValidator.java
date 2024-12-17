package sep490.idp.validation;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import sep490.idp.dto.SignupDTO;
import sep490.idp.repository.UserRepository;

import java.util.Optional;

@Component
@Qualifier("signupValidator")
@RequiredArgsConstructor
public class SignupValidator implements Validator<SignupDTO> {

    private final UserRepository userRepo;


    @Override
    public Optional<String> getValidateFirstMessage(SignupDTO signupDTO) {
        if (!passwordsMatch(signupDTO)) {
            return Optional.of("validation.password.invalid.notmatch");
        }
        if (userRepo.existsByEmail(signupDTO.getEmail())) {
            return Optional.of("validation.email.invalid.exist");
        }

        return Optional.empty();
    }

    private boolean passwordsMatch(SignupDTO signupDTO) {
        return signupDTO.getPassword().equals(signupDTO.getConfirmPassword());
    }


}
