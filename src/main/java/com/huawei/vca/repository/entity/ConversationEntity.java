package com.huawei.vca.repository.entity;

import com.huawei.vca.repository.ConversationTurn;
import com.huawei.vca.repository.Turn;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "conversations")
public class ConversationEntity {

    @Id
    private String userId;

    private List<ConversationTurn>conversationTurns;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<ConversationTurn> getConversationTurns() {
        return conversationTurns;
    }

    public void setConversationTurns(List<ConversationTurn> conversationTurns) {
        this.conversationTurns = conversationTurns;
    }

    public void addConversationTurn(Turn turn, String text){

        if (conversationTurns == null) {
            conversationTurns = new ArrayList<>();
        }

        conversationTurns.add(new ConversationTurn(turn, text));

    }

}
