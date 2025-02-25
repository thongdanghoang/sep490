package enterprise.dtos;

import enterprise.entities.TransactionType;
import jakarta.validation.constraints.Min;

import java.util.UUID;

public record SubscribeRequestDTO(
        UUID buildingId,
        @Min(1) int months,
        @Min(100) int numberOfDevices,
        double monthRatio,
        double deviceRatio,
        TransactionType type) {
}
