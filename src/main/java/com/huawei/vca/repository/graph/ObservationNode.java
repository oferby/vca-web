package com.huawei.vca.repository.graph;

import org.jetbrains.annotations.NotNull;
import org.neo4j.ogm.annotation.*;

import java.util.*;

@NodeEntity
public class ObservationNode extends GenericNode implements Comparable<ObservationNode>{

    @Id
    @GeneratedValue
    private
    Long id;

    private String stringId;

    @Relationship(type = "OBSERVE")
    private List<PropertyNode> observationProperties;


    public ObservationNode() {
    }

    public ObservationNode(String stringId){
        this.stringId = stringId;
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

    public List<PropertyNode> getObservationProperties() {
        return observationProperties;
    }

    public void setObservationProperties(List<PropertyNode> observationProperties) {
        this.observationProperties = observationProperties;
    }

    public void addObservationProperty(PropertyNode propertyNode){
        if (observationProperties ==null)
            observationProperties = new ArrayList<>();
        observationProperties.add(propertyNode);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ObservationNode that = (ObservationNode) o;
        return Objects.equals(observationProperties, that.observationProperties);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), observationProperties);
    }

    @Override
    public int compareTo(@NotNull ObservationNode o) {
        return this.id.compareTo(o.getId());
    }
}
