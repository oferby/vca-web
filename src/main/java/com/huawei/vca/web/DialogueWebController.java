package com.huawei.vca.web;

import com.huawei.vca.message.*;
import com.huawei.vca.conversation.SessionController;
import com.huawei.vca.repository.entity.DialogueEntity;
import com.huawei.vca.repository.controller.DialogueRepository;
import com.huawei.vca.repository.graph.ConversationGraphController;
import com.huawei.vca.repository.graph.ConversationGraphRepository;
import com.huawei.vca.repository.nlu.EntityExample;
import com.huawei.vca.repository.nlu.IntentExample;
import com.huawei.vca.repository.nlu.IntentExampleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.concurrent.ExecutorService;

@RestController
@CrossOrigin(origins = {"http://localhost:9527", "http://http://10.100.100.61:9527"})
@RequestMapping("data/dialogues")
public class DialogueWebController {

    private static final Logger logger = LoggerFactory.getLogger(DialogueWebController.class);

    @Autowired
    private SessionController sessionController;


    @Autowired
    private DialogueRepository dialogueRepository;

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private ConversationGraphController conversationGraphController;

    @Autowired
    private ConversationGraphRepository conversationGraphRepository;

    @Autowired
    private IntentExampleRepository intentExampleRepository;

    @GetMapping
    public Set<DialogueSummary> getSessionList(){
        return sessionController.getAllSessions();
    }

    @GetMapping("{id}")
    public Dialogue getDialogue(@PathVariable String id) {
        return sessionController.getDialogueBySessionId(id);
    }

    @GetMapping("editor/{id}")
    public Optional<DialogueEntity> getDialogueForEditById(@PathVariable String id){
        return dialogueRepository.findById(id);
    }

    @GetMapping("editor/all")
    public List<Dialogue> getAllDialogues(){
        List<DialogueEntity> entityList = dialogueRepository.findAll();

        if (entityList.size() == 0) {
            return new ArrayList<>();
        }

        List<Dialogue>dialogueList = new ArrayList<>();
        for (DialogueEntity dialogueEntity : entityList) {
            dialogueList.add(dialogueEntity.getDialogue());
        }

        return dialogueList;
    }

    @GetMapping("editor")
    public List<String> getSessionIdList(){

        List<String> sessionList = new ArrayList<>();

        List<DialogueEntity> dialogueEntities = dialogueRepository.findAll();
        for (DialogueEntity dialogueEntity : dialogueEntities) {
            sessionList.add(dialogueEntity.getId());
        }

        return sessionList;

    }

    @PutMapping("editor")
    public void updateDialogue(@RequestBody Dialogue dialogue) {

        executorService.execute(() -> {

            Optional<DialogueEntity> entityOptional = dialogueRepository.findById(dialogue.getSessionId());
            if (entityOptional.isPresent()) {
                DialogueEntity dialogueEntity = entityOptional.get();
                dialogueEntity.setDialogue(dialogue);
                dialogueRepository.save(dialogueEntity);
                logger.debug("dialogue " + dialogue.getSessionId() + " saved.");
            }

        });

    }

    @DeleteMapping("editor/{id}")
    public void deleteDialogueFromDb(@PathVariable String id) {

        executorService.execute(() -> {
            dialogueRepository.deleteById(id);
        });

    }

    @DeleteMapping("editor/all")
    public void deleteAllDialoguesFromDb(){
        executorService.execute(()-> {
            dialogueRepository.deleteAll();
        });
    }

    @PostMapping("editor/to/map")
    public void saveDialogue(@RequestBody Dialogue dialogue) {

        logger.debug("saving dialogue to graph");
        conversationGraphController.saveDialogueToGraph(dialogue);
        logger.debug("dialogue saved to graph");
    }

    @DeleteMapping("graph/node/{id}")
    public void deleteNodeFromGraph(@PathVariable  String id){
        logger.debug("deleting from graph node id: " + id);
        conversationGraphRepository.deletePathStartingWithId(Long.valueOf(id));
    }


    @GetMapping("nlu")
    public Set<UserUtterEvent> getSavedUserUtters(){

        Set<UserUtterEvent>userUtterEvents = new HashSet<>();

        List<DialogueEntity> dialogueEntityList = dialogueRepository.findAll();
        for (DialogueEntity dialogueEntity : dialogueEntityList) {

            if (dialogueEntity.getDialogue().getHistory() == null)
                continue;

            for (Event event : dialogueEntity.getDialogue().getHistory()) {
               if (event instanceof UserUtterEvent)
                   userUtterEvents.add((UserUtterEvent) event);
            }

        }
        return userUtterEvents;
    }

    @PostMapping("nlu")
    public void updateNlu(@RequestBody Set<UserUtterEvent> userUtterEvents){

        List<IntentExample>intentExamples=new ArrayList<>();
        for (UserUtterEvent userUtterEvent : userUtterEvents) {
            IntentExample example = new IntentExample();
            example.setText(userUtterEvent.getText());
            example.setIntent(userUtterEvent.getNluEvent().getBestIntent().getIntent());

            Set<Slot> slots = userUtterEvent.getNluEvent().getSlots();
            if (slots !=null) {

                for (Slot slot : slots) {

                    EntityExample entityExample = new EntityExample();
                    entityExample.setEntity(slot.getKey());
                    entityExample.setValue(slot.getValue());
                    entityExample.setStart(slot.getStart());
                    entityExample.setEnd(slot.getEnd());

                    example.addEntity(entityExample);

                }

            }

            intentExamples.add(example);
        }

        intentExampleRepository.saveAll(intentExamples);

    }

    @GetMapping("nlu/example")
    public List<IntentExample>getIntentExamples(){
        return intentExampleRepository.findAll();
    }

    @DeleteMapping("nlu/example")
    public void deleteAllExamples(){
        intentExampleRepository.deleteAll();
    }

}
