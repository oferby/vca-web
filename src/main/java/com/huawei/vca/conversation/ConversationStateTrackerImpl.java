package com.huawei.vca.conversation;

import com.huawei.vca.conversation.policy.PolicyController;
import com.huawei.vca.conversation.skill.ObservationCreatorController;
import com.huawei.vca.conversation.skill.PostProcessorSkill;
import com.huawei.vca.conversation.skill.PreprocessorSkill;
import com.huawei.vca.knowledgebase.KnowledgeBaseController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.graph.ConversationGraphController;
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
    private ConversationGraphController conversationGraphController;

    @Autowired
    private List<PreprocessorSkill>preprocessorSkills;

    @Autowired
    private List<PostProcessorSkill>postProcessorSkills;

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

        this.addStateToHistory(dialogue,state,observations);

        logger.debug("current state: " + state);

        BotAct botAct = null;

        do {

            botAct = policyController.getBotAct(observations,state);



            if (botAct == BotAct.DEFAULT){

                goToDefault(dialogue,state);
                break;

            } else if (botAct == BotAct.INFORM_USER) {
                this.transition(state, botAct);
                break;

            } else if (botAct == BotAct.DB_SEARCH) {

                PredictedAction predictedAct = knowledgeBaseController.getPredictedAct(state);

                if (predictedAct.getConfidence() > 0.5) {
                    postProcess(dialogue, predictedAct.getBotEvent());

                    state.put("temp.answer.found", (float) 1.0);

                } else {

                    state.put("temp.answer.found", (float) 0);

                }

            } else if (botAct == BotAct.SEARCH_QUERY) {

                PredictedAction predictedAct = minimalStepGraphController.getPredictedAction(state, observations);
                if (predictedAct.getConfidence() > 0.5) {

                    postProcess(dialogue, predictedAct.getBotEvent());

                    state.put("temp.answer.found", (float) 1.0);

                } else {
                    state.put("temp.answer.found", (float) 0);
                }

            } else if (botAct == BotAct.SEARCH_HISTORY) {

                PredictedAction predictedAction = conversationGraphController.getPredictedAction(state, observations);

                if (predictedAction.getConfidence() > 0.5) {

                    postProcess(dialogue, predictedAction.getBotEvent());

                    state.put("temp.history.found", (float) 1.0);

                } else {
                    state.put("temp.history.found", (float) 0);
                }

            }

            this.transition(state,observations);
            this.transition(state, botAct);

        } while (true);

        searchKey(state,"temp.",true);
        return dialogue;
    }

    private void addStateToHistory(Dialogue dialogue, Map<String, Float> state, Map<String, Float> observation) {

        HashMap<String, Float> stateCopy = new HashMap<>(state);
        HashMap<String, Float> observationCopy = new HashMap<>(observation);

        UserUtterEvent userEvent = (UserUtterEvent) dialogue.getHistory().get(dialogue.getHistory().size() - 1);
        userEvent.setState(stateCopy);
        userEvent.setObservations(observationCopy);

    }


    private void goToDefault(Dialogue dialogue, Map<String, Float> state){

        responseGenerator.addDefaultUtterEvent(dialogue);
        transition(state,BotAct.DEFAULT);

    }

    private void removeBotAct(Map<String, Float> state){
        searchKey(state,"botAct", true);
    }

    private boolean searchKey(Map<String, Float> state, String keyToSearch, boolean withRemove){

        Set<String> keys = state.keySet();
        Set<String>keysToRemove=new HashSet<>();

        for (String key : keys) {
            if (key.startsWith(keyToSearch)){
                if (withRemove)
                    keysToRemove.add(key);
            }
        }

        if (!keysToRemove.isEmpty()) {
            for (String key : keysToRemove) {
                state.remove(key);
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

    private void postProcess(Dialogue dialogue, BotEvent botEvent){

        for (PostProcessorSkill postProcessorSkill : postProcessorSkills) {
            postProcessorSkill.process(dialogue,botEvent);
        }

    }

    @Override
    public Dialogue handleNluOnly(Dialogue dialogue) {
        nluController.handleNlu(dialogue);
        return dialogue;
    }

}
