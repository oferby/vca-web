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

    @MessageMapping("/train/parseDialogue")
    public void getIntentRequest(Dialogue dialogue, @Header("simpSessionId") String sessionId) {

        logger.debug("got new training input: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());
        dialogue.setTraining(true);
        sessionController.addOrUpdateDialogue(sessionId, dialogue);

        dialogue = conversationManager.handleNluOnly(dialogue);
        this.addGraphLocation(dialogue);

        this.sendUserUtterToMonitor(dialogue);
        this.sendSummaryResponse(dialogue);

        dialogue.setText(null);
        this.sendDialogueResponse(dialogue);

    }

    private void addGraphLocation(Dialogue dialogue) {

        String graphLocation = "graph_location";
        Long graphId = (Long) dialogue.getProperty(graphLocation);

        if ( graphId == null) {

            RootNode rootNode = conversationRepository.getRootNode();
            dialogue.addProperty(graphLocation, rootNode.getId());
            List<ObservationNode> observationNodes = rootNode.getObservationNodes();
            this.addActionToDialogue(dialogue, observationNodes);

        } else {



            ActionNode actionNode = conversationRepository.findActionById(graphId);
            this.addActionToDialogue(dialogue, actionNode.getObservationNodes());
        }

    }

    private void addActionToDialogue(Dialogue dialogue, List<ObservationNode> observationNodes) {

        for (ObservationNode observationNode : observationNodes) {
            if (observationNode.getStringId().equals(dialogue.getLastNluEvent().getBestIntent().getIntent())){
                dialogue.addProperty("best_action", observationNode.getActionNode());
            }
        }

    }


    @MessageMapping("/train/saveDialogue")
    public void saveDialogue(Dialogue dialogue) {

        logger.debug("saving dialogue to graph");

        conversationRepositoryController.saveDialogueToGraph(dialogue);

    }

    @MessageMapping("/train/addAction")
    public void addAction(Dialogue dialogue) {
        logger.debug("got new training action: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());

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
