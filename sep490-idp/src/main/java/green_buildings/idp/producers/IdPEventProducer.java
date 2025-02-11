package green_buildings.idp.producers;

import green_buildings.commons.api.events.PendingEnterpriseRegisterEvent;
import green_buildings.commons.api.utils.MDCContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.core.convert.converter.Converter;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrors;
import org.springframework.security.oauth2.server.resource.InvalidBearerTokenException;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
@RequiredArgsConstructor
@Slf4j
public class IdPEventProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    
    private final HttpServletRequest request;
    private final HttpServletResponse response;
    private final JwtDecoder jwtDecoder;
    private final Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthenticationConverter;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder
            .getContextHolderStrategy();
    private static final SecurityContextRepository securityContextRepository = new HttpSessionSecurityContextRepository();
    
    public static final String BEARER_TOKEN_HEADER_NAME = "Authorization";
    private static final Pattern authorizationPattern = Pattern
            .compile("^Bearer (?<token>[a-zA-Z0-9-._~+/]+=*)$", Pattern.CASE_INSENSITIVE);
    
    // TODO: setup technical user msg.headers().add(BEARER_TOKEN_HEADER_NAME, getBearerToken());
    public void publishEnterpriseOwnerRegisterEvent(String correlationId, PendingEnterpriseRegisterEvent payload) {
        var msg = new ProducerRecord<String, Object>(PendingEnterpriseRegisterEvent.TOPIC, payload);
        msg.headers().add(MDCContext.CORRELATION_ID, correlationId.getBytes(StandardCharsets.UTF_8));
        kafkaTemplate.send(msg);
    }
    
    private byte[] getBearerToken() {
        var tokenValue = ((JwtAuthenticationToken) SecurityContextHolder.getContext().getAuthentication())
                .getToken()
                .getTokenValue();
        return "Bearer %s".formatted(tokenValue).getBytes(StandardCharsets.UTF_8);
    }
    
    protected void processAuthorization(ConsumerRecord<?, ?> consumerRecord) {
        String token;
        try {
            token = resolveFromAuthorizationHeader(new String(consumerRecord.headers().lastHeader(BEARER_TOKEN_HEADER_NAME).value()));
        } catch (OAuth2AuthenticationException invalid) {
            log.trace("Sending to authentication entry point since failed to resolve bearer token", invalid);
            return;
        }
        if (token == null) {
            log.trace("Did not process request since did not find bearer token");
            return;
        }
        
        BearerTokenAuthenticationToken authenticationRequest = new BearerTokenAuthenticationToken(token);
        try {
            Authentication authenticationResult = authenticate(authenticationRequest);
            SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
            context.setAuthentication(authenticationResult);
            this.securityContextHolderStrategy.setContext(context);
            securityContextRepository.saveContext(context, request, response);
            if (log.isDebugEnabled()) {
                log.debug("Set SecurityContextHolder to {}", authenticationResult);
            }
        } catch (AuthenticationException failed) {
            this.securityContextHolderStrategy.clearContext();
            log.trace("Failed to process authentication request", failed);
        }
    }
    
    private Authentication authenticate(Authentication authentication) throws AuthenticationException {
        BearerTokenAuthenticationToken bearer = (BearerTokenAuthenticationToken) authentication;
        Jwt jwt = getJwt(bearer);
        AbstractAuthenticationToken token = this.jwtAuthenticationConverter.convert(jwt);
        token.setDetails(bearer.getDetails());
        log.debug("Authenticated token");
        return token;
    }
    
    private Jwt getJwt(BearerTokenAuthenticationToken bearer) {
        try {
            return this.jwtDecoder.decode(bearer.getToken());
        } catch (BadJwtException failed) {
            log.debug("Failed to authenticate since the JWT was invalid");
            throw new InvalidBearerTokenException(failed.getMessage(), failed);
        } catch (JwtException failed) {
            throw new AuthenticationServiceException(failed.getMessage(), failed);
        }
    }
    
    private String resolveFromAuthorizationHeader(String authorization) {
        if (!StringUtils.startsWithIgnoreCase(authorization, "bearer")) {
            return null;
        }
        Matcher matcher = authorizationPattern.matcher(authorization);
        if (!matcher.matches()) {
            BearerTokenError error = BearerTokenErrors.invalidToken("Bearer token is malformed");
            throw new OAuth2AuthenticationException(error);
        }
        return matcher.group("token");
    }
    
}
