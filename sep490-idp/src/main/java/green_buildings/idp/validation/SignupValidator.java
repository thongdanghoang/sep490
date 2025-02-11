package green_buildings.idp.validation;


import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import green_buildings.idp.dto.SignupDTO;
import green_buildings.idp.repository.UserRepository;


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
        dto.setValidated(dto.getErrorMap().isEmpty() && StringUtils.isEmpty(dto.getErrorMsg()));
    }

    private boolean passwordsMatch(SignupDTO signupDTO) {
        return signupDTO.getPassword().equals(signupDTO.getConfirmPassword());
    }


}
