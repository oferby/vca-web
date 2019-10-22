package com.huawei.vca.repository.nlu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.vca.repository.controller.SlotRepository;
import com.huawei.vca.repository.entity.SlotEntity;
import com.huawei.vca.repository.nlu.rasa.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLoadIntents {

    @Autowired
    private RasaExampleRepository rasaExampleRepository;

    @Autowired
    private RasaSynonymRepository rasaSynonymRepository;

    @Autowired
    private SlotRepository slotRepository;

    @Test
    public void load() throws IOException {

        // read json and write to db

        String filename = "/home/avivg-ubuntu/PycharmProjects/rasanlu-v3/data/data(full edit8).json";

        ObjectMapper mapper = new ObjectMapper();

        RasaDataRoot rasaDataRoot = mapper.readValue(new File(filename), RasaDataRoot.class);

        assert rasaDataRoot != null;

        rasaExampleRepository.deleteAll();
        rasaExampleRepository.saveAll(rasaDataRoot.getRasa_nlu_data().getCommon_examples());

        Map<String, SlotEntity>slotEntityMap = new HashMap<>();
        for (CommonExample common_example : rasaDataRoot.getRasa_nlu_data().getCommon_examples()) {

            if (common_example.getEntities() == null)
                continue;

            for (Entity entity : common_example.getEntities()) {
                String key = entity.getEntity();
                if (slotEntityMap.containsKey(key)) {
                    SlotEntity slotEntity = slotEntityMap.get(key);
                    slotEntity.addValue(entity.getValue());
                } else {
                    SlotEntity slotEntity = new SlotEntity();
                    slotEntity.setName(key);
                    slotEntity.addValue(entity.getValue());
                    slotEntityMap.put(key, slotEntity);
                }


            }

        }

        slotRepository.saveAll(slotEntityMap.values());

        rasaSynonymRepository.deleteAll();
        rasaSynonymRepository.saveAll(rasaDataRoot.getRasa_nlu_data().getEntity_synonyms());

    }


}
