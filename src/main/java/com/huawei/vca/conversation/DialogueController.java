package com.huawei.vca.conversation;

import com.huawei.vca.message.Dialogue;

public interface DialogueController {

    void addActionToDialogue(Dialogue dialogue, Class skill);

}
