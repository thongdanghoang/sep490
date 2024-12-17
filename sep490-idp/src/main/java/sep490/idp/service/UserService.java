package sep490.idp.service;

import org.springframework.ui.Model;
import sep490.idp.dto.SignupDTO;
import sep490.idp.dto.SignupResult;

public interface UserService {
    SignupResult signup(SignupDTO signupDTO, Model model);
}
