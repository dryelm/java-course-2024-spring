package edu.java.domain.repositories.jpa;

import edu.java.domain.entities.LinkDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter @Entity
@Table(name = "links")
@AllArgsConstructor
@NoArgsConstructor
public class LinkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String url;

    @Column(name = "updated_at")
    private OffsetDateTime updatedAt;

    @Column(name = "last_checked_at")
    private OffsetDateTime lastCheckedAt;

    @Column(name = "created_at")
    private OffsetDateTime createdAt;

    @Column(name = "created_by")
    private String createdBy;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "links")
    private Set<TgUserEntity> tgChats = new HashSet<>();

    public LinkEntity(
        String url,
        OffsetDateTime updatedAt,
        OffsetDateTime lastCheckedAt
    ) {
        this.url = url;
        this.updatedAt = updatedAt;
        this.lastCheckedAt = lastCheckedAt;
    }

    public LinkDto toDto() {
        return new LinkDto(
            this.id,
            this.url,
            this.createdAt.toLocalDateTime(),
            this.createdBy
        ) {

        };
    }

}
