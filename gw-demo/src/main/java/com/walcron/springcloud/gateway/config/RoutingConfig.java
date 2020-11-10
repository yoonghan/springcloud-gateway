package com.walcron.springcloud.gateway.config;

import com.walcron.springcloud.gateway.filter.ResourceFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.server.ServerWebExchange;

import java.util.function.Predicate;

@Configuration("routing-config")
public class RoutingConfig {

    Logger log = LoggerFactory.getLogger(this.getClass());

    /**Resources are separated hence required own filter**/
    @Bean
    RouterFunction staticResourceLocator(){
        /**For local files use[S]
        return RouterFunctions.resources("/portal/**", new FileSystemResource("D:\\website\\spring-cloud-static-resource/"));
         [E]**/
        /** Not preferred method in the end, there is this issue of no cache control.
         * https://stackoverflow.com/questions/58661768/how-to-serve-static-resources-using-webflux-but-with-cache-support
         * **/
        log.info("run resource check");
        return RouterFunctions
                .resources("/static/**", new ClassPathResource("static/"))
                .filter(new ResourceFilter());
    }

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
