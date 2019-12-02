package com.huawei.vca.conversation.policy;

import com.huawei.vca.message.BotAct;
import com.huawei.vca.message.BotDefaultUtterEvent;
import com.huawei.vca.message.BotEvent;
import com.huawei.vca.message.KnowledgeBaseQueryEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Map;

@Controller
public class SimplePolicyController implements PolicyController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BotAct getBotAct(Map<String, Float> userAction, Map<String, Float> state) {

        if (state.containsKey("botAct:DEFAULT"))
            return BotAct.DEFAULT;

        if (state.containsKey("botAct:QUERY_USER"))
            return BotAct.DB_SEARCH;

        return BotAct.DB_SEARCH;

    }
}
