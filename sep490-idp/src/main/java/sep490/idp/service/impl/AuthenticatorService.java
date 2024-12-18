package sep490.idp.service.impl;


import com.webauthn4j.WebAuthnManager;
import com.webauthn4j.authenticator.AuthenticatorImpl;
import com.webauthn4j.converter.AttestationObjectConverter;
import com.webauthn4j.converter.exception.DataConversionException;
import com.webauthn4j.converter.util.ObjectConverter;
import com.webauthn4j.data.AuthenticationParameters;
import com.webauthn4j.data.AuthenticationRequest;
import com.webauthn4j.data.client.Origin;
import com.webauthn4j.data.client.challenge.DefaultChallenge;
import com.webauthn4j.server.ServerProperty;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import jakarta.validation.ValidationException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;
import sep490.idp.dto.CredentialsRegistration;
import sep490.idp.dto.CredentialsVerification;
import sep490.idp.entity.UserAuthenticator;
import sep490.idp.entity.UserEntity;
import sep490.idp.repository.UserAuthenticatorRepository;

import java.util.Base64;

@Component
public class AuthenticatorService {

    private final UserAuthenticatorRepository repository;

    private final WebAuthnManager webAuthnManager = WebAuthnManager.createNonStrictWebAuthnManager();

    private final HttpServletRequest request;

    private final AttestationObjectConverter attestationConverter = new AttestationObjectConverter(
            new ObjectConverter());

    public AuthenticatorService(UserAuthenticatorRepository repository, HttpServletRequest request) {
        this.repository = repository;
        this.request = request;
    }

    public void saveCredentials(CredentialsRegistration registration, UserEntity user) {
        var attestationObject = Base64.getUrlDecoder()
                .decode(registration.credentials().response().attestationObject());
        // TODO: you should absolutely validate the attestation, not trust it blindly!

        var serializedAuthenticator = UserAuthenticator.builder()
                .id(registration.credentials().id())
                .user(user)
                .credentialsName(registration.name())
                .attestationObject(attestationObject)
                .build();
        repository.save(serializedAuthenticator);
    }

    public UserEntity authenticate(CredentialsVerification verification, String challenge) throws AuthenticationException {
        var userAuthenticator = repository.findById(verification.id())
                .orElseThrow(() -> new BadCredentialsException("Unknown credentials"));
        var attestation = attestationConverter.convert(userAuthenticator.getAttestationObject());

        var authenticator = new AuthenticatorImpl(attestation.getAuthenticatorData().getAttestedCredentialData(),
                attestation.getAttestationStatement(), attestation.getAuthenticatorData().getSignCount());

        var clientDataJSON = Base64.getUrlDecoder().decode(verification.response().clientDataJSON());
        var authenticatorData = Base64.getUrlDecoder().decode(verification.response().authenticatorData());
        var signature = Base64.getUrlDecoder().decode(verification.response().signature());
        var b64Challenge = Base64.getUrlEncoder().encodeToString(challenge.getBytes());

        var serverProperty = new ServerProperty(getOrigin(), getRpId(), new DefaultChallenge(b64Challenge), null);

        var authenticationRequest = new AuthenticationRequest(verification.id().getBytes(), authenticatorData,
                clientDataJSON, signature);
        var authenticationParameters = new AuthenticationParameters(serverProperty, authenticator, false);

        try {
            webAuthnManager.validate(authenticationRequest, authenticationParameters);

            // TODO: required per spec, to allow for authenticator clone detection, see:
            // https://www.w3.org/TR/webauthn-3/#sctn-sign-counter
            // userAuthenticator.setCounter(authenticationData.getAuthenticatorData().getSignCount());
        }
        catch (DataConversionException e) {
            // If you would like to handle WebAuthn data structure parse error, please
            // catch DataConversionException
            throw e;
        }
        catch (ValidationException e) {
            // If you would like to handle WebAuthn data validation error, please catch
            // ValidationException
            throw e;
        }
        return userAuthenticator.getUser();
    }

    @Transactional
    public boolean deleteCredential(UserEntity user, String credentialId) {
        return repository.deleteByIdAndUser(credentialId, user) > 0;
    }

    private String getRpId() {
        return UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString()).build().getHost();
    }

    private Origin getOrigin() {
        var origin = UriComponentsBuilder.fromHttpUrl(request.getRequestURL().toString())
                .replacePath(null)
                .toUriString();
        return new Origin(origin);
    }
}
