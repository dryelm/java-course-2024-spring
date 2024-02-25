package edu.java.scrapper.clients;

import edu.java.clients.github.GitHubClient;
import edu.java.clients.github.responses.GitHubRepoResponse;
import org.junit.Rule;
import org.junit.Test;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.springframework.web.reactive.function.client.WebClient;
import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.assertj.core.api.Assertions.assertThat;

public class GitHubClientTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(8089);

    @Test
    public void testFetchRepository() {
        stubFor(get(urlEqualTo("/repos/user/repo"))
            .willReturn(aResponse()
                .withHeader("Content-Type", "application/json")
                .withBody("{\"name\":\"repo\",\"full_name\":\"user/repo\",\"created_at\":\"2020-01-01T00:00:00Z\"}")));

        WebClient webClient = WebClient.create("http://localhost:8089");
        GitHubClient gitHubClient = new GitHubClient(webClient);

        var result = gitHubClient.fetchRepository("user", "repo").block();

        assertThat(result.getName()).isEqualTo("repo");
        assertThat(result.getFullName()).isEqualTo("user/repo");
    }
}

