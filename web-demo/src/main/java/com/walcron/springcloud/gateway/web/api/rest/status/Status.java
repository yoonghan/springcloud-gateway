package com.walcron.springcloud.gateway.web.api.rest.status;

public class Status {
    private String status;

    public Status(String status) {
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
