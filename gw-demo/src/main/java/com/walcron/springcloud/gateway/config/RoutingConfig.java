package com.walcron.springcloud.gateway.config;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

@Configuration("routing-config")
public class RoutingConfig {

    Log log = LogFactory.getLog(this.getClass());

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        log.info("Registring router");
        return builder.routes()
                .route(p -> p
                    .path("/api/v1/*")
                    .filters(f -> f.setResponseHeader("Access-Control-Allow-Origin", "http://localhost:3000")
                    )
                    .uri("lb://greetings-service/api/v2")
                    .predicate(new Predicate<ServerWebExchange>() {
                        @Override
                        public boolean test(ServerWebExchange serverWebExchange) {
                            return serverWebExchange.getRequest().getMethod().matches("GET") &&
                                    serverWebExchange.getRequest().getPath().pathWithinApplication().value().startsWith("/api/v2");
                        }
                    })
                )
                .build();
    }
}
