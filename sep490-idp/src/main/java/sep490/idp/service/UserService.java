package sep490.idp.service;

import org.springframework.data.domain.Page;
import org.springframework.ui.Model;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.idp.dto.SignupDTO;
import sep490.idp.dto.SignupResult;
import sep490.idp.entity.UserEntity;

public interface UserService {
    SignupResult signup(SignupDTO signupDTO, Model model);

    Page<UserEntity> search(SearchCriteriaDTO<String> searchCriteria);
}
