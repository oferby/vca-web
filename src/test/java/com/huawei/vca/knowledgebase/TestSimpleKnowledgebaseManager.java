package com.huawei.vca.knowledgebase;

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




    }
}
