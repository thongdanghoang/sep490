package sep490.idp.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import sep490.common.api.security.UserRole;
import sep490.common.api.security.UserScope;
import sep490.common.api.utils.CommonConstant;

import java.util.UUID;

public record EnterpriseUserDTO(
        UUID id,
        @NotBlank String name,
        @NotBlank @Pattern(regexp = CommonConstant.EMAIL_PATTERN) String email,
        @NotNull UserRole role,
        @NotNull UserScope scope) {
}
