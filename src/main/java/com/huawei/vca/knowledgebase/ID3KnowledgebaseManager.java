package com.huawei.vca.knowledgebase;

package com.huawei.vca.knowledgebase;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.controller.SlotRepository;
import com.huawei.vca.repository.entity.SlotEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;
import java.util.*;

@Controller
public class ID3KnowledgebaseManager implements SkillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ResponseGenerator responseGenerator;

    private float confidence = (float) 0.8;


    private List<String> slotValues = new ArrayList<>();

    @Override
    public PredictedAction getPredictedAction(Dialogue dialogue) {

        PredictedAction predictedAction = new PredictedAction();

        GoalPrediction userGoal = findUserGoal(dialogue);
        this.findBestAction(userGoal);
        this.addBotUtterEvent(predictedAction, userGoal);

        logger.debug("Prediction from KB: " + predictedAction);
        return predictedAction;
    }

    private void findBestAction(GoalPrediction goalPrediction) {

    }

    private GoalPrediction findUserGoal(Dialogue dialogue) {

        List<Slot> informSlots = new ArrayList<>();
        List<Slot> denySlots = new ArrayList<>();

        for (Event event : dialogue.getHistory()) { //for all events in dialogue history
            if (event instanceof UserUtterEvent) {
                NluEvent nluEvent = ((UserUtterEvent) event).getNluEvent();
                Set<Slot> slots = nluEvent.getSlots();
                if (slots == null)
                    continue;

                String intent = nluEvent.getBestIntent().getIntent();

                if (intent.equals("inform")) {      // gather inform slots
                    Set<Slot> foodSlots = new HashSet<>();

                    for (Slot slot : slots) {
                        if (slot.getKey().startsWith("food"))
                            foodSlots.add(slot.getSmallCopy());
                    }
                    informSlots.addAll(foodSlots);

                } else if (intent.equals("deny")) {       // gather deny slots
                    Set<Slot> foodSlots = new HashSet<>();
                    for (Slot slot : slots) {
                        if (slot.getKey().startsWith("food"))
                            foodSlots.add(slot.getSmallCopy());
                    }

                    denySlots.addAll(foodSlots);

                } else {
                    logger.error("got user utter with invalid intent: " + intent);
                }

            }
        }

        return this.getGoalPrediction(informSlots, denySlots);

    }
    private void addBotUtterEvent(PredictedAction predictedAction, GoalPrediction goalPrediction) {

        if (goalPrediction.getPossibleGoals() == null || goalPrediction.getPossibleGoals().size() == 0) {
            BotUtterEvent botUtterEvent = responseGenerator.generateNoSolution(this.getClass());
            predictedAction.setBotEvent(botUtterEvent);
            predictedAction.setConfidence((float) 0.1);
            return;
        }

        if (goalPrediction.getPossibleGoals().size() == 1) {

            BotUtterEvent botUtterEvent = responseGenerator.generateResponseForMenuItem(goalPrediction.getPossibleGoals().get(0), this.getClass());
            predictedAction.setConfidence(confidence);
            predictedAction.setBotEvent(botUtterEvent);
            return;
        }

        if (goalPrediction.getBestNextQuestion() == null) {
            logger.debug("**** should not be here *******");
            logger.debug(goalPrediction.toString());
            return;
        }

        SlotEntity slotEntity = goalPrediction.getBestNextQuestion();

        BotUtterEvent botUtterEvent = responseGenerator.generateQueryResponseForSlot(slotEntity, this.getClass());

        predictedAction.setBotEvent(botUtterEvent);
        predictedAction.setConfidence(confidence);

    }
}