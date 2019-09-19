package com.huawei.vca.message;

import java.util.*;

public class Dialogue {

    private String sessionId;
    private String text;
    private List<Event> history;
    private Map<String, String> slots;
    private NluEvent lastNluEvent;

    public Dialogue() {
    }

    public Dialogue(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId() {
        sessionId = UUID.randomUUID().toString();
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Event> getHistory() {
        return history;
    }

    public void setHistory(List<Event> history) {
        this.history = history;
    }

    public void addToHistory(Event event) {

        if (history==null){
            history = new ArrayList<>();
        }

        history.add(event);

    }

    public Map<String, String> getSlots() {
        return slots;
    }

    public void setSlots(Map<String, String> slots) {
        this.slots = slots;
    }

    public NluEvent getLastNluEvent() {
        return lastNluEvent;
    }

    public void setLastNluEvent(NluEvent lastNluEvent) {
        this.lastNluEvent = lastNluEvent;
    }
}
