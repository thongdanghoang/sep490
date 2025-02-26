package greenbuildings.idp.configs;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;

public class CustomLocaleResolver implements LocaleResolver {
    
    private final LocaleResolver statelessResolver;
    private final LocaleResolver statefulResolver;
    
    public CustomLocaleResolver(LocaleResolver statelessResolver, LocaleResolver statefulResolver) {
        this.statelessResolver = statelessResolver;
        this.statefulResolver = statefulResolver;
    }
    
    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        if (request.getRequestURI().contains("/api")) {
            return statelessResolver.resolveLocale(request);
        } else {
            return statefulResolver.resolveLocale(request);
        }
    }
    
    @Override
    public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
        if (request.getRequestURI().startsWith("/api")) {
            statelessResolver.setLocale(request, response, locale);
        } else {
            statefulResolver.setLocale(request, response, locale);
        }
    }
}
