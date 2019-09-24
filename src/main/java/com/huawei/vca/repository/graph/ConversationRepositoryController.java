package com.huawei.vca.repository.graph;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.Event;
import com.huawei.vca.message.UserUtterEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class ConversationRepositoryController {

    @Autowired
    private ConversationRepository conversationRepository;

    public void saveDialogueToGraph(Dialogue dialogue) {

        List<StateNode> toSave = new ArrayList<>();

        RootNode rootNode = conversationRepository.getRootNode();

        List<ObservationNode> nodes = rootNode.getObservationNodes();
        if (nodes == null) {
            nodes = new ArrayList<>();
            rootNode.setObservationNodes(nodes);

        }

        toSave.add(rootNode);

        ActionNode nextActionNode = null;
        ObservationNode nextObservationNode = null;

        List<Event> history = dialogue.getHistory();

        for (Event event : history) {

            if (event instanceof BotUtterEvent) {

                BotUtterEvent botUtterEvent = (BotUtterEvent) event;

                if (nextObservationNode.getActionNode() == null) {
                    nextActionNode = new ActionNode();
                    nextActionNode.setName(botUtterEvent.getText());
                    nextObservationNode.setActionNode(nextActionNode);
                    nodes = new ArrayList<>();
                    nextActionNode.setObservationNodes(nodes);

                    toSave.add(nextObservationNode);
                    toSave.add(nextActionNode);

                    continue;

                }

                if (!nextActionNode.getName().equals(botUtterEvent.getText())) {
                    throw new RuntimeException("multiple actions to single observation");
                }

                nodes = nextActionNode.getObservationNodes();

            } else if (event instanceof UserUtterEvent) {

                UserUtterEvent userUtterEvent = (UserUtterEvent) event;

                boolean found = false;

                for (ObservationNode node : nodes) {
                    if (node.getName().equals(userUtterEvent.getNluEvent().getBestIntent().getIntent())) {
                        found = true;
                        nextObservationNode = conversationRepository.findObservationNodeById(node.getId());
                        nextActionNode = nextObservationNode.getActionNode();
                        if (nextActionNode != null) {
                            nextActionNode = conversationRepository.findActionById(nextActionNode.getId());
                        }

                        break;

                    }

                }

                if (!found) {
                    ObservationNode observationNode = new ObservationNode(userUtterEvent.getNluEvent().getBestIntent().getIntent());
                    nodes.add(observationNode);
                    nextObservationNode = observationNode;
                    toSave.add(observationNode);
                    if (nextActionNode != null) {
                        toSave.add(nextActionNode);
                    }


                }

            }

        }


        Set<StateNode>nodeSet=new HashSet<>(toSave);
        conversationRepository.saveAll(nodeSet);

    }

}
