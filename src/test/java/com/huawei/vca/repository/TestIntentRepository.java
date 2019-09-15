package com.huawei.vca.repository;

import com.huawei.vca.repository.controller.IntentRepository;
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
public class TestIntentRepository {

    @Autowired
    private IntentRepository intentRepository;

    @Test
    public void testCRUD(){

        IntentEntity intentEntity = this.getIntentEntity();

        intentRepository.save(intentEntity);
        List<IntentEntity> all = intentRepository.findAll();

        assert all.size() == 1;
        assert all.contains(intentEntity);

//        String intent = intentEntity.getIntent();
//        intentRepository.deleteById(intent);
//
//        Optional<IntentEntity> byId = intentRepository.findById(intent);
//
//        assert !byId.isPresent();

        intentEntity = this.getRejectIntentEntity();

        intentRepository.save(intentEntity);

    }


    private IntentEntity getIntentEntity() {

        IntentEntity intentEntity = new IntentEntity();
        intentEntity.setIntent("confirm");

        Set<String> textSet = new HashSet<>();
        textSet.add("ok");
        textSet.add("sure");
        textSet.add("fine by me");
        intentEntity.setTextSet(textSet);

        return intentEntity;

    }

    private IntentEntity getRejectIntentEntity() {

        IntentEntity intentEntity = new IntentEntity();
        intentEntity.setIntent("reject");

        Set<String> textSet = new HashSet<>();
        textSet.add("no");
        textSet.add("no way");
        textSet.add("not for me");
        intentEntity.setTextSet(textSet);

        return intentEntity;

    }

    @Test
    public void addTestIntents() {

        intentRepository.deleteAll();

        IntentEntity intentEntity = this.getIntentEntity();
        intentRepository.save(intentEntity);

        intentEntity = this.getRejectIntentEntity();
        intentRepository.save(intentEntity);

    }

    @Test
    public void testFind(){

        List<IntentEntity> all = intentRepository.findAll();

        assert all.size() == 2;

    }



}
