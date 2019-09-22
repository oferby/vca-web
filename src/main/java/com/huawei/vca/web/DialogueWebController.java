package com.huawei.vca.web;

import com.huawei.vca.message.Dialogue;
import com.huawei.vca.conversation.SessionController;
import com.huawei.vca.message.DialogueSummary;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("data/dialogues")
public class DialogueWebController {

    @Autowired
    private SessionController sessionController;

    @GetMapping
    public Set<DialogueSummary> getSessionList(){
        return sessionController.getAllSessions();
    }

    @GetMapping("{id}")
    public Dialogue getDialogue(@PathVariable String id) {
        return sessionController.getDialogueBySessionId(id);
    }


}
