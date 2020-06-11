package com.example.voto.config;

import com.example.voto.handler.UserHandler;
import com.example.voto.handler.VoteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler handler) {
        return RouterFunctions.route(GET("/users").and(accept(MediaType.APPLICATION_JSON)), handler::userList)
                .andRoute(GET("/users/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getUserById)
                .andRoute(POST("/users").and(accept(MediaType.APPLICATION_JSON)), handler::createUser);
    }

    @Bean
    public RouterFunction<ServerResponse> voteRoutes(VoteHandler handler) {
        return RouterFunctions.route(GET("/votes").and(accept(MediaType.APPLICATION_JSON)), handler::getVote);
    }
}
