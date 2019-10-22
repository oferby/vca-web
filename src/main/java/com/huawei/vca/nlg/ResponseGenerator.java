package com.huawei.vca.nlg;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Option;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;
import com.huawei.vca.repository.graph.OptionNode;

import java.util.List;

public interface ResponseGenerator {

    BotUtterEvent generateResponse(String actionId);

    BotUtterEvent generateResponse(String actionId, List<OptionNode> options);

    BotUtterEvent generateNoSolution();

    BotUtterEvent generateResponseForMenuItem(MenuItemEntity menuItemEntity);

    BotUtterEvent generateQueryResponseForSlot(SlotEntity slotEntity);

}
