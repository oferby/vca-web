package com.huawei.vca.grpc;

import com.huawei.vca.intent.NluRequest;
import com.huawei.vca.intent.NluResponse;
import com.huawei.vca.intent.NluServiceGrpc;
import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

import javax.annotation.PostConstruct;

@Controller
public class NluServiceImpl implements NluService{

    @Value("${intent.grpc.server.ip}")
    private String serverIp;

    @Value("${intent.grpc.server.port}")
    private int serverPort;

    private NluServiceGrpc.NluServiceBlockingStub serviceBlockingStub;
    @Override
    public NluResponse getNluResponse(String text) {

        NluRequest nluRequest = NluRequest.newBuilder().setText(text).build();

        return serviceBlockingStub.getNluResponse(nluRequest);
    }

    @PostConstruct
    private void setup() {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(serverIp, serverPort)
                .usePlaintext()
                .build();

        serviceBlockingStub = NluServiceGrpc.newBlockingStub(channel);

    }
}
