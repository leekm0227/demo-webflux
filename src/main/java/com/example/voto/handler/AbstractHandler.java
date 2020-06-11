package com.example.voto.handler;

import com.example.voto.config.ErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.CorePublisher;
import reactor.core.publisher.Mono;

import java.util.function.BiFunction;

class AbstractHandler<T> {
    private Class<T> cls;

    AbstractHandler(Class<T> cls) {
        this.cls = cls;
    }

    BiFunction<HttpStatus, CorePublisher<T>, Mono<ServerResponse>> response = (status, body) ->
            ServerResponse.status(status)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(body, cls);

    static class Error {
        private static BiFunction<HttpStatus, ErrorCode, Mono<ServerResponse>> response = (status, body) ->
                ServerResponse.status(status)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(body, ErrorCode.class);

        static Mono<ServerResponse> duplicateKey(Throwable throwable) {
            return response.apply(HttpStatus.BAD_REQUEST, ErrorCode.DUPLICATE_KEY);
        }
    }
}
