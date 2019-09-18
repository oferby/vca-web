package com.huawei.vca.conversation;

import com.huawei.vca.message.Dialogue;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Controller
public class SessionController {

    private Map<String, Dialogue>dialogueMap = new HashMap<>();

    public void addOrUpdateDialogue(String session, Dialogue dialogue){
        dialogueMap.put(session, dialogue);
    }

    public void removeDialogue(String session){
        dialogueMap.remove(session);
    }

    public Set<String> getAllSessions(){
        return dialogueMap.keySet();
    }

    public Dialogue getDialogueBySessionId(String sessionId){
        return dialogueMap.get(sessionId);
    }

}
