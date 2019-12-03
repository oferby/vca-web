package com.huawei.vca.conversation;

import com.huawei.vca.conversation.policy.PolicyController;
import com.huawei.vca.conversation.skill.ObservationCreatorController;
import com.huawei.vca.conversation.skill.PreprocessorSkill;
import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.knowledgebase.KnowledgeBaseController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.graph.MinimalStepGraphController;
import com.huawei.vca.repository.nlu.NluController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ConversationStateTrackerImpl implements ConversationStateTracker{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NluController nluController;

    @Autowired
    private PolicyController policyController;

//    @Autowired
//    private List<SkillController>skillControllers;

    @Autowired
    private MinimalStepGraphController minimalStepGraphController;

    @Autowired
    private List<PreprocessorSkill>preprocessorSkills;

    @Autowired
    private KnowledgeBaseController knowledgeBaseController;

    @Autowired
    private ResponseGenerator responseGenerator;

    @Autowired
    private ObservationCreatorController observationCreatorController;

    @Override
    public Dialogue handleDialogue(Dialogue dialogue) {

        dialogue.resetObservations();
        nluController.handleNlu(dialogue);

        preprocessorSkills.forEach(p -> p.process(dialogue));

        Map<String, Float> observations = dialogue.getObservations();
        logger.debug("user action: " + observations);

        Map<String, Float> state = dialogue.getState();
        if (state == null) {
            state = new HashMap<>();
            dialogue.setState(state);
        }

        logger.debug("current state: " + state);

        BotAct botAct = null;

        do {

            botAct = policyController.getBotAct(observations,state);

            this.transition(state,observations);

            this.transition(state, botAct);

            if (botAct == BotAct.DEFAULT){

                goToDefault(dialogue,state);
                break;

            } else if (botAct == BotAct.DB_SEARCH) {

                PredictedAction predictedAct = knowledgeBaseController.getPredictedAct(state);

                if (predictedAct.getConfidence() > 0.5) {
                    BotEvent botEvent = predictedAct.getBotEvent();
                    dialogue.setText(((BotUtterEvent) botEvent).getText());
                    dialogue.addToHistory(botEvent);

                    transition(state,BotAct.QUERY_USER);

                } else {

                    goToDefault(dialogue,state);

                }

                break;

            } else if (botAct == BotAct.SEARCH_ANSWER) {

                PredictedAction predictedAct = minimalStepGraphController.getPredictedAction(state, observations);
                if (predictedAct.getConfidence() > 0.5) {
                    BotEvent botEvent = predictedAct.getBotEvent();
                    dialogue.setText(((BotUtterEvent) botEvent).getText());
                    dialogue.addToHistory(botEvent);

                    state.put("temp.answer.found", (float) 1.0);

                } else {
                    state.put("temp.answer.found", (float) 0);
                }

            } else if (botAct == BotAct.INFORM_USER) {

                transition(state, BotAct.INFORM_USER);
                break;
            }


        } while (true);

        searchKey(state.keySet(),"temp.",true);
        return dialogue;
    }


    private void goToDefault(Dialogue dialogue, Map<String, Float> state){

        responseGenerator.addDefaultUtterEvent(dialogue);
        transition(state,BotAct.DEFAULT);

    }

    private void removeBotAct(Map<String, Float> state){
        searchKey(state.keySet(),"botAct", true);
    }

    private boolean searchKey(Set<String> keys, String keyToSearch, boolean withRemove){

        for (String key : keys) {
            if (key.startsWith(keyToSearch)){
                if (withRemove)
                    keys.remove(key);

                return true;
            }
        }

        return false;

    }



    private void transition(Map<String, Float> state, BotAct botAct){

        removeBotAct(state);
        state.put("botAct:"+botAct, (float) 1.0);

        logger.debug("state after action transition: " + state);
    }

    private void transition(Map<String, Float> state, Map<String, Float> observations){

        Map<String, Float> observationsForState = observationCreatorController.createObservationsForState(observations);
        state.putAll(observationsForState);

        logger.debug("state after observation transition: " + state);
    }



    @Override
    public Dialogue handleNluOnly(Dialogue dialogue) {
        nluController.handleNlu(dialogue);
        return dialogue;
    }

}
