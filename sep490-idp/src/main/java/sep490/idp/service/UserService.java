package sep490.idp.service;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.exceptions.BusinessException;
import sep490.idp.dto.EnterpriseUserDetailDTO;
import sep490.idp.dto.NewEnterpriseUserDTO;
import sep490.idp.dto.SignupDTO;
import sep490.idp.dto.SignupResult;
import sep490.idp.dto.UserCriteriaDTO;
import sep490.idp.entity.UserEntity;

import java.util.Set;
import java.util.UUID;

public interface UserService {
    SignupResult signup(SignupDTO signupDTO, Model model);

    Page<UserEntity> search(SearchCriteriaDTO<UserCriteriaDTO> searchCriteria);
    
    void deleteUsers(Set<UUID> userIds) throws BusinessException;
    
    UserEntity createNewUser(NewEnterpriseUserDTO dto) throws BusinessException;
    
    UserEntity getEnterpriseUserDetail(UUID id);
    
    void updateEnterpriseUser(UUID id, EnterpriseUserDetailDTO enterpriseUserDTO) throws BusinessException;
}
