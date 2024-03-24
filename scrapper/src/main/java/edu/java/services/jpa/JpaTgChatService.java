package edu.java.services.jpa;

import edu.java.domain.repositories.jpa.JpaTgUsersRepository;
import edu.java.domain.repositories.jpa.TgUserEntity;
import edu.java.services.exceptions.AlreadyExistException;
import edu.java.services.exceptions.NotExistException;
import edu.java.services.exceptions.RepeatedRegistrationException;
import edu.java.services.interfaces.TgChatService;
import java.util.HashSet;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class JpaTgChatService implements TgChatService {
    private final JpaTgUsersRepository tgUsersRepository;

    @Override
    @Transactional
    public void register(long tgChatId) throws AlreadyExistException, RepeatedRegistrationException {
        if (tgChatId <= 0) {
            throw new IllegalArgumentException("Id should be positive");
        }
        if (!tgUsersRepository.existsById(tgChatId)) {
            tgUsersRepository.saveAndFlush(new TgUserEntity(tgChatId, new HashSet<>()));
        } else {
            throw new RepeatedRegistrationException("This chat already exists");
        }
    }

    @Override
    @Transactional
    public void unregister(long tgChatId) throws NotExistException {
        if (tgUsersRepository.existsById(tgChatId)) {
            tgUsersRepository.deleteById(tgChatId);
        } else {
            throw new NotExistException("This chat doesn't exists");
        }
    }
}
