package spotease.common.api.value_objects;

import spotease.common.api.enums.DateBoundary;

import java.time.LocalDate;

public record DateRange(
        LocalDate from,
        LocalDate to,
        DateBoundary fromBoundary,
        DateBoundary toBoundary
) {

    public static DateRange of(LocalDate from, LocalDate to, DateBoundary fromBoundary, DateBoundary toBoundary) {
        return new DateRange(from, to, fromBoundary, toBoundary);
    }

    public DateRange {
        if (from.isAfter(to)) {
            throw new IllegalArgumentException("Start date must be before or equal to end date.");
        }
    }

    public boolean isDateInRange(LocalDate date) {
        boolean startCondition =
                fromBoundary == DateBoundary.INCLUSIVE
                        ? !date.isBefore(from)
                        : date.isAfter(from);
        boolean endCondition =
                toBoundary == DateBoundary.INCLUSIVE
                        ? !date.isAfter(to)
                        : date.isBefore(to);
        return startCondition && endCondition;
    }
}
