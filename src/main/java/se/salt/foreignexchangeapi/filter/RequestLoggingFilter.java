package se.salt.foreignexchangeapi.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class RequestLoggingFilter extends OncePerRequestFilter {

    private final static Logger logger = LoggerFactory.getLogger(RequestLoggingFilter.class);
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        long start = System.currentTimeMillis();
        String method = request.getMethod();
        String path = request.getRequestURI();
        String userAgent = request.getHeader("User-Agent");

        logger.info("Incoming request={} path={} | User-Agent={}", method , path, userAgent);

        filterChain.doFilter(request, response);

        long duration = System.currentTimeMillis() - start;

        logger.info("Completed request -> method={} path={} status={} durationMs={}",
                method,
                path,
                response.getStatus(),
                duration
        );
    }
}
