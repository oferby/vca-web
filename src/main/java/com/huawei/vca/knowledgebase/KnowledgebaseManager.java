package com.huawei.vca.knowledgebase;

import com.huawei.vca.message.MemoryState;

public interface KnowledgebaseManager {

    GoalPrediction getGoalPrediction(MemoryState memoryState);


}
