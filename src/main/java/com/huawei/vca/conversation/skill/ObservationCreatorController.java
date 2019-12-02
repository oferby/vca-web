package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.Act;
import com.huawei.vca.message.Slot;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class ObservationCreatorController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void createObservations(Map<String,Float> observations, Set<Slot> slots, Act act){

        if (slots == null || slots.isEmpty()) {
            return;
        }

        String prefix;
        if (act == Act.DENY) {
            prefix = "dontwant";
        } else if (act == Act.AFFIRM || act == Act.INFORM) {
            prefix = "want";
        } else {
            throw new RuntimeException("wrong user act");
        }

        for (Slot slot : slots) {
            observations.put(prefix + ":" + slot.getKey() + ":" + slot.getValue(), slot.getConfidence());
        }

    }

    public Map<String,Float> createObservationsForState(Map<String,Float> observations) {

        Map<String,Float> observationState = new HashMap<>();
        for (String key : observations.keySet()) {
            if (key.startsWith("dontwant") || key.startsWith("want"))
                observationState.put(key, observations.get(key));
        }

        return observationState;

    }



}
