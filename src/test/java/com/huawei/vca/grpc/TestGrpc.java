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

    @Test
    public void testNluService() {

        String text = "this is a test!";

        NluResponse nluResponse = nluService.getNluResponse(text);

        assert nluResponse.getIntent().getName().contains(text);

    }

}
