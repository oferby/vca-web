package com.huawei.vca.grpc;


import com.huawei.vca.nlu.intent.example.*;
import com.huawei.vca.repository.nlu.IntentExample;
import com.huawei.vca.repository.nlu.IntentExampleRepository;
import com.huawei.vca.repository.nlu.SynonymRepository;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
public class RasaNluServiceImpl {

    @Value("${intent.grpc.server.ip}")
    private String serverIp;

    @Value("${intent.grpc.server.port}")
    private int serverPort;

    @Autowired
    private IntentExampleRepository intentExampleRepository;

    @Autowired
    private SynonymRepository synonymRepository;

    private RasaNluServiceGrpc.RasaNluServiceBlockingStub serviceBlockingStub;

    public boolean saveExamplesToRasaNlu() {

        List<IntentExample> exampleList = intentExampleRepository.findAll();

        RasaData.Builder rasaBuilder = RasaData.newBuilder();

        for (IntentExample intentExample : exampleList) {
            CommonExample.Builder builder = CommonExample.newBuilder();
            builder.setIntent(intentExample.getIntent());
            builder.setText(intentExample.getIntent());
            CommonExample commonExample = builder.build();

            rasaBuilder.addCommonExamples(commonExample);
        }

        RasaData rasaData = rasaBuilder.build();

        RasaNluRequest.Builder nluBuilder = RasaNluRequest.newBuilder();
        RasaNluRequest rasaNluRequest = nluBuilder.setRasaNluData(rasaData).build();

        RasaNluResponse rasaNluResponse = serviceBlockingStub.saveToRasaNlu(rasaNluRequest);

        return rasaNluResponse != null;

    }
    @PostConstruct
    private void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIp, serverPort)
                .usePlaintext()
                .build();

        serviceBlockingStub = RasaNluServiceGrpc.newBlockingStub(channel);
    }

}
