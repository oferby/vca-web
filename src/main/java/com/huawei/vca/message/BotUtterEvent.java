package com.huawei.vca.message;

public class BotUtterEvent extends BotEvent{

    private String text;
    private Integer actionId;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }
}
