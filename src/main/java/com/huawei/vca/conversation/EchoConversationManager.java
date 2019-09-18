package com.huawei.vca.conversation;


import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.UserUtter;
import org.springframework.stereotype.Controller;

@Controller
public class EchoConversationManager implements ConversationManager{


    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        String text = dialogue.getText();
        dialogue.addToHistory(new UserUtter(text));

        BotUtterEvent botUtterEvent = new BotUtterEvent();
        String response = "echo: " + text;
        botUtterEvent.setText(response);
        dialogue.addToHistory(botUtterEvent);

        dialogue.setText(response);

        return dialogue;
    }
}
