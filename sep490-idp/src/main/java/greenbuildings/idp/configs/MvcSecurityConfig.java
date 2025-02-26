package greenbuildings.idp.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.WebAuthnConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import greenbuildings.idp.security.MvcAuthenticationFailureHandler;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Slf4j
@Configuration
public class MvcSecurityConfig {
    
    @Value("${spring.security.passkeys.rpId}")
    private String rpId;
    
    @Value("${spring.security.passkeys.rpName}")
    private String rpName;
    
    @Value("${spring.security.passkeys.allowedOrigins}")
    private String allowedOrigins;
    
    private void passkeyConfiguration(WebAuthnConfigurer<HttpSecurity> passkeys) {
        passkeys
                .rpId(rpId)
                .rpName(rpName)
                .allowedOrigins(allowedOrigins);
    }
    
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        c -> c
                                .requestMatchers("/css/**", "/img/**", "/js/**", "/favicon.ico").permitAll()
                                .requestMatchers(antMatcher("/signup"), antMatcher("/login"), antMatcher("/logout")).permitAll()
                                .requestMatchers(antMatcher("/passkey/login")).permitAll()
                                .requestMatchers(antMatcher("/forgot-password")).permitAll()
                                .requestMatchers(antMatcher("/enter-otp")).permitAll()
                                .requestMatchers(antMatcher("/forgot-reset-password")).permitAll()
                                .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/login")
                                       .usernameParameter("email")
                                       .passwordParameter("password")
                                       .defaultSuccessUrl("/success", false)
                                       .failureHandler(authenticationFailureHandler())
                                       .permitAll())
                .webAuthn(this::passkeyConfiguration)
                .logout(logout -> logout.logoutUrl("/logout"))
                .build();
    }
    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new MvcAuthenticationFailureHandler();
    }
    
}
