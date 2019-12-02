package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.Act;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Set;

@Controller
public class ConfirmDenySkill implements PreprocessorSkill {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ObservationCreatorController observationCreatorController;

    @Override
    public void process(Dialogue dialogue) {

        Act act = dialogue.getLastNluEvent().getBestIntent().getAct();

        if (act != Act.AFFIRM && act != Act.DENY)
            return;

        dialogue.addObservation("act"+":"+act.getValue(), dialogue.getLastNluEvent().getBestIntent().getConfidence());

        Set<Slot> slots = dialogue.getLastNluEvent().getSlots();
        observationCreatorController.createObservations(dialogue.getObservations(), slots, dialogue.getLastNluEvent().getBestIntent().getAct());

    }

}
