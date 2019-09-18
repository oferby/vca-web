package com.huawei.vca.message;

import com.huawei.vca.intent.NluResponse;

public class UserUtter extends Event{

    private String text;
    private NluResponse nluResponse;


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

    public NluResponse getNluResponse() {
        return nluResponse;
    }

    public void setNluResponse(NluResponse nluResponse) {
        this.nluResponse = nluResponse;
    }
}
