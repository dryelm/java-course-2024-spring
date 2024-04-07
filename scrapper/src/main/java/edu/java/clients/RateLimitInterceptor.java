package edu.java.clients;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.ConsumptionProbe;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class RateLimitInterceptor implements HandlerInterceptor {
    private final Map<String, Bucket> buckets = new ConcurrentHashMap<>();
    private final int thousand = 1000;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception {
        Bucket requestBucket = getBucket(request);
        ConsumptionProbe probe = requestBucket.tryConsumeAndReturnRemaining(1);
        if (probe.isConsumed()) {
            response.addHeader("X-Rate-Limit-Remaining", String.valueOf(probe.getRemainingTokens()));
            return true;
        } else {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
            response.addHeader(
                "X-Rate-Limit-Retry-After-Milliseconds",
                String.valueOf(Duration.ofNanos(probe.getNanosToWaitForRefill()).toMillis())
            );
            return false;
        }
    }

    private Bucket getBucket(HttpServletRequest request) {
        String ip = getClientIP(request);
        return buckets.computeIfAbsent(ip, this::newBucket);
    }

    private Bucket newBucket(String ip) {
        return Bucket4j.builder()
            .addLimit(Bandwidth.classic(
                thousand,
                Refill.intervally(thousand, Duration.ofMinutes(1))
            ))
            .build();
    }

    private String getClientIP(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            try {
                return InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                return request.getRemoteAddr();
            }
        }
        return xfHeader.split(",")[0];
    }
}
