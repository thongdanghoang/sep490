package sep490.idp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupResult {
    private String errorMessage;
    private boolean success;
    private String redirectUrl;
}
