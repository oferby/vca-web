package com.huawei.vca.nlg;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import com.huawei.vca.repository.entity.SlotEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.Set;

@Controller
public class ResponseGeneratorImpl implements ResponseGenerator{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Override
    public BotUtterEvent generateResponse(String actionId) {
        Optional<BotUtterEntity> actionById = botUtterRepository.findById(actionId);

        if (!actionById.isPresent()) {
            throw new RuntimeException("invalid action id");
        }

        BotUtterEntity botUtterEntity = actionById.get();
        BotUtterEvent botUtterEvent = new BotUtterEvent();

//        TODO
//        change to random
        Set<String> textIterator = botUtterEntity.getTextSet();
        String text;
        if (textIterator.iterator().hasNext()) {
            text = textIterator.iterator().next();

        } else {
            text = "** no messages found for action ** " + botUtterEntity.getId();
        }
        botUtterEvent.setId(botUtterEntity.getId());
        botUtterEvent.setText(text);

        return botUtterEvent;

    }

    @Override
    public BotUtterEvent generateResponse(MenuItemEntity menuItemEntity) {
        return null;
    }

    @Override
    public BotUtterEvent generateQueryResponseForSlot(SlotEntity slotEntity) {
        return null;
    }
}
