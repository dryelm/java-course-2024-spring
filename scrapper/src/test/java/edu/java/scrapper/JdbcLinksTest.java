package edu.java.scrapper;

import edu.java.domain.entities.LinkDto;
import edu.java.domain.entities.UserDto;
import edu.java.domain.repositories.LinksRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcLinksTest extends IntegrationTest{
    @Autowired
    private LinksRepository linksRepository;

    @Test
    @Transactional
    @Rollback
    void addLinkTest() {
        LinkDto linkDto = new LinkDto();
        linkDto.setUrl("https://www.google.com");
        linksRepository.add(linkDto);

        assertThat(linksRepository.findAll().stream().map(LinkDto::getUrl)).contains("https://www.google.com");
    }

    @Test
    @Transactional
    @Rollback
    void removeLinkTest() {
        LinkDto linkDto = new LinkDto();
        linkDto.setUrl("https://www.google.com");
        linksRepository.add(linkDto);
        var id = linksRepository.findAll().stream().filter(x -> x.getUrl().equals(linkDto.getUrl())).map(LinkDto::getId).findFirst();
        linksRepository.remove(id.get());
        assertThat(linksRepository.findAll()).isEmpty();
    }


}
