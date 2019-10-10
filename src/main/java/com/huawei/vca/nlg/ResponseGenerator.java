package com.huawei.vca.nlg;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.repository.entity.SlotEntity;

public interface ResponseGenerator {

    BotUtterEvent generateResponse(String actionId);

    BotUtterEvent getQueryForSlot(SlotEntity slotEntity);

}
