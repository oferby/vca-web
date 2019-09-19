package com.huawei.vca.repository.graph;

import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends Neo4jRepository<StateNode, Long> {

    @Query("MATCH (f:StateNode{name:'root'}) OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    RootNode getRootNode();

    @Query("MATCH (f:ObservationNode{name:{0}}) OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ObservationNode findObservationNodeByName(String name);

    @Query("MATCH (f:ObservationNode) WHERE ID(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ObservationNode findObservationNodeById(Long id);

    @Query("MATCH (f:ActionNode{name:{0}}) OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ActionNode findActionNodeByName(String name);

    @Query("MATCH (f:ActionNode{id:{0}}) OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ActionNode findActionNodeById(Long id);
}
