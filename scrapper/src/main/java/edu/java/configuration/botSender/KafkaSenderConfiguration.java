package edu.java.configuration.botSender;

import edu.java.UpdateRequestDto;
import edu.java.configuration.ApplicationConfig;
import edu.java.services.botSender.BotSender;
import edu.java.services.botSender.KafkaBotSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "true")
public class KafkaSenderConfiguration {
    @Bean
    public BotSender queueSender(ApplicationConfig config, KafkaTemplate<String, UpdateRequestDto> kafka) {
        return new KafkaBotSender(config, kafka);
    }
}
