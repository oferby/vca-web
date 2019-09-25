package com.huawei.vca.repository;

import com.huawei.vca.message.*;
import com.huawei.vca.repository.controller.IntentRepository;
import com.huawei.vca.repository.graph.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestGraphDb {

    @Autowired
    private ConversationRepository conversationRepository;

    @Autowired
    private ConversationRepositoryController conversationRepositoryController;

    @Autowired
    private IntentRepository intentRepository;

    @Test
    public void testReset(){

        reset();
        RootNode rootNode = conversationRepository.getRootNode();

        assert rootNode !=null;

    }


    @Test
    public void testFindConnected() {

        reset();

        List<IntentEntity> intentEntityList = intentRepository.findAll();

        Set<Slot>slots = new HashSet<>();
        Slot slot;
        for (int i = 0; i < 30; i++) {
            slot = new Slot();
            slot.setKey("key" + i);
            slot.setValue("value" + i);
            slot.setConfidence((float) 0.8);

            slots.add(slot);
        }
        Iterator<Slot> slotIterator = slots.iterator();

        Dialogue dialogue = new Dialogue();

        for (int i = 0; i < 10; i++) {

            UserUtterEvent userUtterEvent = new UserUtterEvent("obs" + i);
            dialogue.addToHistory(userUtterEvent);

            NluEvent nluEvent = new NluEvent();

            if (i % 2 == 0) {
                nluEvent.addSlot(slotIterator.next());
                nluEvent.addSlot(slotIterator.next());
            }

            IntentEntity intentEntity = intentEntityList.get(i);
            Intent intent = new Intent(intentEntity.getIntent(), (float) 0.9);
            nluEvent.setBestIntent(intent);
            userUtterEvent.setNluEvent(nluEvent);

            BotUtterEvent botUtterEvent = new BotUtterEvent("action" + i);
            botUtterEvent.setId("action_id" + i);
            dialogue.addToHistory(botUtterEvent);

        }

        conversationRepositoryController.saveDialogueToGraph(dialogue);

        dialogue = new Dialogue();
        slotIterator = slots.iterator();

        for (int i = 0; i < 5; i++) {

            UserUtterEvent userUtterEvent = new UserUtterEvent("obs" + i);
            dialogue.addToHistory(userUtterEvent);

            NluEvent nluEvent = new NluEvent();
            if (i % 3 == 0) {
                nluEvent.addSlot(slotIterator.next());
                nluEvent.addSlot(slotIterator.next());
            }

            IntentEntity intentEntity = intentEntityList.get(i);
            Intent intent = new Intent(intentEntity.getIntent(), (float) 0.9);
            nluEvent.setBestIntent(intent);
            userUtterEvent.setNluEvent(nluEvent);

            BotUtterEvent botUtterEvent = new BotUtterEvent("action" + i);
            botUtterEvent.setId("action_id" + i);
            dialogue.addToHistory(botUtterEvent);

        }

        for (int i = 10; i < 15; i++) {

            UserUtterEvent userUtterEvent = new UserUtterEvent("obs" + i);
            dialogue.addToHistory(userUtterEvent);

            NluEvent nluEvent = new NluEvent();

            if (i % 3 == 0) {
                nluEvent.addSlot(slotIterator.next());
                nluEvent.addSlot(slotIterator.next());
            }

            IntentEntity intentEntity = intentEntityList.get(i);
            Intent intent = new Intent(intentEntity.getIntent(), (float) 0.9);
            nluEvent.setBestIntent(intent);
            userUtterEvent.setNluEvent(nluEvent);

            BotUtterEvent botUtterEvent = new BotUtterEvent("action" + i);
            botUtterEvent.setId("action_id" + i);
            dialogue.addToHistory(botUtterEvent);

        }

        conversationRepositoryController.saveDialogueToGraph(dialogue);

        RootNode rootNode = conversationRepository.getRootNode();

        assert rootNode != null;

    }


    @Test
    public void testSet() {

        Set<ObservationNode>nodes = new HashSet<>();

        ObservationNode node = new ObservationNode("text");

        nodes.add(node);

        node.setActionNode(new ActionNode());

        nodes.add(node);

        assert nodes.size() == 1;



    }


    @Test
    public void getNodeById() {

        Iterable<StateNode> nodes = conversationRepository.findAll();

        StateNode node = nodes.iterator().next();

        Optional<StateNode> byId = conversationRepository.findById(node.getId());

        assert byId.isPresent();

        List<ObservationNode> allObservationNodes = conversationRepository.findAllObservationNodes();

        ObservationNode observationNode = allObservationNodes.get(0);

        ObservationNode observationNodeById = conversationRepository.findObservationNodeById(observationNode.getId());

        assert observationNodeById != null;

    }





    @Test
    public void deleteAll(){

        reset();
    }

    @Test
    public void testProperties() {

        reset();
        RootNode rootNode = conversationRepository.getRootNode();
        addObservations(rootNode);

//        ObservationNode obs1 = conversationRepository.findObservationNodeByName("obs1");
//
//        obs1.addProperty("k1", "v1");
//
//        conversationRepository.save(obs1);
//
//        obs1 = conversationRepository.findObservationNodeByName("obs1");
//
//        assert obs1.getProperties() !=null && !obs1.getProperties().keySet().isEmpty();


    }

    private void reset(){

        conversationRepository.deleteAll();

        RootNode rootNode = new RootNode();
        rootNode.setName("root");
        rootNode.setStringId("root");

        RootNode save = conversationRepository.save(rootNode);

        Optional<StateNode> byId = conversationRepository.findById(save.getId());

        assert byId.isPresent();

    }


    public void addObservations(StateNode from) {

        RootNode rootNode = conversationRepository.getRootNode();

        for (int i = 0; i < 10; i++) {
            rootNode.addObservation(new ObservationNode("obs" + i));
        }

        conversationRepository.save(rootNode);
    }

}
