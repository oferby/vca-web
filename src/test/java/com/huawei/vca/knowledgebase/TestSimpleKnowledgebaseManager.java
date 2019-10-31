package com.huawei.vca.knowledgebase;

import com.huawei.vca.message.Slot;
import com.huawei.vca.repository.controller.DialogueRepository;
import com.huawei.vca.repository.entity.DialogueEntity;
import com.huawei.vca.repository.graph.ConversationGraphRepository;
import com.huawei.vca.repository.graph.RootNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSimpleKnowledgebaseManager {

    @Autowired
    private SimpleKnowledgebaseManager simpleKnowledgebaseManager ;

    @Autowired
    private ConversationGraphRepository conversationGraphRepository;

    @Test
    public void testFindingNextSlotToAsk() {


        List<Slot> informSlots = new ArrayList<>();
        Slot slot = new Slot("food:main_dish", "pasta");
        informSlots.add(slot);

        slot = new Slot("food:drink:hard", "wine");
        informSlots.add(slot);


        List<Slot> denySlots = new ArrayList<>();
        slot = new Slot("food:main_dish:pasta","ravioli");
        denySlots.add(slot);

        slot = new Slot("food:main_dish:pasta","penne");
        denySlots.add(slot);

        GoalPrediction goalPrediction = simpleKnowledgebaseManager.getGoalPrediction(informSlots, denySlots);

        assert goalPrediction != null;

    }
}
