package edu.java.domain.entities;

import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

public class DtoBase {
    @Getter
    @Setter
    private LocalDateTime createdAt;

    @Getter
    @Setter
    private String createdBy;

    @Getter
    @Setter
    private Long id;

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof DtoBase dtoBase)) {
            return false;
        }

        if (!getId().equals(dtoBase.getId())) {
            return false;
        }
        if (!getCreatedAt().equals(dtoBase.getCreatedAt())) {
            return false;
        }
        return getCreatedBy().equals(dtoBase.getCreatedBy());
    }

    @Override
    public int hashCode() {
        int result = getCreatedAt().hashCode();
        result = 31 * result + getCreatedBy().hashCode();
        result = 31 * result + (int) (getId() ^ (getId() >>> 32));
        return result;
    }
}
