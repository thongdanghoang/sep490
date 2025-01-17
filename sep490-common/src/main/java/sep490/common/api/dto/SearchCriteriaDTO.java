package sep490.common.api.dto;


import jakarta.validation.constraints.NotNull;

public record SearchCriteriaDTO<T>(
        PageDTO page,
        @NotNull SortDTO sort,
        T criteria
) {
    public static <T> SearchCriteriaDTO<T> of(PageDTO page, SortDTO sort, T criteria) {
        return new SearchCriteriaDTO<>(page, sort, criteria);
    }
}
