package edu.java.configuration.retrys;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "app.retry-policy", name = "type", havingValue = "constant")
public class ConstantRetryConfiguration {

    private final RetryPolicy retryPolicy;

    @Bean
    public Retry constantRetryPolicy() {
        return Retry.fixedDelay(retryPolicy.maxAttempts(), retryPolicy.backoffInitialDelay());
    }
}
