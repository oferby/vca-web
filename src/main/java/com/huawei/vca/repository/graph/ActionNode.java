package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

@NodeEntity
public class ActionNode extends ConversationNode {

    @Id
    @GeneratedValue
    private
    Long id;

    @Relationship(type = "LEADS")
    private List<ObservationNode> observationNodes;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ObservationNode> getObservationNodes() {
        return observationNodes;
    }

    public void setObservationNodes(List<ObservationNode> observationNodes) {
        this.observationNodes = observationNodes;
    }

}
