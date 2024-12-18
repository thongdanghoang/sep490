package sep490.idp.dto;

public record CredentialsRegistration(String name, AuthenticatorCredentials credentials) {

    public record AuthenticatorCredentials(String id, Response response) {
    }

    public record Response(String attestationObject, String clientDataJSON) {
    }
}

