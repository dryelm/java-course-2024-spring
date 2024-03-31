package edu.java.domain.entities;

import java.util.Objects;
import lombok.Getter;
import lombok.Setter;

public class UserLinkDto extends DtoBase {
    @Getter
    @Setter
    private long userId;
    @Getter
    @Setter
    private long linkId;

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserLinkDto that)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        return getUserId() == that.getUserId() && getLinkId() == that.getLinkId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUserId(), getLinkId());
    }
}
