package com.walcron.springcloud.gateway.web.api.rest.status;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class StatusHandler {
    @GetMapping(value="/status")
    public Mono<Status> getStatus() {
        return Mono.just(new Status("up"));
    }
}
