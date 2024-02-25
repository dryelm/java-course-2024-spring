package edu.java.clients.stackoverflow;

import edu.java.clients.stackoverflow.responses.StackOverflowQuestionResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class StackOverflowClient {
    private final WebClient stackOverflowWebClient;

    public StackOverflowClient(WebClient stackOverflowWebClient) {
        this.stackOverflowWebClient = stackOverflowWebClient;
    }

    public Mono<StackOverflowQuestionResponse> fetchQuestion(Long questionId) {
        return stackOverflowWebClient.get()
            .uri(uriBuilder -> uriBuilder.path("/questions/{id}").build(questionId))
            .retrieve()
            .bodyToMono(StackOverflowQuestionResponse.class);
    }
}
