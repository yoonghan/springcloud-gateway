package com.walcron.springcloud.gateway.filter;


import com.walcron.springcloud.gateway.property.TgcsPropertyReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

//implements Ordered if customized here.
public class IpCustomFilter implements GlobalFilter {

    Logger log = LoggerFactory.getLogger(this.getClass());
    List<String> whitelist = new ArrayList<>();

    public IpCustomFilter(TgcsPropertyReader tgcsPropertyReader) {
        whitelist = tgcsPropertyReader.getAllowedIps();
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("ip filtering");

        String id = exchange.getRequest().getRemoteAddress().getHostName();

        if (!whitelist.contains(id)) {

            log.error("ip not allowed {}", id);
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

        return chain.filter(exchange);

    }

}