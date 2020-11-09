package com.walcron.springcloud.gateway.web.api.rest.greeter;

public class GreeterResponse {
    private String message;

    public GreeterResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
