package com.example.voto.handler;

import com.example.voto.domain.User;
import com.example.voto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigInteger;

@Component
public class UserHandler extends AbstractHandler<User> {

    @Autowired
    UserRepository userRepository;

    UserHandler() {
        super(User.class);
    }

    public Mono<ServerResponse> userList(ServerRequest request) {
        Flux<User> user = userRepository.findAllByOrderByCreateAtDesc(PageRequest.of(
                request.queryParam("page").map(Integer::valueOf).orElse(1),
                request.queryParam("size").map(Integer::valueOf).orElse(10)));
        return response.apply(HttpStatus.OK, user);
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userRepository.findById(new BigInteger(request.pathVariable("id"))), User.class);
    }

    public Mono<ServerResponse> createUser(ServerRequest request) {
        return request.bodyToMono(User.class).flatMap(user -> {
                    return userRepository.save(user)
                            .onErrorResume(Error::duplicateKey);

                    return response.apply(HttpStatus.OK, )
                            .body(response, User.class);
                }
        );
    }
}
