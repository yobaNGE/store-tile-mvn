package org.chiches.storecherepitsamvn.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class RequestLoggingInterceptor implements HandlerInterceptor {
    private static final Logger LOGGER =
            LoggerFactory.getLogger(RequestLoggingInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) {
        String method = request.getMethod();

        String requestURI = request.getRequestURI();

        String user = (request.getUserPrincipal() != null)
                ? request.getUserPrincipal().getName()
                : request.getRemoteAddr();

        String timestamp = LocalDateTime.now()
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

        LOGGER.info("request_received: {{\"method\":\"{}\", \"uri\":\"{}\", \"user\":\"{}\", \"timestamp\":\"{}\"}}",
                method, requestURI, user, timestamp);

        return true;
    }
}
