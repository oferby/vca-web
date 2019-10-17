package com.huawei.vca.message;

import java.util.Objects;

public class UserUtterEvent extends Event{

    private String text;
    private NluEvent nluEvent;

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
