package edu.java.services.jbdc;

import edu.java.domain.entities.UserDto;
import edu.java.domain.repositories.TgUsersRepository;
import edu.java.services.interfaces.TgChatService;
import org.springframework.stereotype.Component;

@Component
public class JbdcTgChatService implements TgChatService {
    private final TgUsersRepository tgUsersRepository;

    public JbdcTgChatService(TgUsersRepository tgUsersRepository) {

        this.tgUsersRepository = tgUsersRepository;
    }

    @Override
    public void register(long tgChatId) {
        var dto = new UserDto();
        dto.setId(tgChatId);
        tgUsersRepository.add(dto);
    }

    @Override
    public void unregister(long tgChatId) {
        tgUsersRepository.remove(tgChatId);
    }
}
