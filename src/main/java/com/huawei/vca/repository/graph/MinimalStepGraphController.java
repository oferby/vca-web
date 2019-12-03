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

//    @Value("${skill.confidence.minimal}")
    private float confidence = (float) 0.91;


    @Override
    public PredictedAction getPredictedAction(Map<String, Float> state, Map<String, Float> observations) {

        PredictedAction predictedAction = new PredictedAction();

        RootNode rootNode = conversationGraphRepository.getRootNode();
        List<ObservationNode> observationNodes = rootNode.getObservationNodes();

        if (observationNodes == null) {
            logger.debug("Prediction from Q&A: " + predictedAction);
            return predictedAction;
        }

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

        ObservationNode observationNode = this.checkObservation(observationNodes, searchString);

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

    private ObservationNode checkObservation(List<ObservationNode> observationNodes, String searchString) {
        boolean found = false;

        for (ObservationNode observationNode : observationNodes) {
            if (observationNode.getStringId().equals("query")) {

                List<PropertyNode> properties = observationNode.getProperties();
                for (PropertyNode property : properties) {
                    if (property.getStringId().equals(searchString)){
                        found=true;
                        break;
                    }
                }


            }

            if (found)
                return observationNode;
        }

        return null;
    }

}
