package edu.java.configuration.botSender;

import edu.java.clients.bot.UpdateBotClient;
import edu.java.services.botSender.HttpBotSender;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConditionalOnProperty(prefix = "app", name = "use-queue", havingValue = "false")
public class HttpSenderConfiguration {
    @Bean
    public HttpBotSender httpSender(UpdateBotClient client) {
        return new HttpBotSender(client);
    }
}
