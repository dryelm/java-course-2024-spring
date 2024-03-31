package edu.java;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record UpdateRequestDto(
    @Positive long id,
    @NotBlank String url,
    String description,
    @NotEmpty List<Long> tgChatIds
    ){

}
