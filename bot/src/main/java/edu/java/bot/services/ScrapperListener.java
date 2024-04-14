package edu.java.bot.services;

import edu.java.UpdateRequestDto;
import edu.java.bot.controllers.UpdatesHandler;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.retrytopic.DltStrategy;
import org.springframework.kafka.support.Acknowledgment;

@RequiredArgsConstructor
public class ScrapperListener {

    private final UpdatesHandler updatesHandler;

    @RetryableTopic(attempts = "1",
                    dltStrategy = DltStrategy.ALWAYS_RETRY_ON_ERROR,
                    concurrency = "1",
                    autoCreateTopics = "false",
                    dltTopicSuffix = "-dlq")
    @KafkaListener(topics = "${scrapper-topic.name}", concurrency = "1", groupId = "group1")
    public void listen(@Valid UpdateRequestDto update, Acknowledgment acknowledgment) {
        updatesHandler.handleUpdate(update);
        acknowledgment.acknowledge();
    }
}
