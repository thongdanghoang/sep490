package sep490.idp.service;

import sep490.idp.dto.passkeys.CredentialsRegistration;
import sep490.idp.dto.passkeys.CredentialsVerification;
import sep490.idp.entity.UserEntity;

/**
 * Interface for managing the registration and verification of authenticators.
 * Reference: https://webauthn4j.github.io/webauthn4j/en/#feature
 */
public interface AuthenticatorService {

    void saveCredentials(CredentialsRegistration registration, UserEntity user);

    UserEntity authenticate(CredentialsVerification verification);

    boolean deleteCredential(UserEntity user, String credentialId);
}
