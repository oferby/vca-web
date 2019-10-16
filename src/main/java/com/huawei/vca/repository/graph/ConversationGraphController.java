package com.huawei.vca.repository.graph;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
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

    private static String graphLocation = "graph_location";
    private static String observationLocation = "observation_location";

    @Override
    public PredictedAction getPredictedAction(Dialogue dialogue) {

        PredictedAction predictedAction = new PredictedAction();

        ActionNode actionNode = this.getGraphLocation(dialogue);
        if (actionNode == null) {
            predictedAction.addProperty(graphLocation, "-1");
            return predictedAction;
        }

        BotUtterEvent botUtterEvent = responseGenerator.generateResponse(actionNode.getStringId());
        predictedAction.setBotEvent(botUtterEvent);
        predictedAction.setConfidence((float) 0.9);
        predictedAction.addProperty(graphLocation, actionNode.getId().toString());

        logger.debug("got prediction: " + predictedAction);

        return predictedAction;
    }

    public void saveDialogueToGraph(Dialogue dialogue) {

        List<StateNode> toSave = new ArrayList<>();

        RootNode rootNode = conversationGraphRepository.getRootNode();

        List<ObservationNode> nodes = rootNode.getObservationNodes();
        if (nodes == null) {
            nodes = new ArrayList<>();
            rootNode.setObservationNodes(nodes);

        }

        rootNode.increaseVisited();

        ActionNode nextActionNode = null;
        ObservationNode nextObservationNode = null;

        List<Event> history = dialogue.getHistory();

        for (Event event : history) {

            if (event instanceof BotDefaultUtterEvent) {
                continue;
            }

            if (event instanceof BotUtterEvent) {

                BotUtterEvent botUtterEvent = (BotUtterEvent) event;

                assert nextObservationNode != null;
                if (nextObservationNode.getActionNode() == null) {
                    nextActionNode = new ActionNode();
                    nextActionNode.setStringId(botUtterEvent.getId());
                    nextObservationNode.setActionNode(nextActionNode);
                    nodes = new ArrayList<>();
                    nextActionNode.setObservationNodes(nodes);

                    toSave.add(nextObservationNode);
                    toSave.add(nextActionNode);

                    continue;

                }

                assert nextActionNode != null;
                if (!nextActionNode.getStringId().equals(botUtterEvent.getId())) {
                    throw new RuntimeException("multiple actions to single observation");
                }

                nodes = nextActionNode.getObservationNodes();

            } else if (event instanceof UserUtterEvent) {

                UserUtterEvent userUtterEvent = (UserUtterEvent) event;

                boolean found = false;

                if (nodes == null) {
                    nodes = new ArrayList<>();
                    nextActionNode.setObservationNodes(nodes);
                } else {

                    for (ObservationNode node : nodes) {
                        if (node.getStringId().equals(userUtterEvent.getNluEvent().getBestIntent().getIntent())) {

                            found = true;

                            Map<String, String> properties = node.getProperties();
                            Set<Slot> slots = userUtterEvent.getNluEvent().getSlots();

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

                            if (found) {

                                nextObservationNode = conversationGraphRepository.findObservationNodeById(node.getId());
                                nextObservationNode.increaseVisited();
                                toSave.add(nextObservationNode);
                                nextActionNode = nextObservationNode.getActionNode();
                                if (nextActionNode != null) {
                                    nextActionNode = conversationGraphRepository.findActionById(nextActionNode.getId());
                                }

                                break;

                            }
                        }

                    }
                }

                if (!found) {
                    ObservationNode observationNode = new ObservationNode();

                    observationNode.setStringId(userUtterEvent.getNluEvent().getBestIntent().getIntent());

                    if (userUtterEvent.getNluEvent().getSlots() != null) {
                        for (Slot slot : userUtterEvent.getNluEvent().getSlots()) {
                            observationNode.addProperty(slot.getKey(), slot.getValue());
                        }

                    }

                    nodes.add(observationNode);
                    nextObservationNode = observationNode;
                    nextObservationNode.increaseVisited();
                    toSave.add(observationNode);
                    if (nextActionNode != null) {
                        toSave.add(nextActionNode);
                    }


                }

            }

        }

        Set<ObservationNode> observationNodes = new HashSet<>();
        Set<ActionNode> actionNodes = new HashSet<>();
        Set<StateNode> nodeSet = new HashSet<>();

        nodeSet.add(rootNode);

        for (StateNode stateNode : toSave) {
            if (stateNode instanceof ActionNode) {
                actionNodes.add((ActionNode) stateNode);
            } else {
                observationNodes.add((ObservationNode) stateNode);
            }
        }

        nodeSet.addAll(actionNodes);
        nodeSet.addAll(observationNodes);

        conversationGraphRepository.saveAll(nodeSet);

    }


    private ActionNode getGraphLocation(Dialogue dialogue) {

        if (dialogue.getProperty(graphLocation) == null) {

            RootNode rootNode = conversationGraphRepository.getRootNode();
            List<ObservationNode> observationNodes = rootNode.getObservationNodes();
            return this.findActionId(dialogue.getLastNluEvent(), observationNodes);

        }

        if (dialogue.getProperty(graphLocation).equals("-1")) {
            return null;
        }

        Long graphId = Long.valueOf(dialogue.getProperty(graphLocation));
        ActionNode actionNode = conversationGraphRepository.findActionById(graphId);
        if (actionNode == null || actionNode.getObservationNodes() == null) {
            return null;

        }

        return findActionId(dialogue.getLastNluEvent(), actionNode.getObservationNodes());

    }

    private ActionNode findActionId(NluEvent nluEvent, List<ObservationNode> observationNodes) {

        if (observationNodes == null)
            return null;

        boolean found;

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

                if (found) {

                    ObservationNode node = conversationGraphRepository.findObservationNodeById(observationNode.getId());
                    if (node.getActionNode() != null) {
                        return node.getActionNode();
                    }

                }
            }

        }

        return null;

    }

}