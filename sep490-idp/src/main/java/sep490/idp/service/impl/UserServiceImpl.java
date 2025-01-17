package sep490.idp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.dto.SearchResultDTO;
import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.dto.SignupDTO;
import sep490.idp.dto.SignupResult;
import sep490.idp.entity.UserEntity;
import sep490.idp.mapper.CommonMapper;
import sep490.idp.mapper.EnterpriseUserMapper;
import sep490.idp.repository.UserRepository;
import sep490.idp.service.UserService;
import sep490.idp.validation.Validator;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("signupValidator")
    private final Validator<SignupDTO> validator;
    private final EnterpriseUserMapper userMapper;


    @Override
    public SignupResult signup(SignupDTO signupDTO, Model model) {
        SignupResult result = validateSignupDTO(signupDTO);

        if (result.isSuccess()) {
            var user = createUser(signupDTO);
            userRepo.save(user);
            result.setSuccess(true);
            result.setSuccessMessage("signup.notification");
            result.setRedirectUrl("redirect:/login?message=" + result.getSuccessMessage());
        }

        return result;
    }

    private SignupResult validateSignupDTO(SignupDTO signupDTO) {
        SignupResult result = new SignupResult();
        result.setRedirectUrl("redirect:/login");
        result.setSuccess(true);

        validator.validate(signupDTO);
        if (!signupDTO.isValidated()) {
            result.setSuccess(false);
            result.setErrorMessage(signupDTO.getFirstErrorMsg().orElse(null));
            result.setRedirectUrl("signup");
        }

        return result;
    }


    private UserEntity createUser(SignupDTO signupDTO) {
        return UserEntity.builder()
            .email(signupDTO.getEmail())
            .emailVerified(false)
            .role(UserRole.ENTERPRISE_OWNER)
            .scope(UserScope.ENTERPRISE)
            .firstName(signupDTO.getFirstName())
            .lastName(signupDTO.getLastName())
            .phone(signupDTO.getPhone())
            .phoneVerified(false)
            .password(passwordEncoder.encode(signupDTO.getPassword()))
            .build();
    }

    @Override
    public SearchResultDTO<EnterpriseUserDTO> search(SearchCriteriaDTO<String> searchCriteria) {
        List<UserEntity> userEntities = userRepo.searchByName(searchCriteria.criteria(),
            CommonMapper.toPageable(searchCriteria.page(), searchCriteria.sort()));
        List<EnterpriseUserDTO> result = userEntities.stream().map(userMapper::userEntityToEnterpriseUserDTO).toList();
        long totalElement = userRepo.countAllByName(searchCriteria.criteria());

        return SearchResultDTO.of(result, totalElement);
    }
}
