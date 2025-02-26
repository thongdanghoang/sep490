package greenbuildings.enterprise.dtos;

public record AddressSuggestionDTO(
        String displayName,
        String road,
        String quarter,
        String suburb,
        String city,
        String postcode,
        String country,
        String countryCode
) {
}
