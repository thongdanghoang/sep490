package sep490.common.api.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sep490.common.api.BaseEnum;

@Getter
@RequiredArgsConstructor
public enum BuildingPermissionRole implements BaseEnum {
    MANAGER("manager"),
    AUDITOR("auditor"),
    STAFF("staff");

    private final String code;
}
