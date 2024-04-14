package edu.java.configuration;

import edu.java.clients.bot.UpdateBotClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ClientConfiguration {
    @Bean
    public WebClient gitHubWebClient(WebClient.Builder builder) {
        String gitHubBaseUrl = "https://api.github.com";
        return builder.baseUrl(gitHubBaseUrl).build();
    }

    @Bean
    public WebClient stackOverflowWebClient(WebClient.Builder builder) {
        String stackOverflowBaseUrl = "https://api.stackexchange.com/2.3";
        return builder.baseUrl(stackOverflowBaseUrl).build();
    }

    @Value("${bot.base.url:http://localhost:8090}")
    private String baseUrl;

    @Bean
    public UpdateBotClient botClient() {
        return new UpdateBotClient(baseUrl);
    }
}

