package greenbuildings.idp.dto.passkeys;

public record CredentialsRegistration(String name, AuthenticatorCredentials credentials) {

    // Reference: https://webauthn.guide/#registration
    // This object is return from Client-Authenticator such as WindowHello

    // ID: newly generated credential ID as base-64 encoded string
    // ClientDataJson: The data passed from browser to authenticator to create new credential as UTF-8 byte array
    // AttestationObject: The object contains credential public key ...
    public record AuthenticatorCredentials(String id, Response response) {
    }

    public record Response(String attestationObject, String clientDataJSON) {
    }
}

