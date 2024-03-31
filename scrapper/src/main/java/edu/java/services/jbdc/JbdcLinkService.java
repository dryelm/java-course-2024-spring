package edu.java.services.jbdc;

import edu.java.domain.entities.LinkDto;
import edu.java.domain.entities.UserLinkDto;
import edu.java.domain.repositories.LinksRepository;
import edu.java.domain.repositories.UserLinksRepository;
import edu.java.services.interfaces.LinkService;
import java.net.URI;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class JbdcLinkService implements LinkService {
    private final LinksRepository linksRepository;
    private final UserLinksRepository userLinksRepository;

    public JbdcLinkService(LinksRepository linksRepository, UserLinksRepository userLinksRepository) {
        this.linksRepository = linksRepository;
        this.userLinksRepository = userLinksRepository;
    }

    @Override
    public LinkDto add(long tgChatId, URI url) {

        var linkDto = new LinkDto();
        linkDto.setUrl(url.toString());
        linkDto = linksRepository.add(linkDto);
        var userLinkDto = new UserLinkDto();
        userLinkDto.setUserId(tgChatId);
        userLinkDto.setLinkId(linkDto.getId());

        userLinksRepository.add(userLinkDto);
        return linkDto;
    }

    @Override
    public LinkDto remove(long tgChatId, URI url) {
        linksRepository.findAll().stream()
            .filter(linkDto -> linkDto.getUrl().equals(url.toString()))
            .findFirst()
            .ifPresent(linkDto -> {
                userLinksRepository.remove(tgChatId, linkDto.getId());
                linksRepository.remove(linkDto.getId());
            });
        return null;
    }

    @Override
    public List<LinkDto> listAll(long tgChatId) {
        var userLinks = userLinksRepository.findAllByUserId(tgChatId);
        var allLinks = linksRepository.findAll();
        return allLinks.stream()
            .filter(linkDto -> userLinks.stream().anyMatch(userLinkDto -> userLinkDto.getLinkId() == linkDto.getId()))
            .toList();
    }
}
