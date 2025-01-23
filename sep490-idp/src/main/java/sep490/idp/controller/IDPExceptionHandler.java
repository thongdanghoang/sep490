package sep490.idp.controller;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import sep490.common.api.exceptions.BusinessErrorResponse;
import sep490.common.api.exceptions.BusinessException;

import java.util.UUID;

@ControllerAdvice
@Slf4j
public class IDPExceptionHandler {
    
    @ExceptionHandler(BusinessException.class)
    @ResponseBody
    public ResponseEntity<BusinessErrorResponse> handleBusinessException(BusinessException ex) {
        log.error("Business exception occurred: field={}, i18nKey={}", ex.getField(), ex.getI18nKey(), ex);
        BusinessErrorResponse response = new BusinessErrorResponse(ex, UUID.randomUUID().toString());
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(InvalidFormatException.class)
    public ResponseEntity processJsonInvalidFormatException(InvalidFormatException e) {
        return ResponseEntity.badRequest().build();
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleInvalidMethodArgEx(Exception ex) {
        MethodArgumentNotValidException exp = (MethodArgumentNotValidException) ex;
        log.warn("Invalid method argument send through proxy API or missing validation in frontend");
        exp.getBindingResult()
            .getAllErrors()
            .forEach(err -> log.warn("Error field {}", ((FieldError) err).getField()));
        return ResponseEntity.badRequest().build();
    }

    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        log.error(ex.getMessage(), ex);
        model.addAttribute("error", "An unexpected error occurred, please contact admin!");
        return "error";
    }
}
