package com.huawei.vca.conversation;

import com.huawei.vca.message.Confidence;
import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.DialogueSummary;
import com.huawei.vca.repository.entity.DialogueEntity;
import com.huawei.vca.repository.controller.DialogueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Controller
public class SessionController {

    @Autowired
    private ExecutorService executorService;

    @Autowired
    private DialogueRepository dialogueRepository;

    private Map<String, Dialogue> dialogueMap = new HashMap<>();

    public void addOrUpdateDialogue(String session, Dialogue dialogue) {
        dialogueMap.put(session, dialogue);
        this.saveToDb(dialogue);
    }

    public void removeDialogue(String session) {
        dialogueMap.remove(session);
    }

    public Set<DialogueSummary> getAllSessions() {

        Set<DialogueSummary> dialogueSummaries = new HashSet<>();
        for (Dialogue dialogue : dialogueMap.values()) {

            DialogueSummary dialogueSummary = new DialogueSummary();
            dialogueSummary.setId(dialogue.getSessionId());


            dialogueSummaries.add(dialogueSummary);

            if (dialogue.getLastNluEvent() == null) {
                continue;
            }
            dialogueSummary.setConfidence(dialogue.getLastNluEvent().getBestIntent().getConfidence());

            if (dialogue.isTraining()) {
                dialogueSummary.setConfidenceString(Confidence.TRAIN);
            }
        }

        return dialogueSummaries;
    }

    public Dialogue getDialogueBySessionId(String sessionId) {
        return dialogueMap.get(sessionId);
    }

    private void saveToDb(Dialogue dialogue) {
        dialogueRepository.save(new DialogueEntity(dialogue));
    }

}
