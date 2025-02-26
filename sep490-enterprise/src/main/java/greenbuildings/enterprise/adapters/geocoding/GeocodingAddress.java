package greenbuildings.enterprise.adapters.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This DTO is intended for internal use within the adapter and should not be used outside of it.
 */
public record GeocodingAddress(
        String road,
        String quarter,
        String suburb,
        String city,
        String postcode,
        String country,
        @JsonProperty("country_code") String countryCode
) {
}
