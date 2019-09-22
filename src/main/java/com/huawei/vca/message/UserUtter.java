package com.huawei.vca.message;

public class UserUtter extends Event{

    private String text;
    private NluEvent nluEvent;

    public UserUtter() {
        super();
    }

    public UserUtter(String text) {

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
