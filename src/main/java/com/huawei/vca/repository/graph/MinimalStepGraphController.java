package com.huawei.vca.repository.graph;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
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

//    @Value("${skill.confidence.minimal}")
    private float confidence = (float) 0.91;


    @Override
    public PredictedAction getPredictedAction(Map<String, Float> state, Map<String, Float> observations) {

        PredictedAction predictedAction = new PredictedAction();


        String query = "query:";
        String searchString = null;
        for (String key : observations.keySet()) {
            if (key.startsWith(query)){
                searchString = key.substring(query.length());
                break;
            }
        }

        if (searchString==null){
            return predictedAction;
        }

        ArrayList<String> propStringIdList = new ArrayList<>();
        propStringIdList.add(searchString);

        ObservationNode observationNode = conversationGraphRepository.findByProperties(propStringIdList, propStringIdList.size());

        if (observationNode == null) {
            logger.debug("Prediction from Q&A: " + predictedAction);
            return predictedAction;
        }

        observationNode = conversationGraphRepository.findObservationNodeById(observationNode.getId());

        if (observationNode.getActionNode() == null)
            return predictedAction;

        ActionNode actionNode = conversationGraphRepository.findActionById(observationNode.getActionNode().getId());

        if (actionNode.getObservationNodes() != null)
            return predictedAction;

        predictedAction.setConfidence(confidence);
        BotUtterEvent botUtterEvent = responseGenerator.generateResponse(actionNode.getStringId(), this.getClass());
        predictedAction.setBotEvent(botUtterEvent);

        logger.debug("Prediction from Q&A: " + predictedAction);
        return predictedAction;

    }

}
