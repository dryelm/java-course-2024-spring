package edu.java.domain.entities;

import java.time.LocalDateTime;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class LinkDto extends DtoBase {
    @Getter
    @Setter
    private String url;

    @Getter
    @Setter
    private LocalDateTime lastCheckedAt;

    @Getter
    @Setter
    private LocalDateTime updatedAt;

    public LinkDto(Long id,
        String url,
        LocalDateTime createdAt,
        String createdBy,
        LocalDateTime updatedAt,
        LocalDateTime lastCheckedAt) {
        this.setId(id);
        this.setCreatedAt(createdAt);
        this.setCreatedBy(createdBy);
        this.url = url;
        this.updatedAt = updatedAt;
        this.lastCheckedAt = lastCheckedAt != null ? lastCheckedAt : LocalDateTime.MIN;
    }

    public LinkDto() {
        this.lastCheckedAt = LocalDateTime.MIN;
        this.updatedAt = LocalDateTime.MIN;
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof LinkDto linkDto)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return Objects.equals(getUrl(), linkDto.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUrl());
    }
}
