package com.huawei.vca.message;

public class DialogueSummary {

    private String id;
    private float confidence;
    private Confidence confidenceString;

    public DialogueSummary() {
    }

    public DialogueSummary(String id, float confidence) {
        this.id = id;
        this.confidence = confidence;
        this.setConfidenceString();
    }

    public DialogueSummary(String id, float confidence, boolean isTraining, boolean needOperator) {
        this.id = id;
        this.confidence = confidence;

        if (needOperator) {
            this.confidenceString = Confidence.NEED_OPERATOR;
        } else if (isTraining) {
            this.confidenceString = Confidence.TRAIN;
        } else {
            this.setConfidenceString();
        }

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
        this.setConfidenceString();
    }

    private void setConfidenceString() {
        if (confidence < 0.3) {
            this.confidenceString = Confidence.LOW;
        } else if (confidence < 0.6) {
            this.confidenceString = Confidence.MEDIUM;
        } else {
            this.confidenceString = Confidence.HIGH;
        }

    }

    public Confidence getConfidenceString() {
        return confidenceString;
    }

    public void setConfidenceString(Confidence confidenceString) {
        this.confidenceString = confidenceString;
    }
}
