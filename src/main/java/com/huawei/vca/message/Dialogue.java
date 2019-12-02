package com.huawei.vca.message;

import java.util.*;

public class Dialogue {

    private String sessionId;
    private String text;
    private List<Event> history;
    private NluEvent lastNluEvent;
    private boolean isTraining;
    private Map<String,Float> observations;
    private Map<String,Float> state;
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

    public void addState(String key, Float value){

        if (this.state == null) {
            this.state = new HashMap<>();
        }

        this.state.put(key,value);
    }

    public Float getState(String key){

        if (this.state == null)
            return null;

        return this.state.get(key);
    }

    public boolean isNeedOperator() {
        return needOperator;
    }

    public void setNeedOperator(boolean needOperator) {
        this.needOperator = needOperator;
    }

    public void addObservation(String key, Float confidence){
        if (observations == null)
            observations = new HashMap<>();
        observations.put(key, confidence);
    }

    public Map<String, Float> getObservations() {
        return observations;
    }

    public void setObservations(Map<String, Float> observations) {
        this.observations = observations;
    }

    public Map<String, Float> getState() {
        return state;
    }

    public void setState(Map<String, Float> state) {
        this.state = state;
    }

    public void resetObservations(){
        this.observations = new HashMap<>();
    }

}
