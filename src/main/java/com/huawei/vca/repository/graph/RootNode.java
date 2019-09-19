package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class RootNode extends StateNode {

    @Relationship(type = "LEADS")
    private List<ObservationNode> observationNodes;

    public List<ObservationNode> getObservationNodes() {
        return observationNodes;
    }

    public void setObservationNodes(List<ObservationNode> observationNodes) {
        this.observationNodes = observationNodes;
    }

    public void addObservation(ObservationNode observationNode) {
        if (observationNodes == null) {
            observationNodes = new ArrayList<>();
        }

        observationNodes.add(observationNode);
    }


}
