package edu.java.scrapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class SimpleIntegrationTest extends IntegrationTest {
    @Test
    public void testPostgresContainer() {
        Assertions.assertTrue(POSTGRES.isRunning());
    }
}
