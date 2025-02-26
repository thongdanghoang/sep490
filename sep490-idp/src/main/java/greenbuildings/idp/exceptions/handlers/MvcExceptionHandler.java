package greenbuildings.idp.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "greenbuildings.idp.controller")
@Slf4j
public class MvcExceptionHandler {
    
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        log.error(ex.getMessage(), ex);
        model.addAttribute("error", "An unexpected error occurred, please contact admin!");
        return "error";
    }
}
