package com.huawei.vca.conversation.policy;

import com.huawei.vca.message.BotAct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.Map;
import java.util.Set;

@Controller
public class SimplePolicyController implements PolicyController{

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public BotAct getBotAct(Map<String, Float> userAction, Map<String, Float> state) {



        if (userAction.containsKey("userAct:DENY") || userAction.containsKey("userAct:INFORM")) {

            if (state.containsKey("botAct:DEFAULT"))
                return BotAct.DEFAULT;

            if (state.containsKey("botAct:QUERY_USER"))
                return BotAct.DB_SEARCH;

            return BotAct.DB_SEARCH;

        }

        if (userAction.containsKey("userAct:QUERY")) {

            if (!searchKey(state.keySet(), "temp.answer.found",false)){
                return BotAct.SEARCH_ANSWER;
            }

            if (state.get("temp.answer.found") == 1.0) {
                return BotAct.INFORM_USER;
            }

        }

        return BotAct.DEFAULT;

    }

    private boolean searchKey(Set<String> keys, String keyToSearch, boolean withRemove){

        for (String key : keys) {
            if (key.startsWith(keyToSearch)){
                if (withRemove)
                    keys.remove(key);

                return true;
            }
        }

        return false;

    }


}
