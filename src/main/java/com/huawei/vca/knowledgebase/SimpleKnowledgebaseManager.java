package com.huawei.vca.knowledgebase;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.controller.SlotRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;
import org.apache.commons.lang3.EnumUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.*;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Controller
public class SimpleKnowledgebaseManager implements SkillController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ResponseGenerator responseGenerator;

//    private String goalPrediction = "goal_prediction";

    private List<String> slotValues = new ArrayList<>();

    @Override
    public PredictedAction getPredictedAction(Dialogue dialogue) {

        PredictedAction predictedAction = new PredictedAction();

        GoalPrediction userGoal = findUserGoal(dialogue);
        this.findBestAction(userGoal);

        if (userGoal.getBestNextQuestion() != null) {
            this.addBotUtterEvent(predictedAction, userGoal);
        }

        logger.debug("got prediction from KB: " + predictedAction);
        return predictedAction;
    }

    private void addBotUtterEvent(PredictedAction predictedAction, GoalPrediction goalPrediction) {

        SlotEntity slotEntity = goalPrediction.getBestNextQuestion();

        BotUtterEvent botUtterEvent = responseGenerator.generateQueryResponseForSlot(slotEntity);

        predictedAction.setBotEvent(botUtterEvent);
        predictedAction.setConfidence((float) 0.95);

    }

    private void findBestAction(GoalPrediction goalPrediction) {

        if (goalPrediction.getPossibleGoals() == null || goalPrediction.getPossibleGoals().size() == 0)
            return;

        Map<String, Map<String, Integer>> slotMap = new HashMap<>();

        for (MenuItemEntity possibleGoal : goalPrediction.getPossibleGoals()) {
            for (Slot slot : possibleGoal.getSlots()) {
                if (!slotMap.containsKey(slot.getKey())) {
                    Map<String, Integer> vMap = new HashMap<>();
                    vMap.put(slot.getValue(), 1);
                    slotMap.put(slot.getKey(), vMap);
                } else {

                    Map<String, Integer> vMap = slotMap.get(slot.getKey());

                    if (vMap.containsKey(slot.getValue())) {
                        Integer value = vMap.get(slot.getValue());
                        vMap.put(slot.getValue(), value + 1);
                    } else {
                        vMap.put(slot.getValue(), 1);
                    }


                }

            }

        }

        for (String slotValue : slotValues) {
            boolean found = this.searchSlots(slotMap, goalPrediction, slotValue);
            if (found)
                return;
        }

    }


    private boolean searchSlots(Map<String, Map<String, Integer>> slotMap, GoalPrediction goalPrediction, String searchValue) {

        if (slotMap.containsKey(searchValue) && slotMap.get(searchValue).keySet().size() > 1) {

            int possibleGoals = goalPrediction.getPossibleGoals().size();
            boolean maxFound = false;
            for (Integer value : slotMap.get(searchValue).values()) {
                if (value == possibleGoals){
                    maxFound = true;
                    break;
                }
            }

            if (maxFound)
                return false;

            Optional<SlotEntity> optionalSlotEntity = slotRepository.findById(searchValue);
            assert optionalSlotEntity.isPresent();

            SlotEntity slotEntity = optionalSlotEntity.get();
            Set<String> tempValues = new HashSet<>(slotEntity.getValues());
            Set<String> values = slotMap.get(searchValue).keySet();
            for (String value : slotEntity.getValues()) {
                if (!values.contains(value)) {
                    tempValues.remove(value);
                }
            }
            slotEntity.setValues(tempValues);
            goalPrediction.setBestNextQuestion(slotEntity);
            return true;
        }

        return false;

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
        goalPrediction.setInformSlots(informSlots);
        goalPrediction.setDenySlots(denySlots);

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

        int size = 0;
        if (goalPrediction.getPossibleGoals() != null)
            size = goalPrediction.getPossibleGoals().size();

        logger.debug("calculated prediction for user goal. size: " + size);
        return goalPrediction;
    }

    @PostConstruct
    private void setup() {
        slotValues.add("food:main_dish");
        slotValues.add("food:ingredient:meat");
        slotValues.add("food:main_dish:burger:cook_level");
        slotValues.add("food:condiment:sauce:burger_sauce");

        slotValues.add("food:main_dish:pasta");
        slotValues.add("food:condiment:sauce:pasta_sauce");
        slotValues.add("food:ingredient:plant:vegetable");
        slotValues.add("food:drink:hard");
        slotValues.add("food:drink:soft");


    }

}
