package com.walcron.springcloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

//implements Ordered if customized here.
public class PostCustomFilter implements GlobalFilter {

    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("post filter");

        Mono<Void> chainFilter = chain.filter(exchange);

        log.info("Request Path: {}", exchange.getRequest().getPath(), exchange.getRequest().getQueryParams());
        log.info("Request Query Params: {}", exchange.getRequest().getQueryParams());
        log.info("Request Header: {}", exchange.getRequest().getHeaders().keySet().stream().count());
        exchange.getRequest().getBody()
                .flatMap(body -> {
                    log.info("Request Body:"+ body.toString());
                    return Mono.just(body.toString());
                });
        exchange.getResponse().beforeCommit(() ->{
            ServerHttpResponse response = exchange.getResponse();
            log.info("Response Status: {}", response.getStatusCode());
            return Mono.empty();
        });
        return chainFilter;

    }

//    @Override
//    public int getOrder() {
//        return 1;
//    }
}
