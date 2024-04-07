package edu.java.configuration.retrys;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.util.retry.Retry;

@RequiredArgsConstructor
@Configuration
@ConditionalOnProperty(prefix = "app.retry-policy", name = "type", havingValue = "linear")
public class LinearRetryConfiguration {

    private final RetryPolicy retryPolicy;

    @Bean
    public Retry linearRetryPolicy() {
        return new LinearRetry(
            retryPolicy.maxAttempts(),
            retryPolicy.backoffInitialDelay(),
            retryPolicy.backoffAppender()
        );
    }

}
