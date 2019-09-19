package com.huawei.vca.message;

public class Intent {

    private String intent;
    private float confidence;

    public Intent() {
    }

    public Intent(String intent, float confidence) {
        this.intent = intent;
        this.confidence = confidence;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }
}
