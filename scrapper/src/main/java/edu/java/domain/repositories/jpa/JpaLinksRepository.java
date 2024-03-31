package edu.java.domain.repositories.jpa;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface JpaLinksRepository extends JpaRepository<LinkEntity, Long> {
    Optional<LinkEntity> findByUrl(String url);

    @Query("FROM LinkEntity link WHERE link.lastCheckedAt <= :time")
    List<LinkEntity> findByLastCheckTime(OffsetDateTime time);
}
