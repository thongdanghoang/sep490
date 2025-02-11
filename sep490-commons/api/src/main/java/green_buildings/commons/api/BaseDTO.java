package green_buildings.commons.api;

import java.util.UUID;

public interface BaseDTO {
    UUID id();
    int version();
}
