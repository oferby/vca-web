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

    private String name;

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
        if (!(o instanceof StateNode)) return false;
        StateNode stateNode = (StateNode) o;
        return Objects.equals(getId(), stateNode.getId()) &&
                Objects.equals(getName(), stateNode.getName()) &&
                Objects.equals(getStateId(), stateNode.getStateId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getStateId());
    }

}
