package spotease.common.api.value_objects;

import spotease.common.api.enums.SortDirection;

public record Sort(
        String field,
        SortDirection direction
) {
    public static Sort of(String field, SortDirection direction) {
        return new Sort(field, direction);
    }
}
