package edu.java.bot.clients;

import edu.java.AddLinkRequest;
import edu.java.LinkResponse;
import edu.java.LinksListResponse;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class LinksScrapperClient {

    private final WebClient webClient;
    private final String path = "/links";
    private String header;

    public LinksScrapperClient(String baseUrl) {
        this.webClient = WebClient.builder()
            .baseUrl(baseUrl)
            .build();
    }

    public Mono<ResponseEntity<LinksListResponse>> getLinks(long tgChatId) {
        return webClient.get()
            .uri(path)
            .header(header, String.valueOf(tgChatId))
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<ResponseEntity<LinkResponse>> setLink(long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri(path)
            .header(header, String.valueOf(tgChatId))
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }

    public Mono<ResponseEntity<LinkResponse>> deleteLink(long tgChatId, AddLinkRequest addLinkRequest) {
        return webClient.post()
            .uri("/links/delete")
            .header(header, String.valueOf(tgChatId))
            .bodyValue(addLinkRequest)
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<>() {});
    }




}
