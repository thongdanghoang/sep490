package spotease.common.api.value_objects;

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

