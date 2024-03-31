package edu.java.domain.repositories;

import edu.java.domain.entities.UserLinkDto;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserLinksRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String userIdLabel = "user_id";
    private final String linkIdLabel = "link_id";
    private final int depth = 3;

    public UserLinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(UserLinkDto dto) {
        dto.setCreatedAt(LocalDateTime.now());
        setCreatedBy(dto);
        String sql = "INSERT INTO user_links (user_id, link_id) VALUES (?, ?)";
        jdbcTemplate.update(sql, dto.getUserId(), dto.getLinkId());
    }

    public void remove(long userId, long linkId) {
        String sql = "DELETE FROM user_links WHERE user_id = ? AND link_id = ?";
        jdbcTemplate.update(sql, userId, linkId);
    }

    public List<UserLinkDto> findAllByUserId(long userId) {
        String sql = "SELECT user_id, link_id FROM user_links where user_id = ${userId}";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserLinkDto() {{
            setUserId(rs.getLong(userIdLabel));
            setLinkId(rs.getLong(linkIdLabel));
        }});
    }

    public List<UserLinkDto> findAllByLinkId(long linkId) {
        String sql = "SELECT user_id, link_id FROM user_links where link_id = ${link_id}";
        return jdbcTemplate.query(sql, (rs, rowNum) -> new UserLinkDto() {{
            setUserId(rs.getLong(userIdLabel));
            setLinkId(rs.getLong(linkIdLabel));
        }});
    }

    private void setCreatedBy(UserLinkDto linkDto) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        linkDto.setCreatedBy(stackTrace[depth].getClassName() + "." + stackTrace[depth].getMethodName());
    }
}
