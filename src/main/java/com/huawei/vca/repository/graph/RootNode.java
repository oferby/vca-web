package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NodeEntity
public class RootNode extends GenericNode{

    @Id
    @GeneratedValue
    private
    Long id;

    private String stringId;

    @Relationship("HAS")
    private List<StateNode> stateNodeList;

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

    public List<StateNode> getStateNodeList() {
        return stateNodeList;
    }

    public void setStateNodeList(List<StateNode> stateNodeList) {
        this.stateNodeList = stateNodeList;
    }

    public void addStateNode(StateNode stateNode){
        if (stateNodeList == null)
            stateNodeList = new ArrayList<>();
        stateNodeList.add(stateNode);
    }

}
