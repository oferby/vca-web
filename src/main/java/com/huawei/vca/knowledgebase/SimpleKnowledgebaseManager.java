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
import org.springframework.beans.factory.annotation.Value;
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

    //    @Value("${skill.confidence.kb}")
//    @Value("#{Integer.valueOf('${skill.confidence.kb}')}")
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

    private void findBestAction(GoalPrediction goalPrediction) {

        if (goalPrediction.getPossibleGoals() == null || goalPrediction.getPossibleGoals().size() < 2)
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

//        check for slot only in 1 of the possible goals
        for (String slotValue : slotValues) {
            boolean found = this.searchLeftSlots(slotMap, goalPrediction, slotValue);
            if (found)
                return;
        }

    }

    private boolean searchLeftSlots(Map<String, Map<String, Integer>> slotMap, GoalPrediction goalPrediction, String searchValue) {

        int possibleGoals = goalPrediction.getPossibleGoals().size();
        boolean maxFound = this.checkMax(slotMap, possibleGoals, searchValue);
        if (maxFound)
            return false;

        for (Integer value : slotMap.get(searchValue).values()) {
            if (value > 0 && value < possibleGoals) {
                SlotEntity slotEntity = this.getSlotEntityForSearchValue(slotMap, searchValue);
                goalPrediction.setBestNextQuestion(slotEntity);
                return true;
            }

        }

        return false;

    }

    private boolean searchSlots(Map<String, Map<String, Integer>> slotMap, GoalPrediction goalPrediction, String searchValue) {

        if (slotMap.containsKey(searchValue) && slotMap.get(searchValue).keySet().size() > 1) {

            int possibleGoals = goalPrediction.getPossibleGoals().size();
            boolean maxFound = this.checkMax(slotMap, possibleGoals, searchValue);
            if (maxFound)
                return false;

            SlotEntity slotEntity = this.getSlotEntityForSearchValue(slotMap, searchValue);
            goalPrediction.setBestNextQuestion(slotEntity);
            return true;
        }

        return false;

    }

    private boolean checkMax(Map<String, Map<String, Integer>> slotMap, int possibleGoals, String searchValue) {

        boolean maxFound = false;

        if (!slotMap.containsKey(searchValue))
            return true;

        for (Integer value : slotMap.get(searchValue).values()) {
            if (value == possibleGoals) {
                maxFound = true;
                break;
            }
        }

        return maxFound;

    }


    private SlotEntity getSlotEntityForSearchValue(Map<String, Map<String, Integer>> slotMap, String searchValue) {

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

        return slotEntity;
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


    public GoalPrediction getGoalPrediction(List<Slot> informSlots, List<Slot> denySlots) {

        GoalPrediction goalPrediction = new GoalPrediction();
        goalPrediction.setInformSlots(informSlots);
        goalPrediction.setDenySlots(denySlots);

        if (informSlots.size() > 0) {

            List<MenuItemEntity> result = mongoTemplate.find(query(where("slots").all(informSlots)), MenuItemEntity.class);

            if (denySlots.size() > 0) {

                List<MenuItemEntity> notResult = mongoTemplate.find(query(where("slots").nin(denySlots)), MenuItemEntity.class);
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
        slotValues.add("food:bread");

        slotValues.add("food:main_dish:pasta");
        slotValues.add("food:condiment:sauce:pasta_sauce");
        slotValues.add("food:ingredient:plant:vegetable");
        slotValues.add("food:drink:hard");
        slotValues.add("food:drink:soft");


    }

}
