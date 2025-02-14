package green_buildings.commons.api.exceptions;

import green_buildings.commons.api.enums.ServerErrorType;

/**
 * Model of a server error to be sent to client in case error happens in the backend.
 * It contains http status and error detail.
 */
public record TechnicalErrorResponse(
        String correlationId,
        String message
) implements ServerErrorResponse {
    @Override
    public ServerErrorType getErrorType() {
        return ServerErrorType.TECHNICAL;
    }
}
