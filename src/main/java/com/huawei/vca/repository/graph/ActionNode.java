package com.huawei.vca.repository.graph;

import org.jetbrains.annotations.NotNull;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.ArrayList;
import java.util.List;

@NodeEntity
public class ActionNode extends GenericNode implements Comparable<ActionNode> {

    @Id
    @GeneratedValue
    private
    Long id;

    private String stringId;

    @Relationship(type = "OPTIONS")
    private List<OptionNode>optionNodeList;



    public ActionNode(String stringId) {
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

    public List<OptionNode> getOptionNodeList() {
        return optionNodeList;
    }

    public void setOptionNodeList(List<OptionNode> optionNodeList) {
        this.optionNodeList = optionNodeList;
    }

    public void addOption(OptionNode optionNode) {
        if (optionNodeList == null)
            optionNodeList = new ArrayList<>();
        optionNodeList.add(optionNode);
    }

    @Override
    public int compareTo(@NotNull ActionNode o) {
        return o.getId().compareTo(this.id);
    }
}
