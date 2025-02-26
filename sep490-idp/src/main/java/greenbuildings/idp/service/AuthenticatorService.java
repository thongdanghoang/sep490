package greenbuildings.idp.service;

import greenbuildings.idp.dto.passkeys.CredentialsRegistration;
import greenbuildings.idp.dto.passkeys.CredentialsVerification;
import greenbuildings.idp.entity.UserEntity;

/**
 * Interface for managing the registration and verification of authenticators.
 * Reference: https://webauthn4j.github.io/webauthn4j/en/#feature
 */
public interface AuthenticatorService {

    void saveCredentials(CredentialsRegistration registration, UserEntity user);

    UserEntity authenticate(CredentialsVerification verification);

    boolean deleteCredential(UserEntity user, String credentialId);
}
