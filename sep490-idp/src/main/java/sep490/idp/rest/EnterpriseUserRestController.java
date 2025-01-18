package sep490.idp.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.dto.SearchResultDTO;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.mapper.EnterpriseUserMapper;
import sep490.idp.service.UserService;

@RestController
@RequestMapping("/api/enterprise-user")
@RequiredArgsConstructor
public class EnterpriseUserRestController {
    
    private final UserService userService;
    private final EnterpriseUserMapper userMapper;
    
    @PostMapping("/search")
    public ResponseEntity<SearchResultDTO<EnterpriseUserDTO>> searchEnterpriseUser(@RequestBody SearchCriteriaDTO<String> searchCriteria) {
        var searchResults = userService.search(searchCriteria);
        if (searchResults.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        var results = searchResults.get().map(userMapper::userEntityToEnterpriseUserDTO).toList();
        var totalElements = searchResults.getTotalElements();
        return ResponseEntity.ok(SearchResultDTO.of(results, totalElements));
    }
    
}
