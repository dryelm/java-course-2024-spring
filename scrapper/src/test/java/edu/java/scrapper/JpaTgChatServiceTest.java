package edu.java.scrapper;

import edu.java.domain.repositories.jpa.JpaTgUsersRepository;
import edu.java.domain.repositories.jpa.TgUserEntity;
import edu.java.services.exceptions.AlreadyExistException;
import edu.java.services.exceptions.NotExistException;
import edu.java.services.exceptions.RepeatedRegistrationException;
import edu.java.services.jpa.JpaTgChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest(properties = {"app.database-access-type=jpa"})
public class JpaTgChatServiceTest extends IntegrationTest {

    @Autowired
    private JpaTgChatService jpaTgChatService;

    @Autowired
    private JpaTgUsersRepository tgUsersRepository;

    @Test
    @Transactional
    public void testRegister() throws RepeatedRegistrationException, AlreadyExistException {
        long tgChatId = 123L;
        jpaTgChatService.register(tgChatId);
        assertTrue(tgUsersRepository.existsById(tgChatId));
    }

    @Test
    @Transactional
    public void testRegisterAlreadyExists() {
        long tgChatId = 123L;
        tgUsersRepository.saveAndFlush(new TgUserEntity(tgChatId));
        assertThrows(RepeatedRegistrationException.class, () -> jpaTgChatService.register(tgChatId));
    }

    @Test
    @Transactional
    public void testUnregister() throws NotExistException {
        long tgChatId = 123L;
        tgUsersRepository.saveAndFlush(new TgUserEntity(tgChatId));
        jpaTgChatService.unregister(tgChatId);
        assertFalse(tgUsersRepository.existsById(tgChatId));
    }

    @Test
    @Transactional
    public void testUnregisterNotExists() {
        long tgChatId = 123L;
        assertThrows(NotExistException.class, () -> jpaTgChatService.unregister(tgChatId));
    }
}
