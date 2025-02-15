package enterprise.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import green_buildings.commons.api.enums.StatusPayment;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.UUID;

public record PaymentDTO(
        UUID id,
        @JsonFormat(pattern = "dd/MM/yyyy") LocalDateTime createdDate,
        StatusPayment status,
        @NotNull long amount
) {
}
