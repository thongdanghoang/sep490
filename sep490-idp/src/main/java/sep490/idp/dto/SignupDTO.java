package sep490.idp.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import sep490.idp.validation.ToValidated;

import static sep490.common.api.utils.CommonConstant.*;

@Getter
@Setter
public class SignupDTO implements ToValidated {

    @Pattern(regexp = EMAIL_PATTERN, message = "{validation.email.invalid}")
    @Size(min = 1, max = 255, message = "{validation.email.length}")
    private String email;

    @Pattern(regexp = PASSWORD_PATTERN, message = "{validation.password.invalid}")
    private String password;

    @Pattern(regexp = PASSWORD_PATTERN, message = "{validation.confirmPassword.invalid}")
    private String confirmPassword;

    @Pattern(regexp = VIETNAM_PHONE_PATTERN, message = "{validation.phone.invalid}")
    private String phone;

    @Size(min = 1, max = 100, message = "{validation.firstName.invalid}")
    private String firstName;

    @Size(min = 1, max = 100, message = "{validation.lastName.invalid}")
    private String lastName;
}