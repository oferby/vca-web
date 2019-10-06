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
public class GraphConversationStateTracker implements ConversationStateTracker {

    private static final Logger logger = LoggerFactory.getLogger(GraphConversationStateTracker.class);

    @Autowired
    private NluService nluService;

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Autowired
    private ConversationGraphController conversationGraphController;

    private static String graphLocation = "graph_location";
    private static String observationLocation = "observation_location";

    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        String currentGraphLocation = null;
        if (dialogue.getProperties() != null && dialogue.getProperties().get(graphLocation) != null){
            currentGraphLocation = dialogue.getProperties().get(graphLocation);
        }

        this.handleUserUtter(dialogue);

        if (this.conversationGraphController.addGraphLocation(dialogue))
            this.addActionToDialogue(dialogue);
        else {

            String statelessAction = this.conversationGraphController.getStatelessAction(dialogue);
            if (statelessAction != null) {
                dialogue.setText(statelessAction);
                this.addActionToDialogue(dialogue);
                if (currentGraphLocation!=null)
                    dialogue.addProperty(graphLocation, currentGraphLocation);

                return dialogue;
            }

            if (!dialogue.isTraining())
                this.addDefaultUtterEvent(dialogue);

        }

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

        if (dialogue.getProperties() != null && dialogue.getProperties().get(observationLocation) != null) {
            Event event = dialogue.getHistory().get(dialogue.getHistory().size() - 1);
            if (event instanceof UserUtterEvent) {
                event.setLocation(dialogue.getProperties().get(observationLocation));
                dialogue.getProperties().remove(observationLocation);
            }
        }


        Optional<BotUtterEntity> actionById = botUtterRepository.findById(dialogue.getText());

        if (!actionById.isPresent()) {
            throw new RuntimeException("invalid action id");
        }

        BotUtterEntity botUtterEntity = actionById.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();
        botUtterEvent.setLocation(dialogue.getProperties().get(graphLocation));

//        TODO
//        change to random
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
        BotDefaultUtterEvent botUtterEvent = new BotDefaultUtterEvent("I'm sorry but I did not understand what you've said. Let me route your call to human.");
        dialogue.addToHistory(botUtterEvent);
        dialogue.setText(botUtterEvent.getText());
        dialogue.setNeedOperator(true);
    }


}
