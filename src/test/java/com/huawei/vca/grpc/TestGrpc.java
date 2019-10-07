package com.huawei.vca.grpc;

import com.huawei.vca.intent.NluResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGrpc {

    @Autowired
    private NluService nluService;

    @Autowired
    private RasaNluServiceImpl rasaNluService;

//    @Test
    public void testNluService() {

        String text = "I would like to have lunch";

        NluResponse nluResponse = nluService.getNluResponse(text);

        System.out.println(nluResponse);

    }

//    @Test
    public void testRasaNlu() {

        rasaNluService.saveExamplesToRasaNlu();

    }

}
