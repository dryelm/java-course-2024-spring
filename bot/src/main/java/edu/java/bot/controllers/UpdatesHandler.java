package edu.java.bot.controllers;

import edu.java.ApiErrorResponse;
import java.util.Arrays;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import reactor.core.publisher.Mono;


@RestControllerAdvice
public class UpdatesHandler {
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
