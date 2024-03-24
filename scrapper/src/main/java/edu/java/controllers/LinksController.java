package edu.java.controllers;

import edu.java.AddLinkRequest;
import edu.java.ApiErrorResponse;
import edu.java.LinkResponse;
import edu.java.LinksListResponse;
import edu.java.services.interfaces.LinkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import java.net.URI;
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

    private final LinkService linkService;

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
        return Mono.fromCallable(() -> {
            var links = linkService.listAll(tgChatId);
            if (links != null) {
                var linkResponses = links.stream()
                    .map(link -> new LinkResponse(link.getUrl(), link.getId()))
                    .toArray(LinkResponse[]::new);
                return ResponseEntity.ok(new LinksListResponse(linkResponses, linkResponses.length));
            }

            return ResponseEntity.ok(new LinksListResponse(new LinkResponse[0], 0));
        });
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
        return Mono.fromCallable(() -> {
            var link = linkService.add(tgChatId, URI.create(addLinkRequest.link()));
            return ResponseEntity.ok(new LinkResponse(link.getUrl(), link.getId()));
        });
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
        return Mono.fromCallable(() -> {
            var link = linkService.remove(tgChatId, URI.create(addLinkRequest.link()));
            return ResponseEntity.ok(new LinkResponse(link.getUrl(), link.getId()));
        });
    }

}
