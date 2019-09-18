package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ConversationNode {

    private String name;

    private Integer actionId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getActionId() {
        return actionId;
    }

    public void setActionId(Integer actionId) {
        this.actionId = actionId;
    }
}
