package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.Dialogue;
import org.springframework.stereotype.Controller;

@Controller
public class SentimentAnalysisSkill implements PreprocessorSkill{
    @Override
    public void process(Dialogue dialogue) {

//        dialogue.addObservation("sentiment:positive", (float) 0.1);
//        dialogue.addObservation("sentiment:negative", (float) 0.1);
//        dialogue.addObservation("sentiment:neutral", (float) 0.8);

    }
}
