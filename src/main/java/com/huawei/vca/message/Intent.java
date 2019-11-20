package com.huawei.vca.message;

public class Intent {

    private Act act;
    private float confidence;

    public Intent() {
    }

    public Intent(String act, float confidence) {
        this.act = Act.getByValue(act);
        this.confidence = confidence;
    }

    public Act getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = Act.valueOf(act);
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }
}
