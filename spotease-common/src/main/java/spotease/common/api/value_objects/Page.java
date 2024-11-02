package spotease.common.api.value_objects;

public record Page(
        int pageSize,
        int pageNumber
) {
    public static Page of(int pageSize, int pageNumber) {
        return new Page(pageSize, pageNumber);
    }
}
