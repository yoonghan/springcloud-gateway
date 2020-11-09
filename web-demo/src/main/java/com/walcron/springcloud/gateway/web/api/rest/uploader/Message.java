package com.walcron.springcloud.gateway.web.api.rest.uploader;

public class Message {
    private String status;

    public Message(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
