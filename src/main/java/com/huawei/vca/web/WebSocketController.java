package com.huawei.vca.web;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.repository.SessionController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

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

    @MessageMapping("/parseDialogue")
    public void getIntentRequest(Dialogue dialogue, @Header("simpSessionId") String sessionId) {

        logger.debug("got new user input: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());

        sessionController.addOrUpdateDialogue(sessionId, dialogue);
        this.sendResponse(dialogue);

    }

    private void sendResponse(Dialogue dialogueResponse) {
        template.convertAndSend("/topic/dialogue/"+dialogueResponse.getSessionId(), dialogueResponse);
    }

}
