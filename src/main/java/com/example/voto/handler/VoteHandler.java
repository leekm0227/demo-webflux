package com.example.voto.handler;

import com.example.voto.domain.Vote;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Component
public class VoteHandler {

    public Mono<ServerResponse> getVote(ServerRequest request) {
        Vote vote = new Vote();
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(vote), Vote.class);
    }
}
