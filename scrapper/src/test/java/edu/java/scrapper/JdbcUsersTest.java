package edu.java.scrapper;

import edu.java.domain.entities.UserDto;
import edu.java.domain.repositories.TgUsersRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class JdbcUsersTest extends IntegrationTest {
    @Autowired
    private TgUsersRepository tgUsersRepository;

    @Test
    @Transactional
    @Rollback
    void addUserTest() {
        UserDto userDto = new UserDto();
        userDto.setId(123456L);
        tgUsersRepository.add(userDto);

        assertThat(tgUsersRepository.findAll().stream().map(UserDto::getId)).contains(123456L);
    }

    @Test
    @Transactional
    @Rollback
    void removeUserTest() {
        UserDto userDto = new UserDto();
        userDto.setId(123456L);
        tgUsersRepository.add(userDto);
        tgUsersRepository.remove(123456L);

        assertThat(tgUsersRepository.findAll()).isEmpty();
    }

}
