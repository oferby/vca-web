package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.Properties;

import java.util.*;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObservationNode)) return false;
        if (!super.equals(o)) return false;
        ObservationNode that = (ObservationNode) o;
        return Objects.equals(getActionNode(), that.getActionNode()) &&
                Objects.equals(getProperties(), that.getProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getActionNode(), getProperties());
    }
}
