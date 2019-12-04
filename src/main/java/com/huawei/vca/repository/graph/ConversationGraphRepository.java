package com.huawei.vca.repository.graph;

import org.springframework.data.neo4j.annotation.Depth;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public interface ConversationGraphRepository extends Neo4jRepository<StateNode, Long> {

    @Query("MATCH (f:StateNode{name:'root'}) OPTIONAL MATCH (f)-[l:LEADS]->(to) OPTIONAL MATCH (to)-[hp:HAS_PROPERTY]->(prop) RETURN f, collect(l), collect(to), collect(hp), collect(prop)")
    RootNode getRootNode();

    @Query("MATCH (o:ObservationNode)-[h:HAS_PROPERTY]->(p:PropertyNode) where p.stringId in {propStringIdList} with o,collect(p) as colp, collect(h) as colh where size(colp) = {propertySize} return o, colp, colh")
    ObservationNode findByProperties(List<String> propStringIdList, int propertySize);




    @Query("MATCH (f:ObservationNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ObservationNode findObservationNodeById(Long id);

    @Query("MATCH (n:ObservationNode) RETURN n")
    List<ObservationNode>findAllObservationNodes();

    @Query("MATCH (f:ActionNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) OPTIONAL MATCH (f)-[o:OPTIONS]->(op) RETURN f, collect(l), collect(to), collect(o),collect(op)")
    ActionNode findActionById(Long id);

    @Query("MATCH (f:StateNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    StateNode findStateNodeById(Long id);

    @Query("MATCH (f:StateNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l*]->(to) detach delete f,to")
    void deletePathStartingWithId(Long id);

}
