package enterprise.adapters.exchangerate_api;

import java.math.BigDecimal;

/**
 * A record representing the conversion rates from the ExchangeRate API.
 * This DTO is intended for internal use within the adapter and should not be used outside of it.
 */
public record ConversionRates(
        BigDecimal USD,
        BigDecimal CNY,
        BigDecimal VND
) {
}
