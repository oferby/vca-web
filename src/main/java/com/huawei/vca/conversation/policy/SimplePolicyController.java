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

        logger.debug("state: " + state +" userAction: " + userAction);

        if (userAction.containsKey("userAct:DENY") || userAction.containsKey("userAct:INFORM") ) {

            if (state.containsKey("botAct:DEFAULT"))
                return BotAct.DEFAULT;

            if (state.containsKey("botAct:QUERY_USER"))
                return BotAct.DB_SEARCH;

            if (state.containsKey("botAct:DB_SEARCH")){

                if (state.get("temp.answer.found") == 1.0) {

                    return BotAct.INFORM_USER;

                } else {

                    return BotAct.DEFAULT;
                }

            }

            if (state.containsKey("botAct:SEARCH_HISTORY")) {

                if (state.get("temp.history.found") == 1.0) {
                    return BotAct.INFORM_USER;
                }

                return BotAct.DB_SEARCH;

            }

            return BotAct.SEARCH_HISTORY;

        }

        if (userAction.containsKey("userAct:QUERY")) {

            if (!state.containsKey("temp.answer.found")){
                return BotAct.SEARCH_QUERY;
            }

            if (state.get("temp.answer.found") == 1.0) {
                return BotAct.INFORM_USER;
            }

        }

        return BotAct.DEFAULT;

    }

}
