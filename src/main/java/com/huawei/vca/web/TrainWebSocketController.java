package com.huawei.vca.web;

import com.huawei.vca.conversation.ConversationStateTracker;
import com.huawei.vca.conversation.DialogueController;
import com.huawei.vca.conversation.SessionController;
import com.huawei.vca.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.concurrent.ExecutorService;

@Controller
public class TrainWebSocketController {

    private static final Logger logger = LoggerFactory.getLogger(TrainWebSocketController.class);

    @Autowired
    private SessionController sessionController;

    @Autowired
    private ConversationStateTracker conversationStateTracker;

    @Autowired
    private WebSocketController webSocketController;

    @Autowired
    private DialogueWebController dialogueWebController;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private DialogueController dialogueController;

    @MessageMapping("/train/parseDialogue")
    public void getIntentRequest(Dialogue dialogue, @Header("simpSessionId") String sessionId) {

        logger.debug("got new training input: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());

        executorService.execute(()-> {

            dialogue.setTraining(true);

            conversationStateTracker.handleDialogue(dialogue);
            sessionController.addOrUpdateDialogue(sessionId, dialogue);

            List<Event> history = dialogue.getHistory();
            if (history.get(history.size() - 1) instanceof BotUtterEvent) {

                this.webSocketController.sendResponseToAll(dialogue);

            } else {

                this.webSocketController.sendUserUtterToMonitor(dialogue);
                this.webSocketController.sendSummaryResponse(dialogue);

                dialogue.setText(null);
                this.webSocketController.sendDialogueResponse(dialogue);

            }


        });

    }

    @MessageMapping("/train/saveDialogue")
    public void saveDialogue(Dialogue dialogue) {

        dialogueWebController.saveDialogue(dialogue);

    }

    @MessageMapping("/train/addAction")
    public void addAction(Dialogue dialogue) {

        logger.debug("got new action to add: " + dialogue.getText());

        executorService.execute(() -> {
            dialogueController.addActionToDialogue(dialogue);
            sessionController.addOrUpdateDialogue(dialogue.getSessionId(), dialogue);
            this.webSocketController.sendResponseToAll(dialogue);
        });

    }

}
