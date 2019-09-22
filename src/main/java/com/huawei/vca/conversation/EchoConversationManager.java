package com.huawei.vca.conversation;


import com.huawei.vca.grpc.NluService;
import com.huawei.vca.intent.Entity;
import com.huawei.vca.intent.NluResponse;
import com.huawei.vca.message.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class EchoConversationManager implements ConversationManager {

    @Autowired
    private NluService nluService;

    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        String text = dialogue.getText();
        UserUtter userUtter = new UserUtter(text);
        dialogue.addToHistory(userUtter);
        NluEvent nluEvent = handleNlu(text);
        userUtter.setNluEvent(nluEvent);
        dialogue.setLastNluEvent(nluEvent);

        BotUtterEvent botUtterEvent = new BotUtterEvent();
        String response = "echo: " + text;
        botUtterEvent.setText(response);
        dialogue.addToHistory(botUtterEvent);

        dialogue.setText(response);

        return dialogue;
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
