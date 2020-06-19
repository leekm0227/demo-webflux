package com.example.voto.config;

import com.example.voto.handler.UserHandler;
import com.example.voto.handler.VoteHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class RouterConfig {

    @Bean
    CorsWebFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsWebFilter(source);
    }

    @Bean
    public RouterFunction<ServerResponse> userRoutes(UserHandler handler) {
        return RouterFunctions.route(GET("/users").and(accept(MediaType.APPLICATION_JSON)), handler::userList)
//                .andRoute(GET("/users/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getUserById)
                .andRoute(GET("/users/info").and(accept(MediaType.APPLICATION_JSON)), handler::info)
                .andRoute(POST("/users/join").and(accept(MediaType.APPLICATION_JSON)), handler::join)
                .andRoute(POST("/users/login").and(accept(MediaType.APPLICATION_JSON)), handler::login);
    }

    @Bean
    public RouterFunction<ServerResponse> voteRoutes(VoteHandler handler) {
        return RouterFunctions.route(GET("/votes").and(accept(MediaType.APPLICATION_JSON)), handler::getVote)
                .andRoute(GET("/votes/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::getVoteById)
                .andRoute(POST("/votes").and(accept(MediaType.APPLICATION_JSON)), handler::createVote)
                .andRoute(PUT("/votes/{id}").and(accept(MediaType.APPLICATION_JSON)), handler::updateVote)
                .andRoute(POST("/votes/{id}/items").and(accept(MediaType.APPLICATION_JSON)), handler::addVoteItem)
                .andRoute(POST("/votes/{id}/items/{itemId}").and(accept(MediaType.APPLICATION_JSON)), handler::likeVoteItem);
    }
}
