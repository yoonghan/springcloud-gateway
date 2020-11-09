package com.walcron.springcloud.gateway.web.api.socketio;

public class Event {
    private String eventId;
    private String eventDt;

    public Event(String eventId, String eventDt) {
        this.eventId = eventId;
        this.eventDt = eventDt;
    }

    public String getEventId() {
        return eventId;
    }

    public String getEventDt() {
        return eventDt;
    }
}