package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.BotEvent;
import com.huawei.vca.message.Dialogue;

public interface PostProcessorSkill {

    void process(Dialogue dialogue, BotEvent botEvent);

}
