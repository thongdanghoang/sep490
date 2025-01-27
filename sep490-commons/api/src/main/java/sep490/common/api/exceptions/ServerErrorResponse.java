package sep490.common.api.exceptions;

import sep490.common.api.enums.ServerErrorType;

/**
 * Model of an error body to be sent to client in case error happens in the backend. Should include
 * at least a correlation ID and the relevant error message.
 */
public interface ServerErrorResponse {
    ServerErrorType getErrorType();
}
