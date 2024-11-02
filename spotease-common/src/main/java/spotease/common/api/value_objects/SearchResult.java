package spotease.common.api.value_objects;

import java.util.List;

public record SearchResult<T>(
        List<T> results,
        long totalElements
) {
    public static <T> SearchResult<T> of(List<T> results, long totalElements) {
        return new SearchResult<>(results, totalElements);
    }
}
