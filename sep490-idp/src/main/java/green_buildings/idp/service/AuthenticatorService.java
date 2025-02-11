package green_buildings.idp.service;

import green_buildings.idp.dto.passkeys.CredentialsRegistration;
import green_buildings.idp.dto.passkeys.CredentialsVerification;
import green_buildings.idp.entity.UserEntity;

/**
 * Interface for managing the registration and verification of authenticators.
 * Reference: https://webauthn4j.github.io/webauthn4j/en/#feature
 */
public interface AuthenticatorService {

    void saveCredentials(CredentialsRegistration registration, UserEntity user);

    UserEntity authenticate(CredentialsVerification verification);

    boolean deleteCredential(UserEntity user, String credentialId);
}
