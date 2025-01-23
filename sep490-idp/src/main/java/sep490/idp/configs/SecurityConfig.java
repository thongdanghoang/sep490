package sep490.idp.configs;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.encrypt.KeyStoreKeyFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sep490.idp.service.impl.CustomAuthenticationFailureHandler;
import sep490.idp.service.impl.UserInfoService;

import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
public class SecurityConfig {
    
    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.setAllowedOrigins(List.of("http://localhost:4200"));
        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        corsConfig.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        corsConfig.setAllowCredentials(true);
        corsConfig.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfig);
        return source;
    }
    
    @Bean
    @Order(1)
    public SecurityFilterChain asFilterChain(HttpSecurity http)
            throws Exception {
        // reference: https://docs.spring.io/spring-authorization-server/reference/guides/how-to-userinfo.html
        // TODO: solve deprecated
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
            .oidc(Customizer.withDefaults());
        http
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .exceptionHandling(exceptions -> exceptions
                                           .defaultAuthenticationEntryPointFor(
                                                   new LoginUrlAuthenticationEntryPoint("/login"),
                                                   new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                                                                              )
                                  );
        return http.build();
    }
    
    @Bean
    @Order(2)
    public SecurityFilterChain jwtFilterChain(HttpSecurity http) throws Exception {
        http
                .securityMatcher("/api/**")
                .authorizeHttpRequests(authorize -> authorize.anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(resourceServer -> resourceServer.jwt(Customizer.withDefaults()));
        return http.build();
    }
    
    @Bean
    @Order(3)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        
        http.cors(cors -> cors.configurationSource(corsConfigurationSource()));
        
        http.authorizeHttpRequests(
                c -> c
                        .requestMatchers("/css/**", "/img/**", "/js/**", "/favicon.ico").permitAll()
                        .requestMatchers(antMatcher("/signup"), antMatcher("/login")).permitAll()
                        .requestMatchers(antMatcher("/passkey/login")).permitAll()
                        .requestMatchers(antMatcher("/forgot-password"), antMatcher("/enter-otp"), antMatcher("/forgot-reset-password")).permitAll()
                        .anyRequest().authenticated());
        
        http.formLogin(form -> form.loginPage("/login")
                                   .usernameParameter("email")
                                   .passwordParameter("password")
                                   .defaultSuccessUrl("/success", false)
                                   .failureHandler(authenticationFailureHandler())
                                   .permitAll());
        
        // Passkey Configuration
        http.webAuthn(passkeys ->
                              passkeys.rpName("SEP490 IDP").rpId("localhost").allowedOrigins("http://localhost:8180"));
        
        return http.build();
    }
    
    
    @Bean
    public AuthenticationFailureHandler authenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public JWKSource<SecurityContext> jwkSource()
            throws NoSuchAlgorithmException {
        var keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("keystore.jks"), "P@ssW0rd".toCharArray());
        var keyPair = keyStoreKeyFactory.getKeyPair("GreenBuildings");
        var publicKey = (RSAPublicKey) keyPair.getPublic();
        var privateKey = (RSAPrivateKey) keyPair.getPrivate();
        var rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID("kid")
                .build();
        var jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }
    
    @Bean
    public OAuth2TokenCustomizer<JwtEncodingContext> jwtCustomizer(UserInfoService userInfoService) {
        return context -> {
            if (OidcParameterNames.ID_TOKEN.equals(context.getTokenType().getValue())) {
                OidcUserInfo oidcUserInfo = userInfoService.loadUser(context.getPrincipal().getName());
                context.getClaims().claims(claimsConsumer -> claimsConsumer.putAll(oidcUserInfo.getClaims()));
            } else if (OAuth2TokenType.ACCESS_TOKEN.equals(context.getTokenType())) {
                Map<String, Object> customClaims = userInfoService.getCustomClaimsForJwtAuthenticationToken(context.getPrincipal().getName());
                context.getClaims().claims(claimsConsumer -> claimsConsumer.putAll(customClaims));
            }
        };
    }
    
    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }
}
