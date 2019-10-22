package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class OptionNode extends StateNode{

    public OptionNode(String id, String text) {
        this.stringId = id;
        this.name = text;
    }
}
