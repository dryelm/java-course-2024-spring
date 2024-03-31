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
    public final LinksRepository linksRepository;
    public final UserLinksRepository userLinksRepository;
    private final TgUsersRepository tgUsersRepository;

    public JdbcAccessConfiguration(LinksRepository linksRepository, UserLinksRepository userLinksRepository,
        TgUsersRepository tgUsersRepository
    ) {
        this.linksRepository = linksRepository;
        this.userLinksRepository = userLinksRepository;
        this.tgUsersRepository = tgUsersRepository;
    }

    @Bean
    public LinkService linkService() {
        return new JbdcLinkService(linksRepository, userLinksRepository);
    }

    @Bean
    public TgChatService tgChatService() {
        return new JbdcTgChatService(tgUsersRepository);
    }
}
