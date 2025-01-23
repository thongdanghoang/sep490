package sep490.idp.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sep490.common.api.dto.SearchCriteriaDTO;
import sep490.common.api.dto.SearchResultDTO;
import sep490.common.api.exceptions.BusinessException;
import sep490.idp.dto.EnterpriseUserDTO;
import sep490.idp.dto.NewEnterpriseUserDTO;
import sep490.idp.dto.UserCriteriaDTO;
import sep490.idp.mapper.EnterpriseUserMapper;
import sep490.idp.service.UserService;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/enterprise-user")
@RequiredArgsConstructor
public class EnterpriseUserRestController {
    
    private final UserService userService;
    private final EnterpriseUserMapper userMapper;
    
    @PostMapping("/search")
    public ResponseEntity<SearchResultDTO<EnterpriseUserDTO>> searchEnterpriseUser(@RequestBody SearchCriteriaDTO<UserCriteriaDTO> searchCriteria) {
        var searchResults = userService.search(searchCriteria);
        if (searchResults.isEmpty()) {
            return ResponseEntity.ok(SearchResultDTO.of(List.of(), searchResults.getTotalElements()));
        }
        var results = searchResults.get().map(userMapper::userEntityToEnterpriseUserDTO).toList();
        var totalElements = searchResults.getTotalElements();
        return ResponseEntity.ok(SearchResultDTO.of(results, totalElements));
    }
    
    @PostMapping("/create")
    public ResponseEntity<?> createNewEnterpriseUser(@Valid @RequestBody NewEnterpriseUserDTO dto) throws BusinessException {
        userService.createNewUser(dto);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/delete")
    public ResponseEntity deleteUsers(@RequestBody Set<UUID> userIds) throws BusinessException {
          userService.deleteUsers(userIds);
         return ResponseEntity.noContent().build();
    }
    
    
}
