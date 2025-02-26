package greenbuildings.idp.dto.passkeys;

public record CredentialsVerification(String id, Response response) {
    public record Response(String authenticatorData, String clientDataJSON, String signature, String userHandle) {
    }
}

