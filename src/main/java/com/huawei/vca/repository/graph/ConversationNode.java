package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class ConversationNode {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
