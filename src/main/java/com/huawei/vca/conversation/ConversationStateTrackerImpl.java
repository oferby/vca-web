package com.huawei.vca.conversation;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.grpc.NluService;
import com.huawei.vca.intent.Entity;
import com.huawei.vca.intent.NluResponse;
import com.huawei.vca.message.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
public class ConversationStateTrackerImpl implements ConversationStateTracker{

    private static final Logger logger = LoggerFactory.getLogger(ConversationStateTrackerImpl.class);

    @Autowired
    private NluService nluService;

    @Autowired
    private List<SkillController> skillControllers;

    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        this.handleNlu(dialogue);

        List<PredictedAction>predictedActions = new ArrayList<>();

        for (SkillController skillController : skillControllers) {
            PredictedAction predictedAction = skillController.getPredictedAction(dialogue);
        }

        Collections.sort(predictedActions);


        return null;
    }

    @Override
    public Dialogue handleNluOnly(Dialogue dialogue) {
        this.handleNlu(dialogue);
        return dialogue;
    }

    private void handleNlu(Dialogue dialogue) {

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
            nluEvent.addSlot(new Slot(entity.getEntity(), entity.getValue(), entity.getConfidence(),entity.getStart(), entity.getEnd()));
        }

        return nluEvent;
    }

    @Override
    public void addActionToDialogue(Dialogue dialogue) {

    }
}
