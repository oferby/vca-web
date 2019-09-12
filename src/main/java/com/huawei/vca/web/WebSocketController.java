package com.huawei.vca.web;

import com.huawei.vca.message.Dialogue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
    private SimpMessagingTemplate template;

    @MessageMapping("/parseDialogue")
    public void getIntentRequest(Dialogue dialogue) {

        logger.debug("got new user input: " + dialogue.getText());

        this.sendResponse(dialogue);

    }

    private void sendResponse(Dialogue dialogueResponse) {
        template.convertAndSend("/topic/dialogue", dialogueResponse);
    }

}
