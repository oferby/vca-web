package com.huawei.vca.web;

import com.huawei.vca.conversation.ConversationManager;
import com.huawei.vca.message.Confidence;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.conversation.SessionController;
import com.huawei.vca.message.DialogueSummary;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

/*
 * This class is a starting point for user input that comes from web socket.
 * Different channels can exist to send user inputs to the dialogue
 * engine.
 *
 * */
@Controller
public class WebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketController.class);

    @Autowired
    private SessionController sessionController;

    @Autowired
    private SimpMessagingTemplate template;

    @Autowired
    private ConversationManager conversationManager;

    @MessageMapping("/parseDialogue")
    public void getIntentRequest(Dialogue dialogue, @Header("simpSessionId") String sessionId) {

        logger.debug("got new user input: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());

        dialogue = conversationManager.handleDialogue(dialogue);
        sessionController.addOrUpdateDialogue(sessionId, dialogue);

        this.sendResponseToAll(dialogue);

    }

    public void sendResponseToAll(Dialogue dialogue) {

        this.sendDialogueResponse(dialogue);
        this.sendUserUtterToMonitor(dialogue);
        this.sendSummaryResponse(dialogue);

    }

    public void sendDialogueResponse(Dialogue dialogue) {
        template.convertAndSend("/topic/dialogue/" + dialogue.getSessionId(), dialogue);
    }

    public void sendSummaryResponse(Dialogue dialogue) {

        DialogueSummary dialogueSummary = new DialogueSummary(dialogue.getSessionId(),
                dialogue.getLastNluEvent().getBestIntent().getConfidence());

        if (dialogue.isNeedOperator()){
            dialogueSummary.setConfidenceString(Confidence.NEED_OPERATOR);
        } else if (dialogue.isTraining()){
            dialogueSummary.setConfidenceString(Confidence.TRAIN);
        }

        template.convertAndSend("/topic/dialogue/monitor/summary", dialogueSummary);
    }

    public void sendUserUtterToMonitor(Dialogue dialogue) {
        template.convertAndSend("/topic/dialogue/monitor/data/" + dialogue.getSessionId(), dialogue);
    }


}
