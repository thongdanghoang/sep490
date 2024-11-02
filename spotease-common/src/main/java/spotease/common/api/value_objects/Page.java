package spotease.common.api.value_objects;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

/**
 * Represents pagination parameters for API requests.
 *
 * @param pageSize   The number of items per page (1-100)
 * @param pageNumber The zero-based page number
 */
public record Page(
        @Min(1)
        int pageSize,
        @Min(0) @Max(100)
        int pageNumber
) {
    public static Page of(int pageSize, int pageNumber) {
        return new Page(pageSize, pageNumber);
    }
}
