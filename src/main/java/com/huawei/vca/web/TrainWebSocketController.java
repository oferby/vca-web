package com.huawei.vca.web;

import com.huawei.vca.conversation.ConversationManager;
import com.huawei.vca.conversation.GraphConversationManager;
import com.huawei.vca.conversation.SessionController;
import com.huawei.vca.message.*;
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
import java.util.Set;

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
    private ConversationGraphController conversationGraphController;

    @MessageMapping("/train/parseDialogue")
    public void getIntentRequest(Dialogue dialogue, @Header("simpSessionId") String sessionId) {

        logger.debug("got new training input: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());
        dialogue.setTraining(true);

        conversationManager.handleDialogue(dialogue);
        sessionController.addOrUpdateDialogue(sessionId, dialogue);

        List<Event> history = dialogue.getHistory();
        if (history.get(history.size() - 1) instanceof BotUtterEvent) {

            this.sendResponseToAll(dialogue);

        } else {

            this.sendUserUtterToMonitor(dialogue);
            this.sendSummaryResponse(dialogue);

            dialogue.setText(null);
            this.sendDialogueResponse(dialogue);

        }

    }

    @MessageMapping("/train/saveDialogue")
    public void saveDialogue(Dialogue dialogue) {

        logger.debug("saving dialogue to graph");

        conversationGraphController.saveDialogueToGraph(dialogue);

        logger.debug("dialogue saved to graph");

    }

    @MessageMapping("/train/addAction")
    public void addAction(Dialogue dialogue) {

        logger.debug("got new action to add: " + dialogue.getText());
        conversationManager.addActionToDialogue(dialogue);
        sendResponseToAll(dialogue);

    }

    private void sendResponseToAll(Dialogue dialogue){
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
