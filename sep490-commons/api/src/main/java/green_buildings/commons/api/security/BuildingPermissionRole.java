package green_buildings.commons.api.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import green_buildings.commons.api.BaseEnum;

@Getter
@RequiredArgsConstructor
public enum BuildingPermissionRole implements BaseEnum {
    MANAGER("manager"),
    AUDITOR("auditor"),
    STAFF("staff");

    private final String code;
}
