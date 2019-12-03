package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.Act;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.Slot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class UserInformSkill implements PreprocessorSkill{



    @Autowired
    private ObservationCreatorController observationCreatorController;


    @Override
    public void process(Dialogue dialogue) {

        Act act = dialogue.getLastNluEvent().getBestIntent().getAct();

        if (act!=Act.INFORM)
            return;
        Set<Slot> slots = dialogue.getLastNluEvent().getSlots();
        observationCreatorController.createObservations(dialogue.getObservations(), slots, dialogue.getLastNluEvent().getBestIntent().getAct());

    }
}
