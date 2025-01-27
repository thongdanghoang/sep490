package sep490.common.api;

import java.util.UUID;

public interface BaseEntity {
    UUID getId();
    int getVersion();
}
