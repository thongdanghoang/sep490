package sep490.common.api.exceptions;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * An exception class that represent the business error for the application.
 */
@Getter
public class BusinessException extends RuntimeException {
    protected final String field;
    protected final String i18nKey;
    protected final ArrayList<BusinessErrorParam> args;
    protected final int httpStatus;
    
    private static final int EXPECTATION_FAILED = 417;
    
    /**
     * Constructs a BusinessException with a specific field, internationalization key, and error parameters.
     *
     * @param field   The name of the field associated with the error. Can be null if no specific field is related.
     * @param i18nKey The internationalization key used to retrieve a localized error message. Must not be null.
     * @param args    A list of error parameters providing additional context for the exception. If null, an empty list will be used.
     * @implNote This constructor defaults the HTTP status to 417 (Expectation Failed) when no specific status is provided.
     */
    public BusinessException(String field, String i18nKey, List<BusinessErrorParam> args) {
        this(field, i18nKey, args, EXPECTATION_FAILED);
    }
    
    /**
     * Constructs a new BusinessException with specified field, internationalization key, error parameters, and HTTP status.
     *
     * @param field The name of the field associated with the error
     * @param i18nKey The internationalization key for localized error messages
     * @param args A list of business error parameters providing additional context
     * @param httpStatus The HTTP status code to be associated with this exception
     */
    protected BusinessException(String field, String i18nKey, List<BusinessErrorParam> args, int httpStatus) {
        this.field = field;
        this.i18nKey = i18nKey;
        this.args = new ArrayList<>(args);
        this.httpStatus = httpStatus;
    }
    
    /**
     * Constructs a new BusinessException with detailed error information and a cause.
     *
     * @param field The name of the field associated with the error, can be null
     * @param i18nKey The internationalization key for error message localization
     * @param args A list of business error parameters providing additional context
     * @param httpStatus The HTTP status code associated with this exception
     * @param cause The underlying throwable that caused this exception
     */
    protected BusinessException(String field, String i18nKey, List<BusinessErrorParam> args, int httpStatus, Throwable cause) {
        super(cause);
        this.field = field;
        this.i18nKey = i18nKey;
        this.args = new ArrayList<>(args);
        this.httpStatus = httpStatus;
    }
    
    /**
     * Constructs a BusinessException using a map of arguments, converting map entries to BusinessErrorParam objects.
     *
     * @param field    The name of the field associated with the error, can be null
     * @param i18nKey  The internationalization key for the error message
     * @param args     A map of error parameters, where keys are parameter names and values are their corresponding values
     */
    protected BusinessException(String field, String i18nKey, Map<String, Object> args) {
        this(field, i18nKey, args.entrySet().stream().map(entry -> new BusinessErrorParam(entry.getKey(), entry.getValue())).toList());
    }
    
    /**
     * Constructs a BusinessException with an internationalization key and error parameters, with no specific field.
     *
     * @param i18nKey the internationalization key identifying the error message
     * @param args a list of {@link BusinessErrorParam} containing additional error details
     */
    protected BusinessException(String i18nKey, List<BusinessErrorParam> args) {
        this(null, i18nKey, args);
    }
    
    /**
     * Constructs a BusinessException with an internationalization key and no field or error parameters.
     *
     * @param i18nKey the internationalization key representing the specific error message
     */
    protected BusinessException(String i18nKey) {
        this(null, i18nKey, List.of());
    }
    
    /**
     * Constructs a BusinessException with a specific field and internationalization key.
     *
     * @param field The name of the field associated with the error
     * @param i18nKey The internationalization key describing the error
     */
    public BusinessException(String field, String i18nKey) {
        this(field, i18nKey, List.of());
    }
    
    /**
     * Constructs a BusinessException with an internationalization key, HTTP status, and a cause.
     *
     * @param i18nKey the internationalization key describing the error
     * @param httpStatus the HTTP status code associated with the exception
     * @param cause the underlying cause of the exception
     */
    protected BusinessException(String i18nKey, int httpStatus, Throwable cause) {
        this(null, i18nKey, Collections.emptyList(), httpStatus, cause);
    }
}
