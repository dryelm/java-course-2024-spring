package edu.java.clients.bot;

import edu.java.UpdateRequestDto;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class UpdateBotClient {
    private final WebClient webClient;

    public UpdateBotClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<ServerResponse> setUpdates(UpdateRequestDto request) {
        return webClient.post()
            .uri("/updates")
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(request)
            .retrieve()
            .bodyToMono(ServerResponse.class);
    }
}
