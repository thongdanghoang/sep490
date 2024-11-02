package spotease.common.api.value_objects;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record SearchResult<T>(
        @NotNull List<T> results,
        long totalElements
) {
    public static <T> SearchResult<T> of(@NotNull List<T> results, long totalElements) {
        return new SearchResult<>(results, totalElements);
    }
}
