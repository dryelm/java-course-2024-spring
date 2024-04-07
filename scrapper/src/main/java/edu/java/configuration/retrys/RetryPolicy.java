package edu.java.configuration.retrys;

import java.time.Duration;

public record RetryPolicy(RetryType retryType, int maxAttempts, Duration backoffInitialDelay, int backoffAppender) {
}
