package edu.java.domain.repositories;

import edu.java.domain.entities.UserDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class TgUsersRepository {
    private final JdbcTemplate jdbcTemplate;
    private final int depth = 3;

    public TgUsersRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(UserDto dto) {
        dto.setCreatedAt(LocalDateTime.now());
        setCreatedBy(dto);
        String sql = "INSERT INTO tg_users (id, created_at, created_by) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, dto.getId(), dto.getCreatedAt(), dto.getCreatedBy());
    }

    public void remove(long userId) {
        String sql = "DELETE FROM tg_users WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public List<UserDto> findAll() {
        String sql = "SELECT id, created_at, created_by FROM tg_users";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserDto() {{
                setId(rs.getLong("id"));
                setCreatedAt(rs.getTimestamp("created_at").toLocalDateTime());
                setCreatedBy(rs.getString("created_by"));
            }}
        );
    }

    private void setCreatedBy(UserDto userDto) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        userDto.setCreatedBy(stackTrace[depth].getClassName() + "." + stackTrace[depth].getMethodName());
    }
}
