package green_buildings.commons.api.enums;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import green_buildings.commons.api.BaseEnum;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public enum Currency implements BaseEnum {
    USD("currency.usd"),
    VND("currency.vnd"),
    CNY("currency.cny");
    
    private final String code;
}
