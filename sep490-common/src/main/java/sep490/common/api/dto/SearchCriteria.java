package sep490.common.api.dto;

import java.util.List;

public record SearchCriteria<T>(
        Page page,
        List<Sort> sorts,
        T criteria
) {
    public static <T> SearchCriteria<T> of(Page page, List<Sort> sorts, T criteria) {
        return new SearchCriteria<>(page, sorts, criteria);
    }
}

