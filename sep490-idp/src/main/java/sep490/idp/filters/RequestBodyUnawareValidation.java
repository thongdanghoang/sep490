package sep490.idp.filters;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
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
import java.util.Objects;

@RestControllerAdvice(basePackages = "sep490.idp.rest")
public class RequestBodyUnawareValidation extends RequestBodyAdviceAdapter {
    
    private final Validator validator;
    
    public RequestBodyUnawareValidation() {
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            this.validator = factory.getValidator();
        }
    }
    
    @Override
    public Object afterBodyRead(Object body,
                                HttpInputMessage inputMessage,
                                MethodParameter parameter,
                                Type targetType,
                                Class<? extends HttpMessageConverter<?>> converterType) {
        
        var violations = Objects.requireNonNull(validator).validate(body);
        if (!CollectionUtils.isEmpty(violations)) {
            var violation = violations.stream().findFirst().get();
            throw new BasicValidationException(
                    violation.getPropertyPath().toString(),
                    violation.getMessage(),
                    Collections.emptyList()
            );
        }
        return super.afterBodyRead(body, inputMessage, parameter, targetType, converterType);
    }
    
    @Override
    public boolean supports(MethodParameter methodParameter,
                            Type targetType,
                            Class<? extends HttpMessageConverter<?>> converterType) {
        return methodParameter.hasParameterAnnotation(RequestBody.class);
    }
}
