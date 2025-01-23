package sep490.idp.filters;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;
import sep490.common.api.exceptions.BusinessException;

import java.lang.reflect.Type;
import java.util.Collections;

@RestControllerAdvice
public class PayloadValidationRestAdvice extends RequestBodyAdviceAdapter {
    
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
                var fields = violations
                        .stream()
                        .map(ConstraintViolation::getPropertyPath)
                        .map(Object::toString)
                        .reduce((s1, s2) -> s1 + ", " + s2)
                        .orElse("");
                throw new BusinessException(fields, StringUtils.EMPTY, Collections.emptyList());
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
