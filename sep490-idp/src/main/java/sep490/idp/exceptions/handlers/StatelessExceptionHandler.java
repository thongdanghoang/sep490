package sep490.idp.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authorization.AuthorizationDeniedException;
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
    
    private static TechnicalErrorResponse technicalError(Throwable exception, String errorMsg) {
        return new TechnicalErrorResponse(MDC.get(MDCContext.CORRELATION_ID), errorMsg);
    }
    
    private static BusinessErrorResponse businessError(BusinessException exception) {
        return new BusinessErrorResponse(
                MDC.get(MDCContext.CORRELATION_ID),
                exception.getField(),
                exception.getI18nKey(),
                exception.getArgs()
        );
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<TechnicalErrorResponse> handleGenericException(Exception ex) {
        log.error("Unhandled exception occurred. Correlation ID: {}", MDC.get(MDCContext.CORRELATION_ID), ex);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(technicalError(ex, ex.getMessage()));
    }
    
    @ExceptionHandler(TechnicalException.class)
    public ResponseEntity<TechnicalErrorResponse> handleTechnicalException(TechnicalException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(technicalError(ex, ex.getMessage()));
    }
    
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessErrorResponse> handleBusinessException(BusinessException ex) {
        return ResponseEntity
                .status(ex.getHttpStatus())
                .body(businessError(ex));
    }
    
    @ExceptionHandler(AuthorizationDeniedException.class)
    public ResponseEntity<TechnicalErrorResponse> handleAccessDeniedException(AuthorizationDeniedException ex) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(technicalError(ex, ex.getMessage()));
    }
}
