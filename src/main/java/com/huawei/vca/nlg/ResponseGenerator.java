package com.huawei.vca.nlg;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;

public interface ResponseGenerator {

    BotUtterEvent generateResponse(String actionId);

    BotUtterEntity generateResponse(MenuItemEntity menuItemEntity);

    BotUtterEvent generateQueryResponseForSlot(SlotEntity slotEntity);

}
