package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.Act;
import com.huawei.vca.message.Dialogue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
public class ConfirmDenySkill implements PreprocessorSkill{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void process(Dialogue dialogue) {

        Act act = dialogue.getLastNluEvent().getBestIntent().getAct();

        if (act == Act.AFFIRM){
            this.extractSlot(dialogue, true);
        } else if (act == Act.NEGATE) {
            this.extractSlot(dialogue, false);
        }

    }

    private void extractSlot(Dialogue dialogue, boolean isConfirm) {





    }
}
