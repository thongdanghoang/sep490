package sep490.common.api.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import sep490.common.api.BaseEnum;

@Getter
@RequiredArgsConstructor
public enum UserScope implements BaseEnum {
    BUILDING("building"),
    ENTERPRISE("enterprise");

    private final String code;
}
