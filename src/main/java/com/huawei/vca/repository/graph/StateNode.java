package com.huawei.vca.repository.graph;

import org.jetbrains.annotations.NotNull;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class StateNode extends GenericNode{

    @Id
    @GeneratedValue
    private
    Long id;

    private String stringId;

    @Relationship(type = "LEADS")
    private ActionNode actionNode;

    @Relationship(type = "HAS_OBSERVATION")
    private ObservationNode observationNode;

    @Relationship(type = "OBSERVE")
    private List<PropertyNode> stateProperties;

    private Integer visited;


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

    public ActionNode getActionNode() {
        return actionNode;
    }

    public void setActionNode(ActionNode actionNode) {
        this.actionNode = actionNode;
    }

    public ObservationNode getObservationNode() {
        return observationNode;
    }

    public void setObservationNode(ObservationNode observationNode) {
        this.observationNode = observationNode;
    }

    public List<PropertyNode> getStateProperties() {
        return stateProperties;
    }

    public void setStateProperties(List<PropertyNode> stateProperties) {
        this.stateProperties = stateProperties;
    }

    public Integer getVisited() {
        return visited;
    }

    public void setVisited(Integer visited) {
        this.visited = visited;
    }

    public void increaseVisited() {

        if (this.visited == null)
            visited = 0;

        this.visited++;
    }




}
