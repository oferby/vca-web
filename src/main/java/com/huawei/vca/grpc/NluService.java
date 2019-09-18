package com.huawei.vca.grpc;

import com.huawei.vca.intent.NluRequest;
import com.huawei.vca.intent.NluResponse;

public interface NluService {

    NluResponse getNluResponse(String text);

}
