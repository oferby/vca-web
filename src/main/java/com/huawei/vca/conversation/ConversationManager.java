package com.huawei.vca.conversation;


import com.huawei.vca.message.Dialogue;

public interface ConversationManager {

    Dialogue handleDialogue(Dialogue dialogue);

    Dialogue handleNluOnly(Dialogue dialogue);

}
