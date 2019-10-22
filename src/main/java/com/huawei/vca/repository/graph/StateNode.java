package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import java.util.Objects;

@NodeEntity
public class StateNode {

    @Id
    @GeneratedValue
    private
    Long id;

    String stringId;

    String name;

    private Integer stateId;


    public StateNode() {
    }

    public StateNode(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStringId() {
        return stringId;
    }

    public void setStringId(String stringId) {
        this.stringId = stringId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStateId() {
        return stateId;
    }

    public void setStateId(Integer stateId) {
        this.stateId = stateId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateNode stateNode = (StateNode) o;
        return Objects.equals(id, stateNode.id) &&
                Objects.equals(stringId, stateNode.stringId) &&
                Objects.equals(name, stateNode.name) &&
                Objects.equals(stateId, stateNode.stateId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, stringId, name, stateId);
    }
}
