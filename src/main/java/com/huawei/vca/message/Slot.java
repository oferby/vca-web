package com.huawei.vca.message;

import java.util.Objects;

public class Slot {

    private String key;
    private String value;
    private float confidence;

    public Slot() {
    }

    public Slot(String key, String value, float confidence) {
        this.key = key;
        this.value = value;
        this.confidence = confidence;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public float getConfidence() {
        return confidence;
    }

    public void setConfidence(float confidence) {
        this.confidence = confidence;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slot)) return false;
        Slot slot = (Slot) o;
        return Float.compare(slot.getConfidence(), getConfidence()) == 0 &&
                Objects.equals(getKey(), slot.getKey()) &&
                Objects.equals(getValue(), slot.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue(), getConfidence());
    }
}
