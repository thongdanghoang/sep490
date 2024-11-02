package spotease.common.api.dto;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class AbstractBaseDTO {
    protected UUID id;
    protected Integer version;

    protected AbstractBaseDTO() {
        // Abstract class should not be instantiated
    }
}