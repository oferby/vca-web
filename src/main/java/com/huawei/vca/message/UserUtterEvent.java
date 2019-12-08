package com.huawei.vca.message;

import java.util.Map;
import java.util.Objects;

public class UserUtterEvent extends Event{

    private String text;
    private NluEvent nluEvent;
    private Map<String, Float> observations;
    private Map<String, Float> state;

    public UserUtterEvent() {
        super();
    }

    public UserUtterEvent(String text) {

        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public NluEvent getNluEvent() {
        return nluEvent;
    }

    public void setNluEvent(NluEvent nluEvent) {
        this.nluEvent = nluEvent;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserUtterEvent)) return false;
        UserUtterEvent that = (UserUtterEvent) o;
        return Objects.equals(getText(), that.getText()) &&
                Objects.equals(getNluEvent(), that.getNluEvent());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getNluEvent());
    }
}
