package sep490.common.api.dto;

import java.util.List;

public record SearchCriteriaDTO<T>(
        PageDTO page,
        List<SortDTO> sorts,
        T criteria
) {
    public static <T> SearchCriteriaDTO<T> of(PageDTO page, List<SortDTO> sorts, T criteria) {
        return new SearchCriteriaDTO<>(page, sorts, criteria);
    }
}
