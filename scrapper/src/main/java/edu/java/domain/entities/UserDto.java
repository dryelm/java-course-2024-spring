package edu.java.domain.entities;

import java.util.Objects;

public class UserDto extends DtoBase {

    public UserDto() {
    }

    @Override public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof UserDto userDto)) {
            return false;
        }
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode());
    }
}
