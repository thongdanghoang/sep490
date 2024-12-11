package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import sep490.idp.dto.SignupDTO;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserRepository;
import sep490.idp.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public String signup(SignupDTO signupDTO, Model model) {
        if (!signupDTO.getPassword().equals(signupDTO.getConfirmPassword())) {
            model.addAttribute("error", "Passwords do not match");
            return "signup";
        }
        
        if (userRepo.existsByEmail(signupDTO.getEmail())) {
            model.addAttribute("error", "Email is already registered");
            return "signup";
        }
        
        var user = new UserEntity();
        user.setEmail(signupDTO.getEmail());
        user.setPassword(passwordEncoder.encode(signupDTO.getPassword()));
        
        userRepo.save(user);
        
        return "redirect:/login";
    }
}
