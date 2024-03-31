package edu.java.services.jbdc;

import edu.java.UpdateRequestDto;
import edu.java.clients.bot.UpdateBotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.clients.stackoverflow.responses.StackOverflowQuestionResponse;
import edu.java.domain.entities.UserLinkDto;
import edu.java.domain.repositories.LinksRepository;
import edu.java.domain.repositories.UserLinksRepository;
import edu.java.services.interfaces.LinkUpdater;
import java.time.Duration;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class JdbcLinkUpdater implements LinkUpdater {
    private final UpdateBotClient botClient = new UpdateBotClient("http://localhost:8090");
    private final int twentyFour = 24;
    private final int five = 5;
    private final int four = 4;
    private final int three = 3;
    @Autowired
    private LinksRepository linksRepository;
    @Autowired
    private UserLinksRepository userLinksRepository;
    @Autowired
    private GitHubClient gitHubClient;
    @Autowired
    private StackOverflowClient stackOverflowClient;

    @Override
    public void update() {
        var links = linksRepository.findLinksNotCheckedSince(Duration.ofHours(twentyFour));

        int updatedLinksCount = 0;

        for (var link : links) {
            if (link.getUrl().startsWith("https://github.com/")) {
                String[] parts = link.getUrl().split("/");
                if (parts.length >= five) {
                    String user = parts[three];
                    String repo = parts[four];
                    var response = gitHubClient.fetchRepository(user, repo).block();
                    if (response != null
                        &&
                        response.getUpdatedAt().toLocalDateTime().isAfter(link.getLastCheckedAt().toLocalDateTime())) {
                        var chatIds = userLinksRepository.findAllByLinkId(link.getId());
                        var update = new UpdateRequestDto(
                            link.getId(),
                            link.getUrl(),
                            "",
                            chatIds.stream().map(UserLinkDto::getUserId).toList()
                        );
                        botClient.setUpdates(update);
                        updatedLinksCount++;
                    }
                }
            } else if (link.getUrl().startsWith("https://stackoverflow.com/questions/")) {
                String[] parts = link.getUrl().split("/");
                if (parts.length >= five) {
                    Long questionId = Long.parseLong(parts[four]);
                    StackOverflowQuestionResponse response = stackOverflowClient.fetchQuestion(questionId).block();
                    if (response != null
                        &&
                        response.getUpdatedAt().toLocalDateTime().isAfter(link.getLastCheckedAt().toLocalDateTime())) {
                        var chatIds = userLinksRepository.findAllByLinkId(link.getId());
                        var update = new UpdateRequestDto(
                            link.getId(),
                            link.getUrl(),
                            "",
                            chatIds.stream().map(UserLinkDto::getUserId).toList()
                        );
                        botClient.setUpdates(update);
                        updatedLinksCount++;
                    }
                }
            }

            link.setLastCheckedAt(OffsetDateTime.now());
            linksRepository.update(link);
        }
    }
}
