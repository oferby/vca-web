package com.huawei.vca.repository.graph;

import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends Neo4jRepository<StateNode, Long> {

    @Query("MATCH (f:StateNode{name:'root'}) OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    RootNode getRootNode();

    @Query("MATCH (f:ObservationNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ObservationNode findObservationNodeById(Long id);

    @Query("MATCH (n:ObservationNode) RETURN n")
    List<ObservationNode>findAllObservationNodes();

    @Query("MATCH (f:ActionNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ActionNode findActionById(Long id);

    @Query("MATCH (f:StateNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    StateNode findStateNodeById(Long id);


}
