package com.example.voto.handler;

import com.example.voto.domain.Result;
import com.example.voto.domain.User;
import com.example.voto.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.Objects;

@Component
public class UserHandler extends AbstractHandler {

    @Autowired
    UserRepository userRepository;

    public Mono<ServerResponse> userList(ServerRequest request) {
        return response.apply(userRepository.findAllByOrderByCreateAtDesc(paging.apply(request))
                .collectList()
                .flatMap(users -> Mono.just(new Result() {{
                    addData("user", users);
                }}))
        );
    }

    public Mono<ServerResponse> getUserById(ServerRequest request) {
        return response.apply(userRepository.findById(request.pathVariable("id"))
                .flatMap(user -> Mono.just(new Result() {{
                    addData("user", user);
                }}))
        );
    }

    public Mono<ServerResponse> info(ServerRequest request) {
        String userId = request.headers().firstHeader("auth");
        assert userId != null;
        return response.apply(userRepository.findById(userId)
                .flatMap(user -> Mono.just(new Result() {{
                    addData("user", user);
                }}))
        );
    }

    public Mono<ServerResponse> join(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(user -> response.apply(userRepository.save(user)
                                .flatMap(savedUser -> Mono.just(new Result() {{
                                    addData("user", savedUser);
                                }}))
                        )
                );
    }

    public Mono<ServerResponse> login(ServerRequest request) {
        return request.bodyToMono(User.class)
                .flatMap(user -> response.apply(userRepository.findByAccountAndPassword(user.getAccount(), user.getPassword())
                                .flatMap(findUser -> Mono.just(new Result() {{
                                    addData("user", findUser);
                                }}))
                        )
                );
    }
}
