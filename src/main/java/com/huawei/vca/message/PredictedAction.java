package com.huawei.vca.message;

public class PredictedAction implements Comparable<PredictedAction>{

    private BotEvent botEvent;
    private float confidence;

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

    @Override
    public int compareTo(PredictedAction action) {

        if (this.confidence > action.confidence)
            return 1;
        else if (this.confidence < action.confidence)
            return -1;

        return 0;
    }
}
