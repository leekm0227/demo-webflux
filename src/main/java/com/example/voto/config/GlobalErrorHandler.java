package com.example.voto.config;

import com.mongodb.MongoWriteException;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

@Component
public class GlobalErrorHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        if (ex instanceof MongoWriteException) {
            System.out.println("exception : MongoWriteException");
        }

        return null;
    }
}
