package com.huawei.vca.nlg;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.Option;
import com.huawei.vca.message.PredictedAction;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;
import com.huawei.vca.repository.graph.OptionNode;

import java.util.List;

public interface ResponseGenerator {

    BotUtterEvent generateResponse(String actionId, Class skill);

    BotUtterEvent generateResponse(String actionId, List<OptionNode> options, Class skill);

    BotUtterEvent generateNoSolution(Class skill);

    BotUtterEvent generateResponseForMenuItem(MenuItemEntity menuItemEntity, Class skill);

    BotUtterEvent generateQueryResponseForSlot(SlotEntity slotEntity, Class skill);

    void addDefaultUtterEvent(Dialogue dialogue);

    void generateUtterForPredictedAction(Dialogue dialogue, PredictedAction bestPredictedAction);

}
