package com.huawei.vca.conversation;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.graph.ActionNode;
import com.huawei.vca.repository.graph.ConversationGraphController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Set;

@Controller
public class GraphConversationStateTracker implements SkillController {

    private static final Logger logger = LoggerFactory.getLogger(GraphConversationStateTracker.class);

    @Autowired
    private ConversationGraphController conversationGraphController;

    private static String graphLocation = "graph_location";

    @Override
    public PredictedAction getPredictedAction(Dialogue dialogue) {

        PredictedAction predictedAction = new PredictedAction();

        ActionNode actionNode = this.conversationGraphController.getGraphLocation(dialogue);
        if (actionNode == null){
            predictedAction.addProperty(graphLocation, "-1");
            return predictedAction;
        }

        predictedAction.setActionId(actionNode.getStringId());
        predictedAction.setConfidence((float) 1.0);
        predictedAction.addProperty(graphLocation, actionNode.getId().toString());

        return predictedAction;
    }


}
