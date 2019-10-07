package com.huawei.vca.repository.nlu;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.vca.repository.nlu.rasa.RasaDataRoot;
import com.huawei.vca.repository.nlu.rasa.RasaExampleRepository;
import com.huawei.vca.repository.nlu.rasa.RasaSynonymRepository;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestLoadIntents {

    @Autowired
    private RasaExampleRepository rasaExampleRepository;

    @Autowired
    private RasaSynonymRepository rasaSynonymRepository;

//    @Test
    public void load() throws IOException {

        // read json and write to db

        String filename = "/dev/nlu_data.json";

        ObjectMapper mapper = new ObjectMapper();

        RasaDataRoot rasaDataRoot = mapper.readValue(new File(filename), RasaDataRoot.class);

        assert rasaDataRoot != null;

        rasaExampleRepository.saveAll(rasaDataRoot.getRasa_nlu_data().getCommon_examples());

        rasaSynonymRepository.saveAll(rasaDataRoot.getRasa_nlu_data().getEntity_synonyms());

    }


}
