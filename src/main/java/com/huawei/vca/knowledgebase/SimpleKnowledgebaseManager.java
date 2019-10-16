package com.huawei.vca.knowledgebase;

import com.huawei.vca.conversation.skill.SkillController;
import com.huawei.vca.message.*;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.controller.SlotRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.VariableOperators;
import org.springframework.stereotype.Controller;

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

    private String goalPrediction = "goal_prediction";

    @Override
    public PredictedAction getPredictedAction(Dialogue dialogue) {

        PredictedAction predictedAction = new PredictedAction();

        GoalPrediction userGoal = findUserGoal(dialogue);




        logger.debug("got prediction from guess: " + predictedAction);
        return predictedAction;
    }

//    private PredictedAction findBestActionMostValues(GoalPrediction userGoal) {
//        PredictedAction predictedAction = new PredictedAction();
//        List<MenuItemEntity> possibleGoals = userGoal.getPossibleGoals();
//        if (possibleGoals.size() == 0) {  //no goals remaining, return blank
//            return predictedAction;
//        } else if (possibleGoals.size() == 1) { //1 goal remaining, return offer for it
//            BotUtterEvent botUtterEvent = responseGenerator.generateResponse(possibleGoals.get(0));
//            predictedAction.setActionId(botUtterEvent.getId());
//            predictedAction.setConfidence((float) 0.8);
//            return predictedAction;
//        } else {
//            List<SlotEntity> slotSpace = slotRepository.findAll();
//            List<Slot> informedSlots = userGoal.getInformSlots();
//            Map<SlotEntity, ArrayList<String>> entropyMap = new HashMap<>();
//            for (SlotEntity slotEntity : slotSpace) {
//                ArrayList valueList = new ArrayList<String>();
//                entropyMap.put(slotEntity, valueList);
//                for (MenuItemEntity menuItemEntity : possibleGoals) {
//                    Set<Slot> slots = menuItemEntity.getSlots();
//                    for (Slot slot : slots) {
//                        if (slot.getKey().equals(slotEntity.getName()) &&
//                                !entropyMap.get(slotEntity).contains(slot.getValue())) {
//                            entropyMap.get(slotEntity).add(slot.getValue());
//                        }
//                    }
//                }
//            }
//            Map.Entry<SlotEntity, ArrayList<String>> maxEntry = null;
//            for (Map.Entry<SlotEntity, ArrayList<String>> entry : entropyMap.entrySet())
//            {
//                if (maxEntry == null || entry.getValue().size() > maxEntry.getValue().size()) {
//                    maxEntry = entry;
//                }
//            }
//            LinkedHashSet currentValueList = new LinkedHashSet<>(maxEntry.getValue());
//            maxEntry.getKey().setValues(currentValueList);
//            BotUtterEvent botUtterEvent = responseGenerator.generateQueryResponseForSlot(maxEntry.getKey());
//            predictedAction.setActionId(botUtterEvent.getId());
//            predictedAction.setConfidence((float) 0.95);
//            return predictedAction;
//        }
//    }
//
//    private PredictedAction findBestActionLinkedList(GoalPrediction userGoal) {
//
//        PredictedAction predictedAction = new PredictedAction();
//        List<MenuItemEntity> possibleGoals = userGoal.getPossibleGoals();
//        if (possibleGoals.size() == 0) {  //no goals remaining, return blank
//            return predictedAction;
//        } else if (possibleGoals.size() == 1) { //1 goal remaining, return offer for it
//            BotUtterEvent botUtterEvent = responseGenerator.generateResponse(possibleGoals.get(0));
//            predictedAction.setActionId(botUtterEvent.getId());
//            predictedAction.setConfidence((float) 0.8);
//            return predictedAction;
//        } else { //more than one goal remaining, choose next action based on slots
//            List<SlotEntity> slotSpace = slotRepository.findAll();
//            List<Slot> informedSlots = userGoal.getInformSlots();
//            Map<String, List<String>> actionTree = new HashMap<>();
//            List<String> burgerActions = Arrays.asList("food:ingredient:meat", "food:main_dish:burger:cook_level",
//                    "food:condiment:sauce:burger_sauce", "food:ingredient:plant:vegetable", "food:bread");
//            List<String> pastaActions = Arrays.asList("food:main_dish:pasta", "food:condiment:sauce:pasta_sauce",
//                    "food:ingredient:plant:vegetable");
//            List<String> generalActions = Arrays.asList("food:side_dish", "food:drink");
//            actionTree.put("burger", burgerActions);
//            actionTree.put("pasta", pastaActions);
//            actionTree.put("general", generalActions);
//
//            //remove informed slots from slot space
//            //is there a better way to do this than nested for loops?
//            //this won't work if someone changed their mind
//            for (Slot slot : informedSlots) {
//                String informedKey = slot.getKey();
//                String informedValue = slot.getValue();
//                if (informedValue.contains("burger") || informedKey.contains("burger")
//                        || informedKey.contains("cook_level") || informedKey.contains("bread")) {
//                    actionTree.remove("pasta");
//                    actionTree.get("burger").remove(informedKey);
//                } else if (informedValue.contains("pasta") || informedKey.contains("pasta")) {
//                    actionTree.remove("burger");
//                    actionTree.get("pasta").remove(informedKey);
//                } else if (informedKey.contains("side") || informedKey.contains("drink")) {
//                    actionTree.get("general").remove(informedKey);
//                }
//            }
//            SlotEntity nextSlot = null;
//            if (actionTree.keySet().size() < 1 || actionTree.keySet().size() > 2) {
//                throw new IllegalArgumentException("no categories left in action tree");
//            } else if (actionTree.keySet().size() == 1) {
//                nextSlot = findByName(slotSpace, actionTree.get("general").get(0));
//            } else if (actionTree.containsKey("burger")) {
//                nextSlot = findByName(slotSpace, actionTree.get("burger").get(0));
//            } else {
//                nextSlot = findByName(slotSpace, actionTree.get("pasta").get(0));
//            }
//
//            if (nextSlot == null) {throw new IllegalArgumentException("no actions found in action space");}
//            BotUtterEvent botUtterEvent = responseGenerator.generateQueryResponseForSlot(nextSlot);
//            predictedAction.setActionId(botUtterEvent.getId());
//            predictedAction.setConfidence((float) 0.7);
//            return predictedAction;
//        }
//    }

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

        logger.debug("calculated prediction for user goal. size: " + goalPrediction.getPossibleGoals().size());
        return goalPrediction;
    }

}
