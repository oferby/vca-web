package com.huawei.vca.nlg;

import com.huawei.vca.message.BotUtterEvent;

public interface ResponseGenerator {

    BotUtterEvent generateResponse(String actionId);

}
