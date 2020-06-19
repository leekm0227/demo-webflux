package com.example.voto.handler;

import com.example.voto.domain.Result;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.function.Function;

import static java.lang.StrictMath.max;

class AbstractHandler {
    Function<Mono<Result>, Mono<ServerResponse>> response =
            (body) -> ServerResponse.status(HttpStatus.OK)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body, Result.class);

    Function<ServerRequest, PageRequest> paging =
            (request) -> PageRequest.of(
                    max(request.queryParam("page").map(Integer::valueOf).orElse(1) - 1, 0),
                    request.queryParam("size").map(Integer::valueOf).orElse(10)
            );
}
