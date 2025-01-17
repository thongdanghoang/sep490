package sep490.idp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.dto.SearchResultDTO;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.service.UserService;

@RestController
@RequestMapping("/api/enterprise-user")
@RequiredArgsConstructor
public class EnterpriseUserRestController {
    private final UserService userService;


    @PostMapping("/search")
    public SearchResultDTO<EnterpriseUserDTO> searchEnterpriseUser(@RequestBody SearchCriteriaDTO<String> searchCriteria) {
        return userService.search(searchCriteria);
    }

}
