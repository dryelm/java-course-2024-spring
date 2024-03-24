package edu.java.controllers;

import edu.java.ApiErrorResponse;
import edu.java.services.interfaces.TgChatService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class TgChatController {
    private final TgChatService tgChatService;

    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    @PostMapping("/tg-chat/{id}")
    public Mono<ResponseEntity<?>> registerChat(@PathVariable long id) {
        return Mono.fromCallable(() -> {
            tgChatService.register(id);
            return ResponseEntity.ok().build();
        });
    }

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат удален"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    @DeleteMapping("/tg-chat/{id}")
    public Mono<ResponseEntity<?>> deleteChat(@PathVariable long id) {
        return Mono.fromCallable(() -> {
            tgChatService.unregister(id);
            return ResponseEntity.ok().build();
        });
    }
}
