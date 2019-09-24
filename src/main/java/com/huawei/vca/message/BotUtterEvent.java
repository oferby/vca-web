package com.huawei.vca.message;

public class BotUtterEvent extends BotEvent{

    private String id;
    private String text;
    private Integer actionId;

    public BotUtterEvent() {
    }

    public BotUtterEvent(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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
