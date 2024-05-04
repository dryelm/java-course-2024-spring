package edu.java.bot.controllers;

import com.pengrad.telegrambot.request.SendMessage;
import edu.java.ApiErrorResponse;
import edu.java.UpdateRequestDto;
import edu.java.bot.services.Bot;
import edu.java.bot.utils.UpdateParserUtil;
import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;

@RestControllerAdvice
@RequiredArgsConstructor
public class UpdatesHandler {
    private final Bot bot;

    public void handleUpdate(UpdateRequestDto update) {
        List<SendMessage> requests = UpdateParserUtil.toRequestList(update);

        for (SendMessage request : requests) {
            bot.execute(request);
        }
    }

    @ExceptionHandler(Exception.class)
    public Mono<ResponseEntity<ApiErrorResponse>> handle(Exception ex) {
        return Mono.fromCallable(() -> {
            return ResponseEntity
                .badRequest()
                .body(
                    new ApiErrorResponse(
                        "Data not valid",
                        "400", ex.toString(),
                        ex.getLocalizedMessage(),
                        Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).toArray(String[]::new
                        )
                    ));
        });
    }
}
