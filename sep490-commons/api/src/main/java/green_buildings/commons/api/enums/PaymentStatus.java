package green_buildings.commons.api.enums;

import green_buildings.commons.api.BaseEnum;
import lombok.Getter;

@Getter
public enum PaymentStatus implements BaseEnum {
    PENDING("PENDING"),
    PAID("PAID"),
    PROCESSING("PROCESSING"),
    CANCELLED("CANCELLED");
    
    private final String code;
    
    PaymentStatus(String code) {
        this.code = code;
    }
}
