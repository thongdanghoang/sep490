package green_buildings.commons.api.utils;

import org.apache.commons.codec.binary.StringUtils;
import green_buildings.commons.api.BaseEnum;

import java.util.Arrays;

public final class EnumUtil {

    private EnumUtil() {
        // Utility class, no instances allowed
    }

    public static <T extends BaseEnum> T getCodeFromString(Class<T> clazz, String code) {
        return Arrays.stream(clazz.getEnumConstants())
            .filter(e -> StringUtils.equals(e.getCode(), code))
            .findFirst()
            .orElse(null);
    }

}
