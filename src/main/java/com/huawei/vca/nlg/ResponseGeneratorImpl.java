package com.huawei.vca.nlg;

import com.huawei.vca.message.*;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.controller.SlotRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;
import com.huawei.vca.repository.graph.OptionNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.*;

@Controller
public class ResponseGeneratorImpl implements ResponseGenerator {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public BotUtterEvent generateResponse(String actionId, Class skill) {

        if (actionId.startsWith("utter.ask.slot.")) {
            String[] split = actionId.split("\\.");
            String slotName = split[split.length - 1];
            Optional<SlotEntity> optionalSlotEntity = slotRepository.findById(slotName);
            assert optionalSlotEntity.isPresent();
            return this.generateQueryResponseForSlot(optionalSlotEntity.get(), skill);
        }

        return getUtterWithOptions(actionId, skill);

    }

    @Override
    public BotUtterEvent generateResponse(String actionId, List<OptionNode> options, Class skill) {

        BotUtterEvent botUtterEvent = getUtterWithOptions(actionId, skill);

        if (options != null && options.size() > 0) {
            for (OptionNode option : options) {
                botUtterEvent.addOption(new Option(option.getStringId()));
            }

        }

        return botUtterEvent;

    }

    private BotUtterEvent  getUtterWithOptions(String actionId, Class skill) {

        String askMenuItem = "utter.ask.menu.item.";
        if (actionId.startsWith(askMenuItem)){
            String menuItemId = actionId.substring(askMenuItem.length());
            Optional<MenuItemEntity> optionalMenuItemEntity = menuItemRepository.findById(menuItemId);
            if (!optionalMenuItemEntity.isPresent())
                throw new RuntimeException("invalid action id: " + actionId);

            return this.generateResponseForMenuItem(optionalMenuItemEntity.get(), skill);
        }

        Optional<BotUtterEntity> actionById = botUtterRepository.findById(actionId);

        if (!actionById.isPresent()) {
            throw new RuntimeException("invalid action id: " + actionId);
        }

        BotUtterEntity botUtterEntity = actionById.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();
        botUtterEvent.setSkillId(skill.getSimpleName());
        String text = this.getRandomTextFromCollection(botUtterEntity.getTextSet());
        botUtterEvent.setText(text);
        botUtterEvent.setId(botUtterEntity.getId());
        return botUtterEvent;
    }

    @Override
    public BotUtterEvent generateNoSolution(Class skill) {

        Optional<BotUtterEntity> optionalBotUtterEntity = botUtterRepository.findById("utter.general.misunderstood");
        assert optionalBotUtterEntity.isPresent();

        BotUtterEntity botUtterEntity = optionalBotUtterEntity.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();
        botUtterEvent.setSkillId(skill.getSimpleName());

        String text = this.getRandomTextFromCollection(botUtterEntity.getTextSet());
        botUtterEvent.setText(text);
        botUtterEvent.setId(botUtterEntity.getId());

        return botUtterEvent;
    }

    @Override
    public BotUtterEvent generateResponseForMenuItem(MenuItemEntity menuItemEntity, Class skill) {

        String text = "Your order is: " + menuItemEntity.getDescription() + ", would you like to proceed with the order?";

        BotUtterEvent botUtterEvent = new BotUtterEvent();
        botUtterEvent.setSkillId(skill.getSimpleName());
        botUtterEvent.setId("utter.ask.menu.item." + menuItemEntity.getId());
        botUtterEvent.setText(text);

        if (menuItemEntity.getImageUrl() != null)
            botUtterEvent.addImage(new ImageInfo("/images/food/" + menuItemEntity.getImageUrl()));

        botUtterEvent.addOption(new Option("yes"));
        botUtterEvent.addOption(new Option("no"));

        return botUtterEvent;
    }

    @Override
    public BotUtterEvent generateQueryResponseForSlot(SlotEntity slotEntity, Class skill) {

        String slotName = slotEntity.getName();
        String utterId = "utter.ask.slot." + slotName;

        Optional<BotUtterEntity> optionalBotUtterEntity = botUtterRepository.findById(utterId);

        if (!optionalBotUtterEntity.isPresent())
            throw new RuntimeException("did not find utter for slot: " + slotName);

        BotUtterEvent botUtterEvent = new BotUtterEvent();
        botUtterEvent.setSkillId(skill.getSimpleName());
        botUtterEvent.setId(utterId);

        String text = optionalBotUtterEntity.get().getTextSet().iterator().next();
        if (slotEntity.getValues().size() > 0) {
            text = text + " These are the options:";
        }

        botUtterEvent.setText(text);

        for (String value : slotEntity.getValues()) {
            botUtterEvent.addOption(new Option(value));
        }

        return botUtterEvent;
    }


    private String getRandomTextFromCollection(Set<String> textSet) {

        if (textSet.size() == 0)
            return null;

        List<String> stringList = new ArrayList<>(textSet);
        Random rand = new Random();
        int r = rand.nextInt(stringList.size());
        return stringList.get(r);

    }

    public void addDefaultUtterEvent(Dialogue dialogue) {
        BotDefaultUtterEvent botUtterEvent = new BotDefaultUtterEvent("I'm sorry but I did not understand what you've said. Let me route your call to human.");
        dialogue.addToHistory(botUtterEvent);
        dialogue.setText(botUtterEvent.getText());
        dialogue.setNeedOperator(true);
    }

    @Override
    public void generateUtterForPredictedAction(Dialogue dialogue, PredictedAction bestPredictedAction) {

        dialogue.setText(((BotUtterEvent)bestPredictedAction.getBotEvent()).getText());
        dialogue.addToHistory(bestPredictedAction.getBotEvent());

    }
}
