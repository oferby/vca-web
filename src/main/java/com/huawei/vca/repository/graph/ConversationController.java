package com.huawei.vca.repository.graph;

import java.util.List;

public interface ConversationController {

    RootNode getRootNode();

    List<StateNode> getOneDeptById(String id);

    List<ActionNode> getActionNodesForObservationId(String id);

    List<ObservationNode> getObservationNodesForObservationId(String id);

}
