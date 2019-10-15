package com.huawei.vca.state;

import com.huawei.vca.conversation.ConversationStateTracker;
import com.huawei.vca.message.Dialogue;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestStateTracker {

    @Autowired
    private ConversationStateTracker conversationStateTracker;

    @Test
    public void testTracker(){

        Dialogue dialogue = new Dialogue();
        dialogue = conversationStateTracker.handleDialogue(dialogue);

        assert dialogue != null;

    }



}
