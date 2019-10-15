package com.huawei.vca.conversation.skill;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.PredictedAction;

public interface SkillController {

    PredictedAction getPredictedAction(Dialogue dialogue);


}
