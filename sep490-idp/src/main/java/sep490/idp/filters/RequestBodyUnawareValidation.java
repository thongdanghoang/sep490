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

@RestControllerAdvice(basePackages = "sep490.idp.rest")
public class RequestBodyUnawareValidation extends RequestBodyAdviceAdapter {
    
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
                throw new BasicValidationException(violations);
            }
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
