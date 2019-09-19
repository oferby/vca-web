package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NodeEntity
public class ObservationNode extends StateNode {

    @Relationship(type = "LEADS")
    private ActionNode actionNode;

    @Properties
    private Map<String,String>properties;

    public ObservationNode() {
    }

    public ObservationNode(String name) {
        super(name);
    }

    public ActionNode getActionNode() {
        return actionNode;
    }

    public void setActionNode(ActionNode actionNode) {
        this.actionNode = actionNode;
    }

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    public void addProperty(String key, String value) {
        if (properties == null) {
            properties = new HashMap<>();

        }

        properties.put(key, value);

    }
}
