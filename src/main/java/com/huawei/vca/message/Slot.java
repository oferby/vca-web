package com.huawei.vca.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.Objects;

public class Slot {

    private String key;
    private String value;
    private float confidence;
    private Integer start;
    private Integer end;
    private ValueType valueType;
    private Integer minValue;
    private Integer maxValue;

    public Slot() {
    }

    public Slot(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public Slot(String key, String value, float confidence) {
        this.key = key;
        this.value = value;
        this.confidence = confidence;
    }

    public Slot(String key, String value, float confidence, Integer start, Integer end) {
        this.key = key;
        this.value = value;
        this.confidence = confidence;
        this.start = start;
        this.end = end;
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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    public ValueType getValueType() {
        return valueType;
    }

    public void setValueType(ValueType valueType) {
        this.valueType = valueType;
    }

    public Integer getMinValue() {
        return minValue;
    }

    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    public Integer getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Slot)) return false;
        Slot slot = (Slot) o;
        return Float.compare(slot.getConfidence(), getConfidence()) == 0 &&
                Objects.equals(getKey(), slot.getKey()) &&
                Objects.equals(getValue(), slot.getValue()) &&
                Objects.equals(getStart(), slot.getStart()) &&
                Objects.equals(getEnd(), slot.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue(), getConfidence(), getStart(), getEnd());
    }

    @Override
    public String toString() {
        return "Slot{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", confidence=" + confidence +
                ", start=" + start +
                ", end=" + end +
                '}';
    }

    @JsonIgnore
    public Slot getSmallCopy() {
        Slot newCopy = new Slot();
        newCopy.setKey(this.key);
        newCopy.setValue(this.value);
        return newCopy;
    }
}
