package sep490.idp.exceptions.handlers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice(basePackages = "sep490.idp.controller")
@Slf4j
public class MvcExceptionHandler {
    
    /**
     * Handles generic exceptions across all controllers and prepares an error response.
     *
     * This method is triggered when any unhandled exception occurs in the application.
     * It logs the full exception details and adds a user-friendly error message to the model.
     *
     * @param ex The exception that was thrown and caught by the handler
     * @param model The Spring MVC Model to which error attributes will be added
     * @return A logical view name for the error page, which will be rendered to display the error message
     */
    @ExceptionHandler(Exception.class)
    public String handleGenericException(Exception ex, Model model) {
        log.error(ex.getMessage(), ex);
        model.addAttribute("error", "An unexpected error occurred, please contact admin!");
        return "error";
    }
}
