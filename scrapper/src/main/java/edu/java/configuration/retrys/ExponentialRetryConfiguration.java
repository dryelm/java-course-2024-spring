package edu.java.configuration.retrys;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app.retry-policy", name = "type", havingValue = "exponential")
public class ExponentialRetryConfiguration {

}
