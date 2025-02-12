package green_buildings.idp.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import green_buildings.idp.validation.ToBeValidated;

import static green_buildings.commons.api.utils.CommonConstant.*;

@Getter
@Setter
public class SignupDTO extends ToBeValidated {

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
    
    @Size(min = 1, max = 255, message = "{validation.enterpriseName.invalid}")
    private String enterpriseName;
    
    @Pattern(regexp = VIETNAME_TAX_CODE, message = "{validation.enterpriseTaxCode.invalid}")
    private String enterpriseTaxNumber;
    
    @Pattern(regexp = VIETNAM_ENTERPRISE_HOTLINE_PATTERN, message = "{validation.enterpriseHotline.invalid}")
    private String enterpriseHotline;
}