package sep490.common.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import sep490.common.api.enums.SortDirection;

/**
 * Represents a sorting specification with a field name and direction.
 *
 * @param field     The name of the field to sort by
 * @param direction The direction (ascending or descending) to sort in
 */
public record Sort(
        @NotBlank
        String field,
        @NotNull
        SortDirection direction
) {
    public static Sort of(String field, SortDirection direction) {
        return new Sort(field, direction);
    }
}
