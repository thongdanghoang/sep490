package sep490.idp.service;

import org.springframework.ui.Model;
import sep490.idp.dto.SignupDTO;

public interface UserService {
    String signup(SignupDTO signupDTO, Model model);
}
