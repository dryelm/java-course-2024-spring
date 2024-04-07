package edu.java.configuration.retrys;

import java.time.Duration;
import java.util.List;
import java.util.function.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
public class LinearRetry extends Retry {
    private final int maxAttempts;
    private final Duration minDelay;
    private final Duration appender;

    private final Predicate<? super Throwable> filter;
    private final List<Integer> statusCodes = List.of(500, 502, 503, 504);

    public LinearRetry(int maxAttempts, Duration minDelay, int appender) {
        this.maxAttempts = maxAttempts;
        this.minDelay = minDelay;
        this.appender = Duration.ofSeconds(appender);
        filter = throwable -> throwable instanceof WebClientResponseException
            && statusCodes.contains(((WebClientResponseException) throwable).getStatusCode().value());
    }

    @Override
    public Publisher<?> generateCompanion(Flux<RetrySignal> retrySignals) {
        return retrySignals.flatMap(this::getRetry);
    }

    public Mono<Long> getRetry(Retry.RetrySignal rs) {
        if (!filter.test(rs.failure())) {
            return Mono.error(rs.failure());
        }
        if (rs.totalRetries() < maxAttempts) {
            log.info("Perform retry on {}", rs.failure().getLocalizedMessage());
            Duration delay = minDelay.plus(appender.multipliedBy(rs.totalRetries()));

            log.debug("retry {} with backoff {} seconds", rs.totalRetries(), delay.toSeconds());
            return Mono.delay(delay).thenReturn(rs.totalRetries());
        } else {
            log.error("Retry failed");
            return Mono.error(rs.failure());
        }
    }
}
