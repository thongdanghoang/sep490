package sep490.idp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import sep490.idp.validation.ToValidated;

import static sep490.common.api.utils.CommonConstant.EMAIL_PATTERN;

@Getter
@Setter
public class SignupDTO implements ToValidated {

    @Pattern(regexp = EMAIL_PATTERN, message = "{validation.email.invalid}")
    private String email;

//    @Pattern(regexp = "", message = "{validation.password.invalid}")
    private String password;

//    @Pattern(regexp = "", message = "{validation.confirmPassword.invalid}")
    private String confirmPassword;

//    @Pattern(regexp = "", message = "{validation.phone.invalid}")
    private String phone;

//    @Pattern(regexp = "", message = "{validation.firstName.invalid}")
    private String firstName;

//    @Pattern(regexp = "", message = "{validation.lastName.invalid}")
    private String lastName;
}