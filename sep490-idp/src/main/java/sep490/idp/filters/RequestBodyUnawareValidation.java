package sep490.idp.filters;

import jakarta.validation.Validation;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import sep490.idp.exceptions.BasicValidationException;

import java.lang.reflect.Type;
import java.util.Collections;

@RestControllerAdvice(basePackages = "sep490.idp.rest")
public class RequestBodyUnawareValidation extends RequestBodyAdviceAdapter {
    
    /**
     * Validates the request body after it has been read and before further processing.
     *
     * This method performs validation on the request body using the Jakarta Validation API.
     * If any validation constraints are violated, it throws a {@code BasicValidationException}
     * with details about the first violation encountered.
     *
     * @param body The request body object to be validated
     * @param inputMessage The HTTP input message containing the request body
     * @param parameter The method parameter associated with the request body
     * @param targetType The target type of the body object
     * @param converterType The message converter used to read the body
     * @return The validated request body object
     * @throws BasicValidationException if any validation constraints are violated
     */
    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        try (var factory = Validation.buildDefaultValidatorFactory()) {
            var validator = factory.getValidator();
            var violations = validator.validate(body);
            if (!CollectionUtils.isEmpty(violations)) {
                var violation = violations.stream().findFirst().get();
                throw new BasicValidationException(
                        violation.getPropertyPath().toString(),
                        violation.getMessage(),
                        Collections.emptyList()
                );
            }
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
    
    /**
     * Determines whether the current method parameter should be processed by this advice.
     *
     * @param methodParameter The method parameter being evaluated
     * @param targetType The target type of the parameter
     * @param converterType The HTTP message converter type
     * @return {@code true} if the parameter is annotated with {@code @RequestBody}, {@code false} otherwise
     */
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }
}
