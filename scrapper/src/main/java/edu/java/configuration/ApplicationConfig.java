package edu.java.configuration;

import edu.java.configuration.retrys.RetryPolicy;
import edu.java.services.DefaultLinkUpdater;
import jakarta.validation.constraints.NotNull;
import java.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.annotation.Validated;

@Validated
@EnableScheduling
@ConfigurationProperties(prefix = "app", ignoreUnknownFields = false)
@ComponentScan("edu.java")
public record ApplicationConfig(
    @NotNull
    Scheduler scheduler,
    @NotNull
    AccessType databaseAccessType,
    @NotNull
    RetryPolicy retryPolicy,
    @NotNull
    TopicConfig topic

) {
    @Bean
    public DefaultLinkUpdater linkUpdaterScheduler() {
        return new DefaultLinkUpdater();
    }

    @Bean
    public RetryPolicy retryPolicy() {
        return retryPolicy;
    }

    @Bean
    public long interval() {
        return scheduler().interval().toMillis();
    }

    public record Scheduler(boolean enable, @NotNull Duration interval, @NotNull Duration forceCheckDelay) {
    }

    public record TopicConfig(String name, int partitions, int replicas) {
    }

}

