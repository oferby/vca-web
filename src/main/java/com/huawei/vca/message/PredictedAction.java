package com.huawei.vca.message;

import java.util.HashMap;
import java.util.Map;

public class PredictedAction implements Comparable<PredictedAction>{

    private BotEvent botEvent;
    private float confidence = 0;
    private Map<String, String> properties;

    public BotEvent getBotEvent() {
        return botEvent;
    }

    public void setBotEvent(BotEvent botEvent) {
        this.botEvent = botEvent;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
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

    @Override
    public int compareTo(PredictedAction action) {

        if (this.confidence > action.confidence)
            return -1;
        else if (this.confidence < action.confidence)
            return 1;

        return 0;
    }

    @Override
    public String toString() {
        String botUtterString = null;
        if (botEvent != null & botEvent instanceof BotUtterEvent) {
            botUtterString = ((BotUtterEvent) botEvent).getId();
            if (botUtterString == null)
                botUtterString = ((BotUtterEvent) botEvent).getText();
        }

        return "PredictedAction{" +
                "botEvent=" +  botUtterString +
                ", confidence=" + confidence +
                ", properties=" + properties +
                '}';
    }
}
