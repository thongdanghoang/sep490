package enterprise.dtos;

import java.util.UUID;

public record CreditPackageDTO(UUID id, int version, int numberOfCredits, long price) {
}
