package edu.java.configuration;

import edu.java.domain.repositories.jpa.JpaLinksRepository;
import edu.java.domain.repositories.jpa.JpaTgUsersRepository;
import edu.java.services.interfaces.LinkService;
import edu.java.services.interfaces.TgChatService;
import edu.java.services.jpa.JpaLinkService;
import edu.java.services.jpa.JpaTgChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jpa")
public class JpaAccessConfiguration {
    @Bean
    @Autowired
    public LinkService linkService(JpaLinksRepository linksRepository, JpaTgUsersRepository tgUsersRepository) {
        log.info("JPA link service created");
        return new JpaLinkService(linksRepository, tgUsersRepository);
    }

    @Bean
    @Autowired
    public TgChatService tgUsersService(JpaTgUsersRepository tgUsersRepository) {
        log.info("JPA tg users repository created");
        return new JpaTgChatService(tgUsersRepository);
    }
}
