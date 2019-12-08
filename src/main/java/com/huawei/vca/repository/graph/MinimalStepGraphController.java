package com.huawei.vca.repository.graph;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Map;

@Controller
public class MinimalStepGraphController implements SkillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConversationGraphRepository conversationGraphRepository;

    @Autowired
    private ResponseGenerator responseGenerator;

//    @Value("${skill.confidence.minimal}")
    private float confidence = (float) 0.91;


    @Override
    public PredictedAction getPredictedAction(Map<String, Float> state, Map<String, Float> observations) {

        PredictedAction predictedAction = new PredictedAction();

        StateNode stateNode = conversationGraphRepository.findStateByProperties(observations.keySet());
        if (stateNode == null) {
            logger.debug("Prediction from Q&A: " + predictedAction);
            return predictedAction;
        }

        ActionNode actionNode = stateNode.getActionNode();
        if (actionNode==null){
            logger.debug("There is no action attached to state.\nPrediction from Q&A: " + predictedAction);
            return predictedAction;
        }

        predictedAction.setConfidence(confidence);
        BotUtterEvent botUtterEvent = responseGenerator.generateResponse(actionNode.getStringId(), this.getClass());
        predictedAction.setBotEvent(botUtterEvent);

        logger.debug("Prediction from Q&A: " + predictedAction);
        return predictedAction;

    }

}
