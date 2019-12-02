package com.huawei.vca.conversation.policy;

import com.huawei.vca.message.BotAct;
import com.huawei.vca.message.BotEvent;

import java.util.Map;

public interface PolicyController {

    BotAct getBotAct(Map<String,Float> userAction, Map<String,Float> state);

}
