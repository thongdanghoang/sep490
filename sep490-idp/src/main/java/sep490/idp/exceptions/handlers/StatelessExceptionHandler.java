package sep490.idp.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sep490.common.api.exceptions.BusinessErrorResponse;
import sep490.common.api.exceptions.BusinessException;
import sep490.common.api.exceptions.TechnicalErrorResponse;
import sep490.common.api.exceptions.TechnicalException;
import sep490.common.api.utils.MDCContext;

@RestControllerAdvice(basePackages = "sep490.idp.rest")
@Slf4j
public class StatelessExceptionHandler {
    
    /**
     * Constructs a technical error response for unhandled exceptions.
     *
     * @param exception The throwable exception that occurred
     * @param errorMsg A user-friendly error message to be included in the response
     * @return A {@code TechnicalErrorResponse} containing the correlation ID and error message
     */
    private static TechnicalErrorResponse technicalError(Throwable exception, String errorMsg) {
        var correlationId = MDC.get(MDCContext.CORRELATION_ID);
        var logMsg = "Unhandled exception occurred: %s".formatted(correlationId);
        log.error(logMsg, exception);
        return new TechnicalErrorResponse(correlationId, errorMsg);
    }
    
    /**
     * Constructs a {@code BusinessErrorResponse} from a {@code BusinessException}.
     *
     * @param exception The business exception containing error details
     * @return A {@code BusinessErrorResponse} with correlation ID, field, internationalization key, and arguments
     */
    private static BusinessErrorResponse businessError(BusinessException exception) {
        return new BusinessErrorResponse(
                MDC.get(MDCContext.CORRELATION_ID),
                exception.getField(),
                exception.getI18nKey(),
                exception.getArgs()
        );
    }
    
    /**
     * Handles generic exceptions by creating a technical error response with an internal server error status.
     *
     * @param ex The caught throwable exception to be processed
     * @return A ResponseEntity containing a TechnicalErrorResponse with HTTP 500 status
     */
    @ExceptionHandler(Throwable.class)
    public ResponseEntity<TechnicalErrorResponse> handleGenericException(Throwable ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(technicalError(ex, ex.getMessage()));
    }
    
    /**
     * Handles technical exceptions by creating a technical error response.
     *
     * @param ex The {@link TechnicalException} that was thrown
     * @return A {@link ResponseEntity} with an internal server error status and a technical error response
     */
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<TechnicalErrorResponse> handleTechnicalException(TechnicalException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(technicalError(ex, ex.getMessage()));
    }
    
    /**
     * Handles business-related exceptions and generates an appropriate HTTP response.
     *
     * @param ex The {@link BusinessException} that was thrown during application execution
     * @return A {@link ResponseEntity} containing a {@link BusinessErrorResponse} with the exception details
     *         and the HTTP status specified in the original exception
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(businessError(ex));
    }
}
