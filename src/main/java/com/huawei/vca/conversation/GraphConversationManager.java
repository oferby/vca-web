package com.huawei.vca.conversation;

import com.huawei.vca.grpc.NluService;
import com.huawei.vca.intent.Entity;
import com.huawei.vca.intent.NluResponse;
import com.huawei.vca.message.*;
import com.huawei.vca.repository.BotUtterEntity;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.graph.ConversationGraphController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Set;

@Controller
public class GraphConversationManager implements ConversationManager {

    private static final Logger logger = LoggerFactory.getLogger(GraphConversationManager.class);

    @Autowired
    private NluService nluService;

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Autowired
    private ConversationGraphController conversationGraphController;

    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        this.handleUserUtter(dialogue);

        if (this.conversationGraphController.addGraphLocation(dialogue))
            this.addActionToDialogue(dialogue);
        else
            this.addDefaultUtterEvent(dialogue);

        return dialogue;
    }

    @Override
    public Dialogue handleNluOnly(Dialogue dialogue) {
        this.handleUserUtter(dialogue);
        return dialogue;
    }

    private void handleUserUtter(Dialogue dialogue) {

        String text = dialogue.getText();
        UserUtterEvent userUtterEvent = new UserUtterEvent(text);
        dialogue.addToHistory(userUtterEvent);
        NluEvent nluEvent = handleNlu(text);
        userUtterEvent.setNluEvent(nluEvent);
        dialogue.setLastNluEvent(nluEvent);

    }

    private NluEvent handleNlu(String text) {
        NluResponse nluResponse = nluService.getNluResponse(text);
        NluEvent nluEvent = new NluEvent();
        nluEvent.setBestIntent(new Intent(nluResponse.getIntent().getName(), nluResponse.getIntent().getConfidence()));

        for (Entity entity : nluResponse.getEntitiesList()) {
            nluEvent.addSlot(new Slot(entity.getEntity(), entity.getValue(), entity.getConfidence()));
        }

        return nluEvent;
    }

    public void addActionToDialogue(Dialogue dialogue) {

        logger.debug("got new action: " + dialogue.getText() + " on session id: " + dialogue.getSessionId());

        if (dialogue.getProperties() != null) {
            dialogue.getProperties().remove("best_action");
        }

        Optional<BotUtterEntity> actionById = botUtterRepository.findById(dialogue.getText());

        if (!actionById.isPresent()) {
            throw new RuntimeException("invalid action id");
        }

        BotUtterEntity botUtterEntity = actionById.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();
        Set<String> textIterator = botUtterEntity.getTextSet();
        String text;
        if (textIterator.iterator().hasNext()) {
            text = textIterator.iterator().next();

        } else {
            text = "** no messages found for action ** " + botUtterEntity.getId();
        }
        botUtterEvent.setId(botUtterEntity.getId());
        botUtterEvent.setText(text);
        dialogue.addToHistory(botUtterEvent);
        dialogue.setText(text);

    }

    private void addDefaultUtterEvent(Dialogue dialogue) {
        BotUtterEvent botUtterEvent = new BotUtterEvent("I'm sorry but I did not understand what you've said. Let me route your call to human.");
        dialogue.addToHistory(botUtterEvent);
        dialogue.setText(botUtterEvent.getText());
        dialogue.setNeedOperator(true);
    }
}
