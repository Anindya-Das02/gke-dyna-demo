package in.das.app.gkedemo.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestResponseFilter extends OncePerRequestFilter {
    private static final String TRX_ID_HEADER = "X-Trx-Id";
    private static final String MDC_KEY = "trxId";

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        return request.getRequestURI().startsWith("/actuator");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String trxId = request.getHeader(TRX_ID_HEADER);
        if (!StringUtils.hasText(trxId)) {
            trxId = UUID.randomUUID().toString();
            log.debug("No trxId received in request header '{}'; using generated trxId:{}", TRX_ID_HEADER, trxId);
        }

        MDC.put(MDC_KEY, trxId);
        response.setHeader(TRX_ID_HEADER, trxId);

        long startTime = System.currentTimeMillis();
        try {
            filterChain.doFilter(request, response);
        } finally {
            long durationInMs = System.currentTimeMillis() - startTime;
            log.info("[API Response] method={} uri={} status={} durationInMs={}", request.getMethod(), request.getRequestURI(), response.getStatus(), durationInMs);
            MDC.remove(MDC_KEY);
        }
    }
}
