package edu.java.scrapper.clients;

import edu.java.clients.stackoverflow.StackOverflowClient;
import org.junit.Rule;
import org.junit.Test;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class StackOverflowClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void testFetchQuestion() {
        stubFor(get(urlEqualTo("/questions/123"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\"title\":\"Question title\",\"link\":\"http://stackoverflow.com/questions/123\",\"creation_date\":\"2020-01-01T00:00:00Z\"}")));

        WebClient webClient = WebClient.create("http://localhost:8089");
        StackOverflowClient stackOverflowClient = new StackOverflowClient(webClient);

        var result = stackOverflowClient.fetchQuestion(123L).block();

        assertThat(result.getTitle()).isEqualTo("Question title");
        assertThat(result.getLink()).isEqualTo("http://stackoverflow.com/questions/123");
    }
}

