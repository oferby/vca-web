package com.huawei.vca.knowledgebase;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.entity.MenuItemEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Controller
public class SimpleKnowledgebaseManager implements SkillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private String goalPrediction = "goal_prediction";

    @Override
    public PredictedAction getPredictedAction(Dialogue dialogue) {

        PredictedAction predictedAction = new PredictedAction();

//        add here what to ask next or return the above object

        predictedAction.setConfidence((float) 0.0);
        logger.debug("got prediction: " + predictedAction);

        return predictedAction;
    }

    private GoalPrediction findUserGoal(Dialogue dialogue) {

        List<Slot> informSlots = new ArrayList<>();
        List<Slot> denySlots = new ArrayList<>();

        for (Event event : dialogue.getHistory()) {
            if (event instanceof UserUtterEvent) {
                NluEvent nluEvent = ((UserUtterEvent) event).getNluEvent();
                Set<Slot> slots = nluEvent.getSlots();
                if (slots == null)
                    continue;

                String intent = nluEvent.getBestIntent().getIntent();

                if (intent.equals("inform")) {
                    Set<Slot> foodSlots = new HashSet<>();

                    for (Slot slot : slots) {
                        if (slot.getKey().startsWith("food"))
                            foodSlots.add(slot.getSmallCopy());
                    }
                    informSlots.addAll(foodSlots);

                } else if (intent.equals("deny")) {
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


    private GoalPrediction getGoalPrediction(List<Slot> informSlots, List<Slot> denySlots) {

        GoalPrediction goalPrediction = new GoalPrediction();

        if (informSlots.size() > 0) {

            List<MenuItemEntity> result = mongoTemplate.find(query(where("slots").all(informSlots)), MenuItemEntity.class);

            if (denySlots.size() > 0) {

                List<MenuItemEntity> notResult = mongoTemplate.find(query(where("slots").not().all(denySlots)), MenuItemEntity.class);
                List<MenuItemEntity> intersection = MenuItemEntity.intersection(result, notResult);
                goalPrediction.setPossibleGoals(intersection);

            } else {
                goalPrediction.setPossibleGoals(result);
            }

        }

        logger.debug("calculated prediction for user goal: " + goalPrediction);
        return goalPrediction;
    }

}