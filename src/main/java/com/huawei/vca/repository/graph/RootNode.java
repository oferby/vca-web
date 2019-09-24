package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RootNode)) return false;
        if (!super.equals(o)) return false;
        RootNode rootNode = (RootNode) o;
        return Objects.equals(getObservationNodes(), rootNode.getObservationNodes());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getObservationNodes());
    }
}
