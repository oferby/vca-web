package com.huawei.vca.repository;

import com.huawei.vca.repository.graph.ConversationRepository;
import com.huawei.vca.repository.graph.ObservationNode;
import com.huawei.vca.repository.graph.RootNode;
import com.huawei.vca.repository.graph.StateNode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.awt.datatransfer.Transferable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGraphDb {

    @Autowired
    private ConversationRepository conversationRepository;

    @Test
    public void testReset(){

        reset();
        addObservations();

        StateNode root = conversationRepository.getRootNode();

        assert root !=null;

        StateNode obs1 = conversationRepository.findObservationNodeByName("obs1");

        assert obs1 != null;

        obs1 = conversationRepository.findObservationNodeById(obs1.getId());

        assert obs1 != null;

    }


    @Test
    public void testFindByName() {

        String name = "obs1";

        ObservationNode observationNode = new ObservationNode();
        observationNode.setName(name);

        ObservationNode save = conversationRepository.save(observationNode);

        ObservationNode observationByName = conversationRepository.findObservationNodeByName(name);

        assert observationByName != null;

        conversationRepository.delete(save);

        observationByName = conversationRepository.findObservationNodeByName(name);

        assert observationByName == null;

//        reset();

    }


    @Test
    public void deleteAll(){

        reset();
    }

    @Test
    public void testProperties() {

        reset();
        addObservations();

        ObservationNode obs1 = conversationRepository.findObservationNodeByName("obs1");

        obs1.addProperty("k1", "v1");

        conversationRepository.save(obs1);

        obs1 = conversationRepository.findObservationNodeByName("obs1");

        assert obs1.getProperties() !=null && !obs1.getProperties().keySet().isEmpty();


    }

    private void reset(){

        conversationRepository.deleteAll();

        RootNode rootNode = new RootNode();
        rootNode.setName("root");

        RootNode save = conversationRepository.save(rootNode);

        Optional<StateNode> byId = conversationRepository.findById(save.getId());

        assert byId.isPresent();

    }


    public void addObservations() {

        RootNode rootNode = conversationRepository.getRootNode();

        for (int i = 0; i < 10; i++) {
            rootNode.addObservation(new ObservationNode("obs" + i));
        }

        conversationRepository.save(rootNode);
    }

}
