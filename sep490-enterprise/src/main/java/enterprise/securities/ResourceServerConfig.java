package enterprise.securities;

import commons.springfw.impl.audit.AuditorAwareImpl;
import commons.springfw.impl.filters.MonitoringFilter;
import commons.springfw.impl.securities.CorsConfig;
import commons.springfw.impl.securities.JwtAuthenticationConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableMethodSecurity(jsr250Enabled = true)
public class ResourceServerConfig {
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
    
    @Bean
    public JwtAuthenticationConverter converter() {
        return new JwtAuthenticationConverter();
    }
    
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        return CorsConfig.corsConfigurationSource();
    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(c -> c.jwt(j -> j.jwtAuthenticationConverter(converter())))
                .authorizeHttpRequests(c -> c
                        .requestMatchers("/actuator/health/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterAfter(new MonitoringFilter(), SecurityContextHolderFilter.class)
                .cors(Customizer.withDefaults())
                .build();
    }
    
}
