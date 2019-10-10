package com.huawei.vca.knowledgebase;

import com.huawei.vca.message.MemoryState;
import com.huawei.vca.repository.controller.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class SimpleKnowledgebaseManager implements KnowledgebaseManager{

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Override
    public GoalPrediction getGoalPrediction(MemoryState memoryState) {

        GoalPrediction goalPrediction = new GoalPrediction();




        return goalPrediction;
    }
}
