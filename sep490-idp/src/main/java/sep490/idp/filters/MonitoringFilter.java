package sep490.idp.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.GenericFilter;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.MDC;
import sep490.common.api.utils.MDCContext;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.UUID;

@Slf4j
public class MonitoringFilter extends GenericFilter {
    
    public static final String CORRELATION_ID_HEADER_NAME = "X-Correlation-ID";
    public static final String PROCESSING_TIME_ATTRIBUTE = "processing_time";
    public static final long MAX_RESPONSE_TIME = 1;
    
    /**
     * Filters and monitors HTTP requests by tracking their execution time and managing correlation IDs.
     *
     * This method overrides the default filter behavior to:
     * 1. Ensure the request is an HTTP request
     * 2. Manage correlation IDs using Mapped Diagnostic Context (MDC)
     * 3. Record the request's start time
     * 4. Monitor and log execution time if it exceeds the maximum threshold
     *
     * @param request the servlet request being processed
     * @param response the servlet response being generated
     * @param chain the filter chain for continuing request processing
     * @throws IOException if an I/O error occurs during filtering
     * @throws ServletException if a servlet-related error occurs during filtering
     */
    @Override
    public void doFilter(ServletRequest request,
                         ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        if (request instanceof HttpServletRequest httpRequest && response instanceof HttpServletResponse) {
            var requestingCorrelationId = httpRequest.getHeader(CORRELATION_ID_HEADER_NAME);
            if (StringUtils.isBlank(requestingCorrelationId)) {
                MDC.put(MDCContext.CORRELATION_ID, UUID.randomUUID().toString());
            } else {
                MDC.put(MDCContext.CORRELATION_ID, requestingCorrelationId);
            }
            httpRequest.setAttribute(PROCESSING_TIME_ATTRIBUTE, LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
            
            try {
                chain.doFilter(request, response);
            } finally {
                try {
                    var requestingTime = httpRequest.getAttribute(PROCESSING_TIME_ATTRIBUTE);
                    if (requestingTime != null && NumberUtils.isCreatable(requestingTime.toString())) {
                        var requestingEpoch = NumberUtils.toLong(requestingTime.toString());
                        monitorExecutionTime(requestingEpoch, httpRequest.getRequestURI());
                    }
                } finally {
                    MDC.remove(MDCContext.CORRELATION_ID);
                }
            }
        } else {
            chain.doFilter(request, response);
        }
    }
    
    /**
     * Monitors and logs the execution time of a request if it exceeds the maximum allowed response time.
     *
     * @param requestingEpochTime the timestamp when the request was initially received, in seconds since the Unix epoch
     * @param request the URI or identifier of the request being monitored
     * @throws ArithmeticException if the time calculation results in an overflow
     */
    public static void monitorExecutionTime(long requestingEpochTime, String request) {
        var now = LocalDateTime.now();
        var executionTime = now.toEpochSecond(ZoneOffset.UTC) - requestingEpochTime;
        if (executionTime > MAX_RESPONSE_TIME) {
            log.warn("Request `{}` is processed too long. Execution time: {}s", request, executionTime);
        }
    }
}
