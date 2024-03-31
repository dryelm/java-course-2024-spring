package edu.java.clients.stackoverflow.responses;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import lombok.Getter;

public class StackOverflowQuestionResponse {
    @Getter @JsonProperty("title")
    private String title;

    @Getter @JsonProperty("link")
    private String link;

    @Getter @JsonProperty("creation_date")
    private OffsetDateTime creationDate;

    @Getter  @JsonProperty("updated_at")
    private OffsetDateTime updatedAt;
}
