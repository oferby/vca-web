package com.huawei.vca.repository;

import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBotUtterRepository {

    @Autowired
    private BotUtterRepository botUtterRepository;

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

}
