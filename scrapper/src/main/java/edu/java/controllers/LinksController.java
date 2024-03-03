package edu.java.controllers;

import edu.java.AddLinkRequest;
import edu.java.ApiErrorResponse;
import edu.java.LinkResponse;
import edu.java.LinksListResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
public class LinksController {

    @Operation(summary = "Получить все отслеживаемые ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылки успешно получены"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    @GetMapping("/links")
    public Mono<ResponseEntity<LinksListResponse>> getLinks(@RequestHeader("Tg-Chat-Id") long tgChatId) {
        return Mono.fromCallable(() -> ResponseEntity.ok(new LinksListResponse(new LinkResponse[0], 0)));
    }

    @Operation(summary = "Добавить отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно добавлена"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    @PostMapping("/links")
    public Mono<ResponseEntity<LinkResponse>> setLink(
        @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        return Mono.fromCallable(() -> ResponseEntity.ok(new LinkResponse(addLinkRequest.link(), 1)));
    }

    @Operation(summary = "Убрать отслеживание ссылки")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ссылка успешно убрана"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(
                         mediaType = "application/json",
                         schema = @Schema(implementation = ApiErrorResponse.class))
        )
    })
    @PostMapping("/links/delete")
    public Mono<ResponseEntity<LinkResponse>> deleteLink(
        @RequestHeader("Tg-Chat-Id") long tgChatId,
        @RequestBody AddLinkRequest addLinkRequest
    ) {
        return Mono.fromCallable(() -> ResponseEntity.ok(new LinkResponse(addLinkRequest.link(), 1)));
    }

}
