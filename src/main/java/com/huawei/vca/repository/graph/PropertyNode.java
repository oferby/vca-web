package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class PropertyNode extends StateNode {

    private String key;
    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public static PropertyNode getInstance(String key, String value) {
        PropertyNode propertyNode = new PropertyNode();
        propertyNode.key = key;
        propertyNode.value = value;
        return propertyNode;
    }
}
