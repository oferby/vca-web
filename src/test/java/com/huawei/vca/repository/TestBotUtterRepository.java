package com.huawei.vca.repository;

import com.huawei.vca.message.BotUtterEvent;
import com.huawei.vca.message.Option;
import com.huawei.vca.nlg.ResponseGenerator;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.controller.SlotRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.SlotEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBotUtterRepository {

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Autowired
    private ResponseGenerator responseGenerator;

//    @Test
    public void testCRUD() {

        BotUtterEntity botUtterEntity = this.getBotUtterEntity();

        botUtterRepository.save(botUtterEntity);

        List<BotUtterEntity> all = botUtterRepository.findAll();

        assert all.size() == 1;
        assert all.contains(botUtterEntity);

        String id = botUtterEntity.getId();
        botUtterRepository.deleteById(id);

        Optional<BotUtterEntity> byId = botUtterRepository.findById(id);

        assert !byId.isPresent();


    }

//    @Test
    public void addActions(){

        this.botUtterRepository.deleteAll();

        BotUtterEntity botUtterEntity = this.getBotUtterEntity();

        botUtterRepository.save(botUtterEntity);

        botUtterEntity = this.getAnotherBotUtterEntity();

        botUtterRepository.save(botUtterEntity);

        List<BotUtterEntity> all = botUtterRepository.findAll();

        assert all.size() == 2;

    }

    private BotUtterEntity getBotUtterEntity(){

        BotUtterEntity entity = new BotUtterEntity();
        entity.setId("greet");
        entity.addToTextList("How can I help you?");
        entity.addToTextList("Good morning.");
        entity.addToTextList("Hello to you too :-)");

        return entity;

    }

    private BotUtterEntity getAnotherBotUtterEntity(){

        BotUtterEntity entity = new BotUtterEntity();
        entity.setId("goodbye");
        entity.addToTextList("bye bye");
        entity.addToTextList("Goodbye.");
        entity.addToTextList("see you next time.");

        return entity;

    }

//    @Test
    public void deleteAll() {
        this.botUtterRepository.deleteAll();
    }


//    @Test
    public void addSlotUtters() {


        List<SlotEntity> slotEntities = slotRepository.findAll();

        List<BotUtterEntity>botUtterEntities = new ArrayList<>();
        for (SlotEntity slotEntity : slotEntities) {

            String[] split = slotEntity.getName().split(":");
            StringBuilder category = new StringBuilder(split[split.length - 1]);
            split = category.toString().split("_");
            if (split.length > 1) {
                category = new StringBuilder();
                for (String s : split) {
                    category.append(" ").append(s);
                }
                category = new StringBuilder(category.toString().trim());
            }

            String question = "What kind of " + category + " would you like?";
            String utterId = "utter.ask.slot." + slotEntity.getName();

            BotUtterEntity botUtterEntity = new BotUtterEntity();
            botUtterEntity.setId(utterId);
            botUtterEntity.addToTextList(question);

            botUtterEntities.add(botUtterEntity);
        }

        botUtterRepository.saveAll(botUtterEntities);

    }


    @Test
    public void testUtterForSlot() {

        String slotId = "food:drink:hard:wine";

        Optional<SlotEntity> optionalSlotEntity = slotRepository.findById(slotId);

        assert optionalSlotEntity.isPresent();

        BotUtterEvent botUtterEvent = responseGenerator.generateQueryResponseForSlot(optionalSlotEntity.get());

        assert botUtterEvent != null;

        String utterId = "utter.ask.slot." + slotId;
        botUtterEvent = responseGenerator.generateResponse(utterId);

        assert botUtterEvent != null;

    }
}
