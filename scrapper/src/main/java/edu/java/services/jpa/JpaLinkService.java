package edu.java.services.jpa;

import edu.java.domain.entities.LinkDto;
import edu.java.domain.repositories.jpa.JpaLinksRepository;
import edu.java.domain.repositories.jpa.JpaTgUsersRepository;
import edu.java.domain.repositories.jpa.LinkEntity;
import edu.java.services.exceptions.AlreadyExistException;
import edu.java.services.exceptions.NotExistException;
import edu.java.services.interfaces.LinkService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
public class JpaLinkService implements LinkService {

    private static final String CHAT_NOT_EXIST = "This chat doesn't exist";
    private static final String URL_NOT_EXIST = "This url doesn't exist";
    private final JpaLinksRepository linksRepository;
    private final JpaTgUsersRepository tgUsersRepository;

    @Override
    @Transactional
    public LinkDto add(long tgChatId, URI url) throws NotExistException, AlreadyExistException {
        var user = tgUsersRepository.findById(tgChatId).orElseThrow(() -> new NotExistException(CHAT_NOT_EXIST));

        Optional<LinkEntity> linkOptional = linksRepository.findByUrl(url.toString());
        LinkEntity link;
        if (linkOptional.isEmpty()) {
            link = new LinkEntity(url.toString(), OffsetDateTime.now(), OffsetDateTime.now());
            linksRepository.saveAndFlush(link);
        } else {
            link = linkOptional.get();
        }
        user.addLink(link);

        return link.toDto();
    }

    @Override
    @Transactional
    public LinkDto remove(long tgChatId, URI url) throws NotExistException {
        tgUsersRepository.findById(tgChatId).orElseThrow(() -> new NotExistException(CHAT_NOT_EXIST));

        var linkOptional = linksRepository.findByUrl(url.toString());

        if (linkOptional.isEmpty()) {
            throw new NotExistException(URL_NOT_EXIST);
        } else {
            LinkEntity link = linkOptional.get();
            if (!link.getTgChats().removeIf(tgUserEntity -> tgUserEntity.getId().equals(tgChatId))) {
                throw new NotExistException("This link doesn't connect to this chat.");
            }
            return link.toDto();

        }
    }

    @Override
    @Transactional
    public List<LinkDto> listAll(long tgChatId) throws NotExistException {
        return tgUsersRepository.findById(tgChatId)
            .orElseThrow(() -> new NotExistException(CHAT_NOT_EXIST))
            .getLinks().stream()
            .map(LinkEntity::toDto)
            .toList();
    }

}
