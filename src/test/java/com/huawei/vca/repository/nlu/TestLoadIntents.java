package com.huawei.vca.repository.nlu;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.huawei.vca.utils.RasaDataRoot;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@RunWith(SpringRunner.class)
//@SpringBootTest
public class TestLoadIntents {

    @Test
    public void load() throws IOException {

        // read json and write to db

        String filename = "/dev/nlu_data.json";

        ObjectMapper mapper = new ObjectMapper();

        RasaDataRoot rasaDataRoot = mapper.readValue(new File(filename), RasaDataRoot.class);

        assert rasaDataRoot != null;


    }


}
