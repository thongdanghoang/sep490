package sep490.common.api;

import java.time.LocalDateTime;

public interface AuditableEntity {
    String getCreatedBy();
    LocalDateTime getCreatedDate();
    String getLastModifiedBy();
    LocalDateTime getLastModifiedDate();
}
