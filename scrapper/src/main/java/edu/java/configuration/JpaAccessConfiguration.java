package edu.java.configuration;

import edu.java.domain.repositories.jpa.JpaLinksRepository;
import edu.java.domain.repositories.jpa.JpaTgUsersRepository;
import edu.java.services.interfaces.LinkService;
import edu.java.services.interfaces.TgChatService;
import edu.java.services.jpa.JpaLinkService;
import edu.java.services.jpa.JpaTgChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    public final JpaLinksRepository linksRepository;
    public final JpaTgUsersRepository tgUsersRepository;

    public JpaAccessConfiguration(JpaLinksRepository linksRepository, JpaTgUsersRepository tgUsersRepository) {
        this.linksRepository = linksRepository;
        this.tgUsersRepository = tgUsersRepository;

    }

    @Bean
    public LinkService linkService() {
        return new JpaLinkService(linksRepository, tgUsersRepository);
    }

    @Bean
    public TgChatService tgChatService() {
        return new JpaTgChatService(tgUsersRepository);
    }
}
