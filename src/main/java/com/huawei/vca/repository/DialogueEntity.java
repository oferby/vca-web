package com.huawei.vca.repository;

import com.huawei.vca.message.Dialogue;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "dialogues")
public class DialogueEntity {

    @Id
    private String id;

    private Dialogue dialogue;

    public DialogueEntity() {
    }

    public DialogueEntity(Dialogue dialogue) {
        this.dialogue = dialogue;
        this.id = dialogue.getSessionId();
    }

    public String getId() {
        return id;
    }

    public Dialogue getDialogue() {
        return dialogue;
    }

    public void setDialogue(Dialogue dialogue) {
        this.dialogue = dialogue;
    }
}
