package com.huawei.vca.repository.graph;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Controller
public class MinimalStepGraphController implements SkillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConversationGraphRepository conversationGraphRepository;

    @Autowired
    private ResponseGenerator responseGenerator;


    @Override
    public PredictedAction getPredictedAction(Dialogue dialogue) {

        PredictedAction predictedAction = new PredictedAction();

        RootNode rootNode = conversationGraphRepository.getRootNode();
        List<ObservationNode> observationNodes = rootNode.getObservationNodes();

        if (observationNodes == null)
            return predictedAction;

        ObservationNode observationNode = this.checkObservation(dialogue.getLastNluEvent(), observationNodes);

        if (observationNode == null) {
            return predictedAction;
        }

        observationNode = conversationGraphRepository.findObservationNodeById(observationNode.getId());

        if (observationNode.getActionNode() == null)
            return predictedAction;

        ActionNode actionNode = conversationGraphRepository.findActionById(observationNode.getActionNode().getId());

        if (actionNode.getObservationNodes() != null)
            return predictedAction;

        predictedAction.setConfidence((float) 0.95);
        BotUtterEvent botUtterEvent = responseGenerator.generateResponse(actionNode.getStringId());
        predictedAction.setBotEvent(botUtterEvent);

        logger.debug("got prediction: " + predictedAction);

        return predictedAction;
    }

    private ObservationNode checkObservation(NluEvent nluEvent, List<ObservationNode> observationNodes) {
        boolean found = false;

        for (ObservationNode observationNode : observationNodes) {
            if (observationNode.getStringId().equals(nluEvent.getBestIntent().getIntent())) {

                found = true;

                Map<String, String> properties = observationNode.getProperties();
                Set<Slot> slots = nluEvent.getSlots();

                if (slots != null || properties.size() != 0) {

                    if (slots != null && properties.keySet().size() == slots.size()) {
                        //                            same number of slots

                        for (Slot slot : slots) {
                            if (!properties.containsKey(slot.getKey()) || !properties.get(slot.getKey()).equals(slot.getValue())) {
                                found = false;
                                break;
                            }
                        }

                    } else {
                        found = false;
                    }
                }

            }

            if (found)
                return observationNode;
        }

        return null;
    }
}
