package sep490.idp.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.WebAuthnConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import sep490.idp.security.MvcAuthenticationFailureHandler;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Slf4j
@Configuration
public class MvcSecurityConfig {
    
    private static void passkeyConfiguration(WebAuthnConfigurer<HttpSecurity> passkeys) {
        passkeys
                .rpName("SEP490 IDP")
                .rpId("localhost")
                .allowedOrigins("http://localhost:8180");
    }
    
    @Bean
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(
                        c -> c
                                .requestMatchers("/css/**", "/img/**", "/js/**", "/favicon.ico").permitAll()
                                .requestMatchers(antMatcher("/signup"), antMatcher("/login")).permitAll()
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
                .webAuthn(MvcSecurityConfig::passkeyConfiguration)
                .build();
    }
    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new MvcAuthenticationFailureHandler();
    }
    
}
