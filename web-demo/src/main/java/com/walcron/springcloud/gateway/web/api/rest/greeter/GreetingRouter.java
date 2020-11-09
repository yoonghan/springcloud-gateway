package com.walcron.springcloud.gateway.web.api.rest.greeter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration("GreetingRouter")
public class GreetingRouter {
    @Bean
    public RouterFunction<ServerResponse> route(GreetingHandler greetingHandler) {
        return RouterFunctions
                .route(
                        RequestPredicates.GET("/api/v1/greetings").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        greetingHandler::hello
                )
                .andRoute(
                    RequestPredicates.GET("/api/v1/greetings/{language}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                    greetingHandler::helloByLanguage
                )
                .andRoute(
                        RequestPredicates.POST("/api/v1/greetings").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        greetingHandler::sayHello
                )
                .andRoute(
                        RequestPredicates.POST("/api/v1/greetings/{language}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        greetingHandler::sayHelloByLanguage
                )
                .andRoute(
                        RequestPredicates.GET("/api/v2/greetings").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        greetingHandler::hello
                )
                .andRoute(
                        RequestPredicates.GET("/api/v2/greetings/{language}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        greetingHandler::helloByLanguage
                )
                .andRoute(
                        RequestPredicates.POST("/api/v2/greetings").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        greetingHandler::sayHello
                )
                .andRoute(
                        RequestPredicates.POST("/api/v2/greetings/{language}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON)),
                        greetingHandler::sayHelloByLanguage
                );
    }
}