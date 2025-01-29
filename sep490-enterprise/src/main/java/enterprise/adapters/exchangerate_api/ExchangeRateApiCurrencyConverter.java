package enterprise.adapters.exchangerate_api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sep490.common.api.adapters.CurrencyConverter;
import sep490.common.api.enums.Currency;
import sep490.common.api.exceptions.TechnicalException;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
@RequiredArgsConstructor
public class ExchangeRateApiCurrencyConverter implements CurrencyConverter {
    
    private final RestTemplate restTemplate;
    
    @Value("${exchangerate-api.key}")
    private String key;
    
    @Value("${exchangerate-api.url}")
    private String url;
    
    protected ExchangeRateApiResponse getExchangeRates() {
        var exchangeUrl = url + key + "/latest/USD";
        return restTemplate.getForObject(exchangeUrl, ExchangeRateApiResponse.class);
    }
    
    @Override
    public BigDecimal convert(BigDecimal amount, Currency fromCurrency, Currency toCurrency) {
        if (fromCurrency == toCurrency) {
            return amount;
        }
        
        var exchangeRateApiResponse = getExchangeRates();
        var fromRate = getRate(exchangeRateApiResponse.conversion_rates(), fromCurrency);
        var toRate = getRate(exchangeRateApiResponse.conversion_rates(), toCurrency);
        
        // Convert the amount to USD first, then to the target currency
        var amountInUSD = amount.divide(fromRate, 10, RoundingMode.HALF_UP);
        return amountInUSD.multiply(toRate).setScale(2, RoundingMode.HALF_UP);
    }
    
    @Override
    public BigDecimal convert(long amount, Currency toCurrency) {
        // Assuming the default currency is USD
        var amountInUSD = BigDecimal.valueOf(amount).divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        return convert(amountInUSD, Currency.USD, toCurrency);
    }
    
    @Override
    public long defaultCurrencyConvert(BigDecimal amount, Currency fromCurrency) {
        // Assuming the default currency is USD
        var amountInUSD = convert(amount, fromCurrency, Currency.USD);
        return amountInUSD.multiply(BigDecimal.valueOf(100)).longValue();
    }
    
    private BigDecimal getRate(ConversionRates rates, Currency currency) {
        return switch (currency) {
            case USD -> rates.USD();
            case CNY -> rates.CNY();
            case VND -> rates.VND();
            default -> throw new TechnicalException("ExchangeRateApiCurrencyConverter: Unsupported currency " + currency);
        };
    }
}
