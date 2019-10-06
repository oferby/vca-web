package com.huawei.vca.repository.nlu;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestIntentExamples {

    @Autowired
    IntentExampleRepository intentExampleRepository;

    @Test
    public void testIntentExampleCRUD(){

        String text =  "this is the text";
        String intent = "text_intent";

        IntentExample intentExample = new IntentExample();
        intentExample.setText(text);
        intentExample.setIntent(intent);

        EntityExample entityExample = new EntityExample();
        entityExample.setEntity("k1");
        entityExample.setValue("v1");

        intentExample.addEntity(entityExample);

        intentExampleRepository.save(intentExample);

        Optional<IntentExample> optionalIntentExample = intentExampleRepository.findById(text);

        assert optionalIntentExample.isPresent();

        IntentExample example = optionalIntentExample.get();

        assert example.equals(intentExample);

        intentExampleRepository.delete(example);

        optionalIntentExample = intentExampleRepository.findById(text);

        assert !optionalIntentExample.isPresent();

    }


}
