package com.huawei.vca.repository;

import com.huawei.vca.message.Event;

public class BotEvent extends Event {

    private Integer actionId;

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }


}
