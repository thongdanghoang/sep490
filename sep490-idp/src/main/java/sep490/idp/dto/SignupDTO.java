package sep490.idp.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupDTO {
    // TODO: implement validation
    @Email(message = "Invalid email format")
    @NotBlank(message = "Email is required")
    private String email;

    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;

    @NotBlank(message = "Confirm Password is required")
    private String confirmPassword;

    @Pattern(regexp = "^\\d+$", message = "Phone must contain only digits")
    @Size(max = 16, message = "Phone number must not exceed 16 characters")
    @NotBlank(message = "Phone is required")
    private String phone;

    @Size(max = 50, message = "First name must not exceed 50 characters")
    @NotBlank(message = "First name is required")
    private String firstName;

    @Size(max = 100, message = "Last name must not exceed 100 characters")
    @NotBlank(message = "Last name is required")
    private String lastName;
}