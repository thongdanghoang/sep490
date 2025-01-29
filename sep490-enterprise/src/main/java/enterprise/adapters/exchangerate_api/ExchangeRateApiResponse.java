package enterprise.adapters.exchangerate_api;

/**
 * A record representing the response from the ExchangeRate API.
 * This DTO is intended for internal use within the adapter and should not be used outside of it.
 */
public record ExchangeRateApiResponse(
        String result,
        String documentation,
        String terms_of_use,
        long time_last_update_unix,
        String time_last_update_utc,
        long time_next_update_unix,
        String time_next_update_utc,
        String base_code,
        ConversionRates conversion_rates
) {
}
