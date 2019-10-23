package com.huawei.vca.message;

import com.huawei.vca.message.Event;

public class BotEvent extends Event {

    private String skillId;

    public String getSkillId() {
        return skillId;
    }

    public void setSkillId(String skillId) {
        this.skillId = skillId;
    }
}
