package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.PredictedAction;

import java.util.Map;

public interface SkillController {

    PredictedAction getPredictedAction(Map<String, Float> state, Map<String, Float> observations);

}
