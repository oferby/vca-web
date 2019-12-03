package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.BotEvent;
import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Dialogue;
import org.springframework.stereotype.Controller;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class TimePostProcessorSkill implements PostProcessorSkill{


    @Override
    public void process(Dialogue dialogue, BotEvent botEvent) {

        checkInternalFunctions(botEvent);
        dialogue.setText(((BotUtterEvent) botEvent).getText());
        dialogue.addToHistory(botEvent);

    }

    private void checkInternalFunctions(BotEvent botEvent){

        if (botEvent instanceof BotUtterEvent) {

            BotUtterEvent event = (BotUtterEvent)botEvent;
            String text = event.getText();

            if (text.contains("${")) {
                String func = text.substring(text.indexOf("${")+2,text.indexOf("}"));

                if (func.equals("currentTime")) {
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                    Date date = new Date();
                    String currentTime =  formatter.format(date);
                    event.setText(text.replace("${currentTime}", currentTime));
                }

            }

        }

    }
}
