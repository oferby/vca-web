package com.huawei.vca.repository.graph;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface ConversationGraphRepository extends Neo4jRepository<GenericNode, Long> {

    @Query("MATCH (r:RootNode{stringId:'root'}) RETURN r")
    RootNode getRootNode();

    @Query("MATCH (f:GenericNode{stringId:'root'}) OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    RootNode getRootNodeWithObservations();

    @Query("MATCH (o1:ObservationNode)-[:OBSERVE]->(p1:PropertyNode) " +
            "where not p1.stringId in {propStringIdList} " +
            "with collect(o1) as g1 " +
            "MATCH (o2:ObservationNode)-[h:OBSERVE]->(p2:PropertyNode) " +
            "where p2.stringId in {propStringIdList} and not o2 in g1 " +
            "return o2, collect(p2), collect(h)")
    ObservationNode findObservationByProperties(List<String> propStringIdList);

    PropertyNode findByStringId(String stringId);


    @Query("MATCH (a1:ActionNode)-[:OPTIONS]->(o1:OptionNode) " +
            "where not o1.stringId in {optionIdList} and a1.stringId={stringId} " +
            "with collect(a1) as group1 " +
            "MATCH (a2:ActionNode)-[op:OPTIONS]->(o2:OptionNode) " +
            "where o2.stringId in {optionIdList} and not a2 in group1 " +
            "RETURN a2, collect(op), collect(o2)")
    ActionNode findActionByIdAndOptions(String stringId, List<String>optionIdList);


    @Query("MATCH (s1:StateNode)-[:HAS_PROPERTY]->(p1:PropertyNode) " +
            "WHERE " +
            "not p1.stringId in {optionStringIdList} " +
            "WITH collect(s1) as group1 " +
            "MATCH (s2:StateNode)-[:HAS_PROPERTY]->(p2:PropertyNode) " +
            "MATCH (s2)-[:HAS_OBSERVATION]->(o2:ObservationNode) " +
            "MATCH (s2)-[l:LEADS]->(a2:ActionNode) " +
            "WHERE " +
            "p2.stringId in {optionStringIdList} " +
            "and not s2 in group1 and id(o2)={observationId} " +
            "RETURN s2,a2,l")
    StateNode findStateByPropertiesAndObservation(List<String>optionStringIdList, Long observationId);



    @Query("MATCH (s:StateNode)-[:HAS_OBSERVATION]->(o:ObservationNode) " +
            "WHERE id(o)={observationId} " +
            "and not (s)-[:OBSERVE]->(:PropertyNode) " +
            "RETURN s")
    StateNode findStateNodeByObservationId(Long observationId);


    @Query("MATCH (o3:ObservationNode)-[:OBSERVE]->(p3:PropertyNode) " +
            "WHERE " +
            "not p3.stringId in {propertyStringIdSet} " +
            "WITH collect(o3) as group2, collect(p3) as group3 " +
            "MATCH (o4)-[r4:OBSERVE]->(p4:PropertyNode) " +
            "MATCH (s1:StateNode)-[r1:HAS_OBSERVATION]->(o4) " +
            "MATCH (s1)-[r2:LEADS]->(a1:ActionNode) " +
            "WHERE " +
            "p4.stringId in {propertyStringIdSet} " +
            "and not p4 in group3 " +
            "and not o4 in group2 " +
            "and not (s1)-[:OBSERVE]->(:PropertyNode) " +
            "WITH o4, collect(p4) as col_p4, collect(r4) as col_r4, s1, r2, a1 " +
            "WHERE " +
            "size(col_p4)=size({propertyStringIdSet}) " +
            "WITH s1, r2, a1 " +
            "OPTIONAL MATCH(a1)-[r3:OPTIONS]->(op:OptionNode) " +
            "RETURN s1, r2, a1, collect(op), collect(r3)")
    StateNode findStateByObservation(Set<String>propertyStringIdSet);


    @Query("MATCH(s1:StateNode)-[:OBSERVE]->(p1:PropertyNode) " +
            "WHERE " +
            "not p1.stringId in {statePropertyStringIdSet} " +
            "WITH collect(s1) as group1 " +
            "MATCH(s2:StateNode)-[r2:OBSERVE]->(p2:PropertyNode) " +
            "MATCH (s2)-[r20:LEADS]->(a2:ActionNode) " +
            "WHERE " +
            "p2.stringId in {statePropertyStringIdSet} " +
            "and not s2 in group1 " +
            "WITH collect(p2) as col_p2, s2, collect(r2) as col_r2, r20, a2 " +
            "WHERE size(col_p2) = size({statePropertyStringIdSet}) " +
            "WITH col_p2, s2, col_r2, r20, a2 " +
            "MATCH (o3:ObservationNode)-[:OBSERVE]->(p3:PropertyNode) " +
            "WHERE " +
            "not p3.stringId in {observationPropertyStringIdSet} " +
            "WITH collect(o3) as group2, collect(p3) as group3, col_p2, s2, col_r2, r20, a2 " +
            "MATCH (o4)-[r4:OBSERVE]->(p4:PropertyNode) " +
            "MATCH (s2)-[:HAS_OBSERVATION]->(o4) " +
            "WHERE " +
            "p4.stringId in {observationPropertyStringIdSet} " +
            "and not p4 in group3 " +
            "and not o4 in group2 " +
            "WITH s2, a2, r20 " +
            "OPTIONAL MATCH(a2)-[r3:OPTIONS]->(op:OptionNode) " +
            "RETURN DISTINCT s2, a2, r20, collect(op), collect(r3)")
    StateNode findStateByStateAndObservation(Set<String>observationPropertyStringIdSet, Set<String>statePropertyStringIdSet);

    @Query("MATCH (f:GenericNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l*]->(to) detach delete f,to")
    void deletePathStartingWithId(Long id);

}
