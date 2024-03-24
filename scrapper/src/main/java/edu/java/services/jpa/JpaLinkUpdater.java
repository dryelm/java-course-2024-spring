package edu.java.services.jpa;

import edu.java.UpdateRequestDto;
import edu.java.clients.bot.UpdateBotClient;
import edu.java.clients.github.GitHubClient;
import edu.java.clients.stackoverflow.StackOverflowClient;
import edu.java.domain.repositories.jpa.JpaLinksRepository;
import edu.java.domain.repositories.jpa.LinkEntity;
import edu.java.domain.repositories.jpa.TgUserEntity;
import edu.java.services.interfaces.LinkUpdater;
import java.time.OffsetDateTime;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JpaLinkUpdater implements LinkUpdater {
    private final JpaLinksRepository linksRepository;
    private final UpdateBotClient botClient;
    private final GitHubClient gitHubClient;
    private final StackOverflowClient stackOverflowClient;
    private final int updateInterval = 30;
    private final int three = 3;
    private final int four = 4;
    private final int five = 5;

    @Override
    public void update() {
        var links = linksRepository.findByLastCheckTime(OffsetDateTime.now().minusMinutes(updateInterval));
        links.forEach(link -> {
            if (link.getUrl().startsWith("https://github.com")) {
                updateGitHubLink(link);
            } else if (link.getUrl().startsWith("https://stackoverflow.com/questions/")) {
                updateStackOverflowLink(link);
            }

            setChecked(link);
        });
    }

    private void updateGitHubLink(LinkEntity link) {
        String[] parts = link.getUrl().split("/");
        if (parts.length >= five) {
            String user = parts[three];
            String repo = parts[four];
            var response = gitHubClient.fetchRepository(user, repo).block();
            if (response != null && response.getUpdatedAt().isAfter(link.getUpdatedAt())) {
                var chatIds = link.getTgChats();
                var update = new UpdateRequestDto(
                    link.getId(),
                    link.getUrl(),
                    "",
                    chatIds.stream().map(TgUserEntity::getId).toList()
                );
                botClient.setUpdates(update);
            }
        }
    }

    private void updateStackOverflowLink(LinkEntity link) {
        var parts = link.getUrl().split("/");
        if (parts.length >= five) {
            var questionId = Long.parseLong(parts[four]);
            var response = stackOverflowClient.fetchQuestion(questionId).block();
            if (response != null && response.getUpdatedAt().isAfter(link.getUpdatedAt())) {
                var chatIds = link.getTgChats();
                var update = new UpdateRequestDto(
                    link.getId(),
                    link.getUrl(),
                    "",
                    chatIds.stream().map(TgUserEntity::getId).toList()
                );
                botClient.setUpdates(update);
            }
        }
    }

    private void setChecked(LinkEntity link) {
        link.setLastCheckedAt(OffsetDateTime.now());
    }
}
