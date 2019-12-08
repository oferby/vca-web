package com.huawei.vca.repository;

import com.huawei.vca.message.*;
import com.huawei.vca.repository.controller.DialogueRepository;
import com.huawei.vca.repository.controller.IntentRepository;
import com.huawei.vca.repository.entity.DialogueEntity;
import com.huawei.vca.repository.entity.IntentEntity;
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
    private ConversationGraphRepository conversationGraphRepository;

    @Autowired
    private ConversationGraphController conversationGraphController;

    @Autowired
    private IntentRepository intentRepository;

    @Autowired
    private DialogueRepository dialogueRepository;

    @Test
    public void testReset(){

        reset();
        RootNode rootNode = conversationGraphRepository.getRootNodeWithObservations();

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

        conversationGraphController.saveDialogueToGraph(dialogue);

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

        conversationGraphController.saveDialogueToGraph(dialogue);

        RootNode rootNode = conversationGraphRepository.getRootNodeWithObservations();

        assert rootNode != null;

    }


    @Test
    public void findAction(){

        ArrayList<String> optionList = new ArrayList<String>() {
            {
               add("beef");
               add("lamb");
            }};

        ActionNode action = conversationGraphRepository.findActionByIdAndOptions("What kind of meat would you like? These are the options:", optionList);

        assert action!=null;


    }


    @Test
    public void deleteAll(){

        reset();
    }


    private void reset(){

        conversationGraphRepository.deleteAll();

        RootNode rootNode = new RootNode();
        rootNode.setStringId("root");

        RootNode save = conversationGraphRepository.save(rootNode);

        Optional<GenericNode> byId = conversationGraphRepository.findById(save.getId());

        assert byId.isPresent();

    }


    @Test
    public void testSaveToGraphWithOptions() {

        Optional<DialogueEntity> optionalDialogueEntity = dialogueRepository.findById("dqqtbzxe");

        assert optionalDialogueEntity.isPresent();

        conversationGraphController.saveDialogueToGraph(optionalDialogueEntity.get().getDialogue());

    }

    @Test
    public void testMultipleProperties2(){

        ArrayList<String> propStringIdList = new ArrayList<String>() {
            {
                add("want:food:main_dish:burger");
            }
        };

        ObservationNode observationNode = conversationGraphRepository.findObservationByProperties(propStringIdList);

        assert observationNode!=null;


    }

    @Test
    public void findState() {


        ArrayList<String> properties = new ArrayList<String>() {
            {
                add("botAct:INFORM_USER");
                add("want:food:main_dish:burger");
            }
        };

        StateNode stateNode = conversationGraphRepository.findStateByPropertiesAndObservation(properties, (long) 12368769);

        assert stateNode!=null;


    }


}
