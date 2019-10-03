package com.huawei.vca.web;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.conversation.SessionController;
import com.huawei.vca.message.DialogueSummary;
import com.huawei.vca.repository.DialogueEntity;
import com.huawei.vca.repository.controller.DialogueRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@RestController
@RequestMapping("data/dialogues")
public class DialogueWebController {

    private static final Logger logger = LoggerFactory.getLogger(DialogueWebController.class);

    @Autowired
    private SessionController sessionController;


    @Autowired
    private DialogueRepository dialogueRepository;

    @Autowired
    private ExecutorService executorService;

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

    @GetMapping("editor")
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

}
