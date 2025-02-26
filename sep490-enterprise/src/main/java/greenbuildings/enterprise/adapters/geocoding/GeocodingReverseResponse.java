package greenbuildings.enterprise.adapters.geocoding;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * This DTO is intended for internal use within the adapter and should not be used outside of it.
 */
public record GeocodingReverseResponse(
        String lat,
        String lon,
        @JsonProperty("display_name") String displayName,
        GeocodingAddress address
) {
}
