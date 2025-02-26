package greenbuildings.enterprise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@ComponentScan(basePackages = {"greenbuildings.enterprise", "commons.springfw"})
@EnableJpaAuditing
public class EnterpriseApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(EnterpriseApplication.class, args);
    }
    
}
