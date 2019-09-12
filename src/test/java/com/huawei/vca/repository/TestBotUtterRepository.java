package com.huawei.vca.repository;

import com.huawei.vca.repository.controller.BotUtterRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestBotUtterRepository {

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Test
    public void testCRUD(){

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

    private BotUtterEntity getBotUtterEntity(){

        BotUtterEntity entity = new BotUtterEntity();
        entity.setActionId(1);
        Set<String>textSet = new HashSet<>();
        textSet.add("How can I help you?");
        textSet.add("Good morning.");
        textSet.add("Hello to you too :-)");

        return entity;

    }



}
