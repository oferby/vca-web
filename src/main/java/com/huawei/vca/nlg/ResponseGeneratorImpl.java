package com.huawei.vca.nlg;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Option;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.controller.SlotRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;
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

    @Override
    public BotUtterEvent generateResponse(String actionId) {

        if (actionId.startsWith("utter.ask.slot.")) {
            String[] split = actionId.split("\\.");
            String slotName = split[split.length - 1];
            Optional<SlotEntity> optionalSlotEntity = slotRepository.findById(slotName);
            assert optionalSlotEntity.isPresent();
            return this.generateQueryResponseForSlot(optionalSlotEntity.get());
        }

        Optional<BotUtterEntity> actionById = botUtterRepository.findById(actionId);

        if (!actionById.isPresent()) {
            throw new RuntimeException("invalid action id");
        }

        BotUtterEntity botUtterEntity = actionById.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();

        String text = this.getRandomTextFromCollection(botUtterEntity.getTextSet());
        botUtterEvent.setText(text);
        botUtterEvent.setId(botUtterEntity.getId());

        return botUtterEvent;

    }

    @Override
    public BotUtterEvent generateNoSolution() {

        Optional<BotUtterEntity> optionalBotUtterEntity = botUtterRepository.findById("utter.general.misunderstood");
        assert optionalBotUtterEntity.isPresent();

        BotUtterEntity botUtterEntity = optionalBotUtterEntity.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();

        String text = this.getRandomTextFromCollection(botUtterEntity.getTextSet());
        botUtterEvent.setText(text);
        botUtterEvent.setId(botUtterEntity.getId());

        return botUtterEvent;
    }

    @Override
    public BotUtterEvent generateResponseForMenuItem(MenuItemEntity menuItemEntity) {

        String text = "Your order is: " + menuItemEntity.getDescription() + ", is that correct?";

        BotUtterEvent botUtterEvent = new BotUtterEvent();
        botUtterEvent.setText(text);
        botUtterEvent.addOption(new Option("yes", "Yes"));
        botUtterEvent.addOption(new Option("no", "No"));

        return botUtterEvent;
    }

    @Override
    public BotUtterEvent generateQueryResponseForSlot(SlotEntity slotEntity) {

        String slotName = slotEntity.getName();
        String utterId = "utter.ask.slot." + slotName;

        Optional<BotUtterEntity> optionalBotUtterEntity = botUtterRepository.findById(utterId);

        if (!optionalBotUtterEntity.isPresent())
            throw new RuntimeException("did not find utter for slot: " + slotName);

        BotUtterEvent botUtterEvent = new BotUtterEvent();

        String text = optionalBotUtterEntity.get().getTextSet().iterator().next();
        if (slotEntity.getValues().size() > 0) {
            text = text + " These are the options:";
        }

        botUtterEvent.setText(text);

        for (String value : slotEntity.getValues()) {
            botUtterEvent.addOption(new Option(value, value));
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
}
