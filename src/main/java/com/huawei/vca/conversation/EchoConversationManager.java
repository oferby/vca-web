package com.huawei.vca.conversation;


import com.huawei.vca.grpc.NluService;
import com.huawei.vca.intent.Entity;
import com.huawei.vca.intent.NluResponse;
import com.huawei.vca.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

//@Controller
public class EchoConversationManager implements ConversationManager {

    @Autowired
    private NluService nluService;

    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        this.handleUserUtter(dialogue);

        BotUtterEvent botUtterEvent = new BotUtterEvent();
        String response = "echo: " + dialogue.getText();
        botUtterEvent.setText(response);
        dialogue.addToHistory(botUtterEvent);

        dialogue.setText(response);

        return dialogue;
    }

    @Override
    public Dialogue handleNluOnly(Dialogue dialogue) {

        this.handleUserUtter(dialogue);
        return dialogue;

    }

    @Override
    public void addActionToDialogue(Dialogue dialogue) {

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
            nluEvent.addSlot(new Slot(entity.getEntity(), entity.getValue(),entity.getConfidence()));
        }

        return nluEvent;
    }
}
