package com.huawei.vca.conversation;


import com.huawei.vca.message.Dialogue;

public interface ConversationStateTracker {

    Dialogue handleDialogue(Dialogue dialogue);

    Dialogue handleNluOnly(Dialogue dialogue);

    void addActionToDialogue(Dialogue dialogue);

}
