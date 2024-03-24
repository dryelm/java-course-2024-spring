package edu.java.services.interfaces;

import edu.java.domain.entities.LinkDto;
import edu.java.services.exceptions.AlreadyExistException;
import edu.java.services.exceptions.NotExistException;
import java.net.URI;
import java.util.List;

public interface LinkService {
    LinkDto add(long tgChatId, URI url) throws NotExistException, AlreadyExistException;

    LinkDto remove(long tgChatId, URI url) throws NotExistException;

    List<LinkDto> listAll(long tgChatId) throws NotExistException;
}

