package greenbuildings.idp.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import static greenbuildings.commons.api.utils.CommonConstant.PASSWORD_PATTERN;

@Getter
@Setter
public class ForgotResetPasswordDTO {
    @Pattern(regexp = PASSWORD_PATTERN, message = "{validation.password.invalid}")
    private String password;

    @Pattern(regexp = PASSWORD_PATTERN, message = "{validation.confirmPassword.invalid}")
    private String confirmPassword;
}
