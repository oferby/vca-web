package com.huawei.vca.repository.graph;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ConversationGraphController implements SkillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ConversationGraphRepository conversationGraphRepository;

    @Autowired
    private ResponseGenerator responseGenerator;

    //    @Value("${skill.confidence.graph}")
    private float confidence = (float) 0.9;

    private static String graphLocation = "graph_location";
    private static String observationLocation = "observation_location";

    @Override
    public PredictedAction getPredictedAction(Map<String, Float> state, Map<String, Float> observations) {

        PredictedAction predictedAction = new PredictedAction();


        ObservationNode observationNode = conversationGraphRepository.findObservationByProperties(observations.keySet());

        if (observationNode == null) {
            logger.debug("Prediction from history: " + predictedAction);
            return predictedAction;
        }

        StateNode stateNode;

        if (state.keySet().isEmpty()){
           stateNode = conversationGraphRepository.findStateWithoutPropertiesAndObservation(observationNode.getId());
        } else {
            stateNode = conversationGraphRepository.findStateByPropertiesAndObservation(state.keySet(), observationNode.getId());
        }

        if (stateNode==null || stateNode.getActionNode() == null){
            logger.debug("Prediction from history: " + predictedAction);
            return predictedAction;
        }

        ActionNode actionNode = stateNode.getActionNode();
        predictedAction.setConfidence(confidence);
        BotUtterEvent botUtterEvent = responseGenerator.generateResponse(actionNode.getStringId(), actionNode.getOptionNodeList(), this.getClass());
        predictedAction.setBotEvent(botUtterEvent);

        logger.debug("Prediction from history: " + predictedAction);
        return predictedAction;
    }

    public void saveDialogueToGraph(Dialogue dialogue) {

        List<GenericNode> toSave = new ArrayList<>();

        StateNode stateNode = null;
        ObservationNode observationNode;
        ActionNode actionNode;

        for (Event event : dialogue.getHistory()) {

            if (event instanceof UserUtterEvent) {

                UserUtterEvent userUtterEvent = (UserUtterEvent) event;

                observationNode = getOrSaveObservationNode(userUtterEvent);

                stateNode = this.getOrSaveStateNode(userUtterEvent, observationNode);



            } else if (event instanceof BotUtterEvent) {

                BotUtterEvent botUtterEvent = (BotUtterEvent) event;

                actionNode = this.getOrSaveActionNode(botUtterEvent);

                assert stateNode != null;
                stateNode.setActionNode(actionNode);
                toSave.add(stateNode);

            }

        }

        conversationGraphRepository.saveAll(toSave);

    }

    private StateNode getOrSaveStateNode(UserUtterEvent userUtterEvent, ObservationNode observationNode){

        Map<String, Float> state = userUtterEvent.getState();

        List<PropertyNode> propertyNodeList = this.getOrSaveProperties(state);

        List<String>propertyIdList = new ArrayList<>();
        for (PropertyNode propertyNode : propertyNodeList) {
            propertyIdList.add(propertyNode.getStringId());
        }

        StateNode stateNode = conversationGraphRepository.findStateByPropertiesAndObservation(propertyIdList, observationNode.getId());

        if (stateNode == null) {

            stateNode = new StateNode();
            stateNode.setStateProperties(propertyNodeList);
            stateNode.setObservationNode(observationNode);
            stateNode = conversationGraphRepository.save(stateNode);

        }

        return stateNode;

    }


    private ObservationNode getOrSaveObservationNode(UserUtterEvent userUtterEvent){

        Map<String, Float> observations = userUtterEvent.getObservations();
        List<PropertyNode> propertyNodes = this.getOrSaveProperties(observations);

        List<String>propertyIdList = new ArrayList<>();
        for (PropertyNode propertyNode : propertyNodes) {
            propertyIdList.add(propertyNode.getStringId());
        }

        ObservationNode observationNode = conversationGraphRepository.findObservationByProperties(propertyIdList);

        if (observationNode==null){
            observationNode = new ObservationNode();
            observationNode.setObservationProperties(propertyNodes);
            observationNode = conversationGraphRepository.save(observationNode);
        }

        return observationNode;

    }

    private ActionNode getOrSaveActionNode(BotUtterEvent botUtterEvent) {

        List<String>optionList = new ArrayList<>();

        if (botUtterEvent.getOptions() != null) {
            for (Option option : botUtterEvent.getOptions()) {
                optionList.add(option.getId());
            }

        }

        String actionId = botUtterEvent.getId();
        ActionNode action = conversationGraphRepository.findActionByIdAndOptions(actionId, optionList);

        if (action!=null)
            return action;

        List<GenericNode>toSave = new ArrayList<>();

        action = new ActionNode(actionId);
        toSave.add(action);

        for (String option : optionList) {
            OptionNode optionNode = new OptionNode(option);
            toSave.add(optionNode);
            action.addOption(optionNode);
        }

        Iterable<GenericNode> genericNodes = conversationGraphRepository.saveAll(toSave);

        for (GenericNode genericNode : genericNodes) {
            if (genericNode instanceof ActionNode)
                return (ActionNode) genericNode;
        }

        return null;

    }

    private List<PropertyNode> getOrSaveProperties(Map<String, Float> properties) {

        List<PropertyNode> propertyNodes = new ArrayList<>();
        List<PropertyNode> toSave = new ArrayList<>();

        Set<String> keySet = properties.keySet();

        for (String key : keySet) {
            PropertyNode propertyNode = conversationGraphRepository.findByStringId(key);
            if (propertyNode == null) {
                propertyNode = new PropertyNode(key);
                toSave.add(propertyNode);
            } else {
                propertyNodes.add(propertyNode);
            }
        }

        if (!toSave.isEmpty()) {

            Iterable<PropertyNode> saved = conversationGraphRepository.saveAll(toSave);
            for (PropertyNode propertyNode : saved) {
                propertyNodes.add(propertyNode);
            }

        }

        return propertyNodes;
    }

    public void saveDialogueToGraphOld(Dialogue dialogue) {

        Map<String, Float> observations = dialogue.getObservations();
        Map<String, Float> state = dialogue.getState();

        Set<String> keys = new HashSet<>(observations.keySet());
        keys.addAll(state.keySet());

        List<PropertyNode> propertyNodesToSave = new ArrayList<>();
        List<PropertyNode> propertyNodes = new ArrayList<>();

        String observationName = null;
        for (String key : keys) {

            if (key.startsWith("userAct")) {
                observationName = key;
                continue;
            }

            if (key.startsWith("botAct"))
                continue;

            PropertyNode propertyNode = conversationGraphRepository.findByStringId(key);

            if (propertyNode == null) {
                propertyNode = new PropertyNode();
                propertyNode.setStringId(key);
                propertyNode.setPropertyType(PropertyType.STRING);
                propertyNodesToSave.add(propertyNode);
            } else {
                propertyNodes.add(propertyNode);
            }
        }

        if (!propertyNodesToSave.isEmpty()) {
            Iterable<PropertyNode> saved = conversationGraphRepository.saveAll(propertyNodesToSave);
            for (PropertyNode node : saved) {
                propertyNodes.add(node);
            }
        }

        RootNode rootNode = conversationGraphRepository.getRootNode();

//        ObservationNode observationNode = new ObservationNode();
//        observationNode.setStringId(observationName);
//        observationNode.setObservationProperties(propertyNodes);
//        rootNode.addObservation(observationNode);
//
//        List<GenericNode> nodesToSave = new ArrayList<>(propertyNodes);
//        nodesToSave.add(rootNode);
//        nodesToSave.add(observationNode);
//
//        List<Event> history = dialogue.getHistory();
//        Event event = history.get(history.size() - 1);
//        if (!(event instanceof BotUtterEvent))
//            throw new RuntimeException("wrong bot event");
//
//        BotUtterEvent botUtterEvent = (BotUtterEvent) event;
//
//        ActionNode actionNode = new ActionNode();
//        actionNode.setStringId(botUtterEvent.getId());
//        nodesToSave.add(actionNode);
//        observationNode.setActionNode(actionNode);
//
//        if (botUtterEvent.getOptions() != null) {
//            for (Option option : botUtterEvent.getOptions()) {
//                OptionNode optionNode = new OptionNode(option.getId());
//                actionNode.addOption(optionNode);
//                nodesToSave.add(optionNode);
//            }
//
//        }
//
//        conversationGraphRepository.saveAll(nodesToSave);
//
    }


}