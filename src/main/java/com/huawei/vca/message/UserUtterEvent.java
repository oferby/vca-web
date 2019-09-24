package com.huawei.vca.message;

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
}
