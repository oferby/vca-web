package com.huawei.vca.conversation;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.grpc.NluService;
import com.huawei.vca.intent.Entity;
import com.huawei.vca.intent.NluResponse;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ConversationStateTrackerImpl implements ConversationStateTracker{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NluService nluService;

    @Autowired
    private List<SkillController> skillControllers;

    @Autowired
    private ResponseGenerator responseGenerator;

    private static String graphLocation = "graph_location";

    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        this.handleNlu(dialogue);

        List<PredictedAction>predictedActions = new ArrayList<>();

        for (SkillController skillController : skillControllers) {
            predictedActions.add(skillController.getPredictedAction(dialogue));
        }

        Collections.sort(predictedActions);

        PredictedAction bestPredictedAction = predictedActions.get(0);

        if (bestPredictedAction.getConfidence() == 0) {

            dialogue.addProperty(graphLocation, "-1");

            if (!dialogue.isTraining())
                this.addDefaultUtterEvent(dialogue);


        } else {

            dialogue.setText(((BotUtterEvent)bestPredictedAction.getBotEvent()).getText());
            dialogue.addToHistory(bestPredictedAction.getBotEvent());

            Map<String, String> properties = bestPredictedAction.getProperties();
            if (properties != null) {
                Set<String> keys = properties.keySet();
                for (String key : keys) {
                    dialogue.addProperty(key, properties.get(key));
                }

            }

        }

        return dialogue;
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

    private void addDefaultUtterEvent(Dialogue dialogue) {
        BotDefaultUtterEvent botUtterEvent = new BotDefaultUtterEvent("I'm sorry but I did not understand what you've said. Let me route your call to human.");
        dialogue.addToHistory(botUtterEvent);
        dialogue.setText(botUtterEvent.getText());
        dialogue.setNeedOperator(true);
    }




}
