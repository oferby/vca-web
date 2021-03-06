package com.huawei.vca.conversation;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.nlg.ResponseGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class DialogueActionControllerImpl implements DialogueActionController {

    @Autowired
    private ResponseGenerator responseGenerator;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void addActionToDialogue(Dialogue dialogue, Class skill) {

        logger.debug("set new action: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());

        BotUtterEvent botUtterEvent = this.responseGenerator.generateResponse(dialogue.getText(), skill);
        dialogue.addToHistory(botUtterEvent);
        dialogue.setText(botUtterEvent.getText());

    }




}
