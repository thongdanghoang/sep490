package sep490.idp.configs;

import jakarta.annotation.PostConstruct;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.payos.PayOS;

@Configuration
public class PaymentConfig {
    
    @Value("${payment.payos.client_id}")
    private String clientId;
    
    @Value("${payment.payos.api_key}")
    private String apiKey;
    
    @Value("${payment.payos.checksum_key}")
    private String checksumKey;
    
    @PostConstruct
    public void validateProps() {
        if (StringUtils.isAnyBlank(clientId, apiKey, checksumKey)) {
            throw new IllegalStateException("PayOS credentials must not be blank");
        }
    }
    
    @Bean
    public PayOS payOS() {
        return new PayOS(clientId, apiKey, checksumKey);
    }
    
}
