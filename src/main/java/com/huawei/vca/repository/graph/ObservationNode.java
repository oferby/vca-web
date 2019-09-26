package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.*;
import org.neo4j.ogm.annotation.Properties;

import java.util.*;

@NodeEntity
public class ObservationNode extends StateNode implements Comparable<StateNode>{

    @Relationship(type = "LEADS")
    private ActionNode actionNode;

    @Properties
    private Map<String,String>properties;

    private Integer visited;

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

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public void increaseVisited() {

        if (this.visited == null)
            visited = 0;

        this.visited++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObservationNode that = (ObservationNode) o;
        return Objects.equals(actionNode, that.actionNode) &&
                Objects.equals(properties, that.properties) &&
                Objects.equals(visited, that.visited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), actionNode, properties, visited);
    }

    @Override
    public int compareTo(StateNode stateNode) {
        return this.getStringId().compareTo(stateNode.getStringId());
    }
}
