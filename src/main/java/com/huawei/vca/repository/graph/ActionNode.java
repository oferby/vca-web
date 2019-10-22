package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import javax.swing.plaf.nimbus.State;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NodeEntity
public class ActionNode extends HasObservationsNode implements Comparable<StateNode> {
    @Relationship(type = "OPTIONS")
    private List<OptionNode>optionNodeList;

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
    public int compareTo(StateNode stateNode) {
        return this.getStringId().compareTo(stateNode.getStringId());
    }
}
