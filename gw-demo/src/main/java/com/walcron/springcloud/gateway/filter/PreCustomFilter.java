package com.walcron.springcloud.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

//implements Ordered if customized here.
public class PreCustomFilter implements GlobalFilter {


    Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("pre filter");
        return chain.filter(exchange);
    }

//    @Override
//    public int getOrder() {
//        return -1;
//    }
}
