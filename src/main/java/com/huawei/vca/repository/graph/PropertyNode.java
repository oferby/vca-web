package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

@NodeEntity
public class PropertyNode extends GenericNode {

    @Id
    @GeneratedValue
    private
    Long id;

    private String stringId;

    private PropertyType propertyType;


    public PropertyNode() {
    }

    public PropertyNode(String stringId) {
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

    public PropertyType getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(PropertyType propertyType) {
        this.propertyType = propertyType;
    }
}
