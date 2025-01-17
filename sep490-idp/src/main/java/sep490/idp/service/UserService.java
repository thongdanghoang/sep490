package sep490.idp.service;

import org.springframework.ui.Model;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.dto.SearchResultDTO;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.dto.SignupDTO;
import sep490.idp.dto.SignupResult;

public interface UserService {
    SignupResult signup(SignupDTO signupDTO, Model model);

    SearchResultDTO<EnterpriseUserDTO> search(SearchCriteriaDTO<String> searchCriteria);
}
