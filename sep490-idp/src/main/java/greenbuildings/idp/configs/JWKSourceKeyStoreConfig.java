package greenbuildings.idp.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.security.crypto.encrypt.KeyStoreKeyFactory;

@Configuration
public class JWKSourceKeyStoreConfig {
    
    @Bean
    @Profile("default")
    public KeyStoreKeyFactory defaultKeyStoreKeyFactory(
            @Value("${spring.security.keystore.location}") String keystorePath,
            @Value("${spring.security.keystore.password}") String keystorePassword) {
        return new KeyStoreKeyFactory(new ClassPathResource(keystorePath), keystorePassword.toCharArray());
    }
    
    @Bean
    @Profile("prod")
    public KeyStoreKeyFactory prodKeyStoreKeyFactory(
            @Value("${spring.security.keystore.location}") String keystorePath,
            @Value("${spring.security.keystore.password}") String keystorePassword) {
        return new KeyStoreKeyFactory(new FileSystemResource(keystorePath), keystorePassword.toCharArray());
    }
}
