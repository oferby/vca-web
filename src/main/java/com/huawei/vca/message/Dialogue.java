package com.huawei.vca.message;

import java.util.*;

public class Dialogue {

    private String sessionId;
    private String text;
    private List<Event> history;
    private NluEvent lastNluEvent;
    private boolean isTraining;
    private Map<String,String> properties;
    private boolean needOperator;

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

    public NluEvent getLastNluEvent() {
        return lastNluEvent;
    }

    public void setLastNluEvent(NluEvent lastNluEvent) {
        this.lastNluEvent = lastNluEvent;
    }

    public boolean isTraining() {
        return isTraining;
    }

    public void setTraining(boolean training) {
        isTraining = training;
    }

    public void addProperty(String key, String value){

        if (this.properties == null) {
            this.properties = new HashMap<>();
        }

        this.properties.put(key,value);
    }

    public String getProperty(String key){

        if (this.properties == null)
            return null;

        return this.properties.get(key);
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public boolean isNeedOperator() {
        return needOperator;
    }

    public void setNeedOperator(boolean needOperator) {
        this.needOperator = needOperator;
    }
}
