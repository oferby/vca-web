package com.huawei.vca.knowledgebase;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.MemoryState;

public interface KnowledgebaseManager {

    GoalPrediction findUserGoal(Dialogue dialogue);

}
