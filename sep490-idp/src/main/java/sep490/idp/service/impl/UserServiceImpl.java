package sep490.idp.service.impl;

import commons.springfw.impl.mappers.CommonMapper;
import commons.springfw.impl.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.exceptions.BusinessErrorParam;
import sep490.common.api.exceptions.BusinessException;
import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;
import sep490.common.api.utils.CommonUtils;
import sep490.idp.dto.SignupDTO;
import sep490.idp.dto.SignupResult;
import sep490.idp.dto.UserCriteriaDTO;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserRepository;
import sep490.idp.service.UserService;
import sep490.idp.utils.IEmailUtil;
import sep490.idp.utils.IMessageUtil;
import sep490.idp.utils.SEPMailMessage;
import sep490.idp.validation.Validator;
import sep490.idp.validators.UserValidator;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(rollbackFor = Throwable.class)
public class UserServiceImpl implements UserService {
    
    private final UserRepository userRepo;
    private final UserValidator userValidator;
    private final PasswordEncoder passwordEncoder;
    @Qualifier("signupValidator")
    private final Validator<SignupDTO> validator;
    private final IMessageUtil messageUtil;
    private final IEmailUtil emailUtil;
    @Value("${spring.application.homepage}")
    private String homepage;
    
    
    @Override
    public SignupResult signup(SignupDTO signupDTO, Model model) {
        SignupResult result = validateSignupDTO(signupDTO);
        
        if (!result.isSuccess()) {
            return result;
        }
        var user = createEnterpriseOwner(signupDTO);
        userRepo.save(user);
        result.setSuccess(true);
        result.setSuccessMessage("signup.notification");
        result.setRedirectUrl("redirect:/login?message=" + result.getSuccessMessage());
        
        // TODO: send enterpriseName and taxCode in dto
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
    
    
    private UserEntity createEnterpriseOwner(SignupDTO signupDTO) {
        return UserEntity.register(
                signupDTO.getEmail(),
                false,
                UserRole.ENTERPRISE_OWNER,
                UserScope.ENTERPRISE,
                signupDTO.getFirstName(),
                signupDTO.getLastName(),
                signupDTO.getPhone(),
                false,
                passwordEncoder.encode(signupDTO.getPassword()));
    }
    
    @Override
    public Page<UserEntity> search(SearchCriteriaDTO<UserCriteriaDTO> searchCriteria) {
        UUID enterpriseId = SecurityUtils.getCurrentUserEnterpriseId().orElseThrow();
        var userIDs = userRepo.findByName(
                searchCriteria.criteria().criteria(),
                enterpriseId,
                CommonMapper.toPageable(searchCriteria.page(), searchCriteria.sort()));
        var results = userRepo
                .findByIDsWithPermissions(userIDs.toSet())
                .stream()
                .collect(Collectors.toMap(UserEntity::getId, Function.identity()));
        return userIDs.map(results::get);
    }
    
    @Override
    public void deleteUsers(Set<UUID> userIds) {
        if (CollectionUtils.isEmpty(userIds)) {
            throw new BusinessException("userIds", "user.delete.no.ids", Collections.emptyList());
        }
        var users = userRepo.findByIDs(userIds);
        if (users.size() != userIds.size()) {
            userIds.removeAll(users.stream().map(UserEntity::getId).collect(Collectors.toSet()));
            throw new BusinessException("userIds", "user.delete.not.found",
                                        List.of(new BusinessErrorParam("ids", userIds)));
        }
        // Stream approach to check if any user has the ENTERPRISE_OWNER role
        if (users.stream().anyMatch(user -> user.getEnterprise().getRole() == UserRole.ENTERPRISE_OWNER)) {
            throw new BusinessException("userIds", "user.cannot.delete.owner");
        }
        userRepo.deleteAll(users);
    }
    
    @Override
    public void createOrUpdateEnterpriseUser(UserEntity user) {
        userValidator.validateEnterpriseOwnerManageEmployees(user);
        this.performCreateUserAction(user);
        userRepo.save(user);
    }
    
    private void performCreateUserAction(UserEntity user) {
        // Perform create action when create new
        if (Objects.isNull(user.getId())) {
            var password = CommonUtils.alphaNumericString(12);
            user.setPassword(passwordEncoder.encode(password));
            
            var message = sendPasswordToUserByEmail(user.getEmail(), password);
            emailUtil.sendMail(message);
        }
    }
    
    private SEPMailMessage sendPasswordToUserByEmail(String email, String password) {
        SEPMailMessage message = new SEPMailMessage();
        
        message.setTemplateName("new-user-notify.ftl");
        message.setTo(email);
        message.setSubject(messageUtil.getMessage("newUser.mail.title"));
        
        message.addTemplateModel("userEmail", email);
        message.addTemplateModel("password", password);
        message.addTemplateModel("homepage", homepage);
        
        return message;
    }
    
    @Override
    public UserEntity getEnterpriseUserDetail(UUID id) {
        return userRepo.findByIdWithBuildingPermissions(id).orElseThrow();
    }
    
}
