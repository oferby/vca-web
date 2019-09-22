package com.huawei.vca.conversation;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.message.DialogueSummary;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Controller
public class SessionController {

    private Map<String, Dialogue> dialogueMap = new HashMap<>();

    public void addOrUpdateDialogue(String session, Dialogue dialogue) {
        dialogueMap.put(session, dialogue);
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
        }

        return dialogueSummaries;
    }

    public Dialogue getDialogueBySessionId(String sessionId) {
        return dialogueMap.get(sessionId);
    }

}
