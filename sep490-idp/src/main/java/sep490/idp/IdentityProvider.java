package sep490.idp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class IdentityProvider {

    public static void main(String[] args) {
        SpringApplication.run(IdentityProvider.class, args);
    }

}
