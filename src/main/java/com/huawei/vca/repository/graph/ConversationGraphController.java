package com.huawei.vca.repository.graph;

import com.huawei.vca.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ConversationGraphController {

    @Autowired
    private ConversationGraphRepository conversationGraphRepository;

    private static String graphLocation = "graph_location";

    public void saveDialogueToGraph(Dialogue dialogue) {

        List<StateNode> toSave = new ArrayList<>();

        RootNode rootNode = conversationGraphRepository.getRootNode();

        List<ObservationNode> nodes = rootNode.getObservationNodes();
        if (nodes == null) {
            nodes = new ArrayList<>();
            rootNode.setObservationNodes(nodes);

        }

        rootNode.increaseVisited();
        toSave.add(rootNode);

        ActionNode nextActionNode = null;
        ObservationNode nextObservationNode = null;

        List<Event> history = dialogue.getHistory();

        for (Event event : history) {

            if (event instanceof BotUtterEvent) {

                BotUtterEvent botUtterEvent = (BotUtterEvent) event;

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


        SortedSet<StateNode> nodeSet = new TreeSet<>(toSave);
        conversationGraphRepository.saveAll(nodeSet);

    }

    public boolean addGraphLocation(Dialogue dialogue) {

        if (dialogue.getProperty(graphLocation) == null) {

            RootNode rootNode = conversationGraphRepository.getRootNode();
            List<ObservationNode> observationNodes = rootNode.getObservationNodes();
            return this.addActionToDialogue(dialogue, observationNodes);

        } else {

            if (dialogue.getProperty(graphLocation).equals("-1")) {
                return false;
            }

            Long graphId = Long.valueOf(dialogue.getProperty(graphLocation));
            ActionNode actionNode = conversationGraphRepository.findActionById(graphId);
            if (actionNode == null || actionNode.getObservationNodes() == null) {
                dialogue.addProperty(graphLocation, "-1");
                dialogue.addProperty("best_action", "no action in graph");
                return false;

            }

            return this.addActionToDialogue(dialogue, actionNode.getObservationNodes());
        }

    }

    private boolean addActionToDialogue(Dialogue dialogue, List<ObservationNode> observationNodes) {

        if (observationNodes == null)
            return false;

        for (ObservationNode observationNode : observationNodes) {
            if (observationNode.getStringId().equals(dialogue.getLastNluEvent().getBestIntent().getIntent())) {
                ObservationNode node = conversationGraphRepository.findObservationNodeById(observationNode.getId());
                if (node.getActionNode() != null) {
                    if (dialogue.getProperties() != null)
                        dialogue.getProperties().remove("best_action");

                    dialogue.setText(node.getActionNode().getStringId());
                    dialogue.addProperty(graphLocation, node.getActionNode().getId().toString());
                    return true;
                }

            }
        }

        dialogue.addProperty(graphLocation, "-1");
        dialogue.addProperty("best_action", "no action in graph");
        return false;
    }


}
