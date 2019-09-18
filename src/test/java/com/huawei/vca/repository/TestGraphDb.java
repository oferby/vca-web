package com.huawei.vca.repository;

import com.huawei.vca.repository.graph.ConversationRepository;
import com.huawei.vca.repository.graph.RootNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGraphDb {

    @Autowired
    private ConversationRepository conversationRepository;

    @Test
    public void testCRUD(){

        conversationRepository.deleteAll();

        RootNode rootNode = new RootNode();
        rootNode.setName("root");

        RootNode save = conversationRepository.save(rootNode);

        Optional<RootNode> byId = conversationRepository.findById(save.getId());

        assert byId.isPresent();


    }

}
