package edu.java.bot.clients;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class UserRegistrationScrapperClient {
    private final WebClient webClient;
    private final String path = "/tg-chat/{id}";

    public UserRegistrationScrapperClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<ResponseEntity<?>> registerChat(long id) {
        return webClient.post()
            .uri(uriBuilder -> uriBuilder.path(path).build(id))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<ResponseEntity<?>> deleteChat(long id) {
        return webClient.delete()
            .uri(uriBuilder -> uriBuilder.path(path).build(id))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }
}
