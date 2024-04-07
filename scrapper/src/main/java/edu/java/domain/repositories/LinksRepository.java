package edu.java.domain.repositories;

import edu.java.domain.entities.LinkDto;
import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class LinksRepository {
    private final JdbcTemplate jdbcTemplate;
    private final String idString = "id";
    private final String urlString = "url";
    private final String createdAtString = "created_at";
    private final String createdByString = "created_by";
    private final int depth = 3;

    public LinksRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LinkDto add(LinkDto dto) {
        dto.setCreatedAt(LocalDateTime.now());
        setCreatedBy(dto);
        String sql = "INSERT INTO links (url, created_at, created_by) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, dto.getUrl(), dto.getCreatedAt(), dto.getCreatedBy());
        return dto;
    }

    public void remove(long userId) {
        String sql = "DELETE FROM links WHERE id = ?";
        jdbcTemplate.update(sql, userId);
    }

    public void update(LinkDto link) {
        String sql = "UPDATE links SET url = ?, created_at = ?, created_by = ?, updated_at = ? WHERE id = ?";
        jdbcTemplate.update(sql, link.getUrl(),
            Timestamp.valueOf(link.getCreatedAt()), link.getCreatedBy(), link.getId(), link.getLastCheckedAt()
        );
    }

    public List<LinkDto> findAll() {
        String sql = "SELECT id, url, created_at, created_by FROM links";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
            new LinkDto(
                rs.getLong(idString),
                rs.getString(urlString),
                rs.getTimestamp(createdAtString).toLocalDateTime(),
                rs.getString(createdByString)
            )
        );
    }

    public List<LinkDto> findLinksNotCheckedSince(Duration duration) {
        LocalDateTime cutoff = LocalDateTime.now().minus(duration);
        String sql = "SELECT * FROM links WHERE last_checked_at < ?";
        return jdbcTemplate.query(sql, new Object[] {Timestamp.valueOf(cutoff)}, (rs, rowNum) ->
            new LinkDto(
                rs.getLong(idString),
                rs.getString(urlString),
                rs.getTimestamp(createdAtString).toLocalDateTime(),
                rs.getString(createdByString)
            ));
    }

    private void setCreatedBy(LinkDto linkDto) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        linkDto.setCreatedBy(stackTrace[depth].getClassName() + "." + stackTrace[depth].getMethodName());
    }
}
