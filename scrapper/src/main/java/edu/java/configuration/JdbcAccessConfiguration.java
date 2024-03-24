package edu.java.configuration;

import edu.java.domain.repositories.LinksRepository;
import edu.java.domain.repositories.TgUsersRepository;
import edu.java.domain.repositories.UserLinksRepository;
import edu.java.services.interfaces.LinkService;
import edu.java.services.interfaces.TgChatService;
import edu.java.services.jbdc.JbdcLinkService;
import edu.java.services.jbdc.JbdcTgChatService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "app", name = "database-access-type", havingValue = "jdbc")
public class JdbcAccessConfiguration {
    @Bean
    public LinkService linkService(LinksRepository linksRepository, UserLinksRepository userLinksRepository) {
        log.info("JDBC link service created");
        return new JbdcLinkService(linksRepository, userLinksRepository);
    }

    @Bean
    public TgChatService tgChatService(TgUsersRepository tgUsersRepository) {
        log.info("JDBC chat service created");
        return new JbdcTgChatService(tgUsersRepository);
    }
}
