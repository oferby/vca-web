package com.huawei.vca.knowledgebase;

import com.huawei.vca.message.BotAct;
import com.huawei.vca.message.PredictedAction;

import java.util.Map;

public interface KnowledgeBaseController {

    PredictedAction getPredictedAct(Map<String,Float> state);

}
