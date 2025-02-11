package green_buildings.idp.service;

import green_buildings.commons.api.dto.SearchCriteriaDTO;
import green_buildings.commons.api.exceptions.BusinessErrorResponse;
import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import green_buildings.idp.dto.SignupDTO;
import green_buildings.idp.dto.SignupResult;
import green_buildings.idp.dto.UserCriteriaDTO;
import green_buildings.idp.entity.UserEntity;

import java.util.Set;
import java.util.UUID;

public interface UserService {
    SignupResult signup(SignupDTO signupDTO, Model model);
    
    void commitEnterpriseOwnerCreation(UUID enterpriseId, String correlationId);
    
    void rollbackEnterpriseOwnerCreation(BusinessErrorResponse error);
    
    Page<UserEntity> search(SearchCriteriaDTO<UserCriteriaDTO> searchCriteria);
    
    void deleteUsers(Set<UUID> userIds);
    
    void createOrUpdateEnterpriseUser(UserEntity user);
    
    UserEntity getEnterpriseUserDetail(UUID id);
}
