package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

@NodeEntity
public class ObservationNode extends ConversationNode{

    @Relationship(type = "LEADS")
    private ActionNode actionNode;

    public ActionNode getActionNode() {
        return actionNode;
    }

    public void setActionNode(ActionNode actionNode) {
        this.actionNode = actionNode;
    }
}
