package com.huawei.vca.conversation;

import com.huawei.vca.message.Dialogue;

public interface DialogueActionController {

    void addActionToDialogue(Dialogue dialogue, Class skill);

}
