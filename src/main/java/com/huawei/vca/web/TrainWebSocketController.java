package com.huawei.vca.web;

import com.huawei.vca.conversation.ConversationManager;
import com.huawei.vca.conversation.SessionController;
import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.DialogueSummary;
import com.huawei.vca.repository.BotUtterEntity;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.graph.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;

@Controller
public class TrainWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(TrainWebSocketController.class);

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private SessionController sessionController;

    @Autowired
    private ConversationManager conversationManager;

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Autowired
    private ConversationRepositoryController conversationRepositoryController;

    @Autowired
    private ConversationRepository conversationRepository;

    private static String graphLocation = "graph_location";

    @MessageMapping("/train/parseDialogue")
    public void getIntentRequest(Dialogue dialogue, @Header("simpSessionId") String sessionId) {

        logger.debug("got new training input: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());
        dialogue.setTraining(true);
        sessionController.addOrUpdateDialogue(sessionId, dialogue);

        dialogue = conversationManager.handleNluOnly(dialogue);

        if (this.addGraphLocation(dialogue)) {

            this.addAction(dialogue);

        } else {

            this.sendUserUtterToMonitor(dialogue);
            this.sendSummaryResponse(dialogue);

            dialogue.setText(null);
            this.sendDialogueResponse(dialogue);

        }

    }

    private boolean addGraphLocation(Dialogue dialogue) {

        if (dialogue.getProperty(graphLocation) == null) {

            RootNode rootNode = conversationRepository.getRootNode();
            List<ObservationNode> observationNodes = rootNode.getObservationNodes();
            return this.addActionToDialogue(dialogue, observationNodes);

        } else {

            if (dialogue.getProperty(graphLocation).equals("-1")) {
                return false;
            }

            Long graphId = Long.valueOf(dialogue.getProperty(graphLocation));
            ActionNode actionNode = conversationRepository.findActionById(graphId);
            if (actionNode == null || actionNode.getObservationNodes() == null) {
                dialogue.addProperty(graphLocation, "-1");
                dialogue.addProperty("best_action", "no action in graph");
                return false;

            }

            return this.addActionToDialogue(dialogue, actionNode.getObservationNodes());
        }

    }

    private boolean addActionToDialogue(Dialogue dialogue, List<ObservationNode> observationNodes) {

        for (ObservationNode observationNode : observationNodes) {
            if (observationNode.getStringId().equals(dialogue.getLastNluEvent().getBestIntent().getIntent())) {
                ObservationNode node = conversationRepository.findObservationNodeById(observationNode.getId());
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


    @MessageMapping("/train/saveDialogue")
    public void saveDialogue(Dialogue dialogue) {

        logger.debug("saving dialogue to graph");

        conversationRepositoryController.saveDialogueToGraph(dialogue);

        logger.debug("dialogue saved to graph");

    }

    @MessageMapping("/train/addAction")
    public void addAction(Dialogue dialogue) {
        logger.debug("got new training action: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());

        if (dialogue.getProperties()!=null){
            dialogue.getProperties().remove("best_action");
        }

        Optional<BotUtterEntity> actionById = botUtterRepository.findById(dialogue.getText());

        if (!actionById.isPresent()) {
            throw new RuntimeException("invalid action id");
        }

        BotUtterEntity botUtterEntity = actionById.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();
        String text = botUtterEntity.getTextSet().iterator().next();
        botUtterEvent.setText(text);
        botUtterEvent.setId(botUtterEntity.getId());

        dialogue.addToHistory(botUtterEvent);
        dialogue.setText(text);

        sessionController.addOrUpdateDialogue(dialogue.getSessionId(), dialogue);
        this.sendDialogueResponse(dialogue);
        this.sendUserUtterToMonitor(dialogue);
        this.sendSummaryResponse(dialogue);

    }

    public void sendDialogueResponse(Dialogue dialogueResponse) {
        template.convertAndSend("/topic/dialogue/" + dialogueResponse.getSessionId(), dialogueResponse);
    }

    private void sendSummaryResponse(Dialogue dialogueResponse) {

        DialogueSummary dialogueSummary = new DialogueSummary(dialogueResponse.getSessionId(),
                dialogueResponse.getLastNluEvent().getBestIntent().getConfidence(), true);
        template.convertAndSend("/topic/dialogue/monitor/summary", dialogueSummary);
    }

    private void sendUserUtterToMonitor(Dialogue dialogue) {
        template.convertAndSend("/topic/dialogue/monitor/data/" + dialogue.getSessionId(), dialogue);
    }

}
