package edu.java.services.botSender;

import edu.java.UpdateRequestDto;
import edu.java.configuration.ApplicationConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;

@RequiredArgsConstructor
public class KafkaBotSender implements BotSender {
    private final ApplicationConfig config;
    private final KafkaTemplate<String, UpdateRequestDto> kafka;

    @Override
    public void send(UpdateRequestDto update) {
        kafka.send(config.topic().name(), update);
    }
}
