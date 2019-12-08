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

    @Query("MATCH (s1:StateNode)-[:HAS_OBSERVATION]->(o1:ObservationNode) " +
            "MATCH (o1)-[:OBSERVE]->(p1:PropertyNode) " +
            "WHERE " +
            "not p1.stringId in {propertyStringIdSet} " +
            "WITH collect(o1) as group1 " +
            "MATCH (s2:StateNode)-[r1:HAS_OBSERVATION]->(o2:ObservationNode) " +
            "MATCH (o2)-[r2:OBSERVE]->(p2:PropertyNode) " +
            "MATCH (s2)-[r3:LEADS]->(a2:ActionNode) " +
            "MATCH (a2)-[r4:OPTIONS]->(op:OptionNode) " +
            "WHERE " +
            "p2.stringId in {propertyStringIdSet} " +
            "and not o2 in group1 " +
            "RETURN s2, r1, o2, collect(p2), collect(r2), r3, a2, collect(r4), collect(op)")
    StateNode findStateByObservation(Set<String>propertyStringIdSet);


    @Query("MATCH(s0:StateNode)-[:OBSERVE]->(p0:PropertyNode) " +
            "MATCH (s1:StateNode)-[:HAS_OBSERVATION]->(o1:ObservationNode) " +
            "MATCH (o1)-[:OBSERVE]->(p1:PropertyNode) " +
            "WHERE " +
            "not p1.stringId in {observationPropertyStringIdSet} and " +
            "not p0.stringId in {statePropertyStringIdSet} " +
            "WITH collect(o1) as group1, collect(s0) as group2 " +
            "MATCH (s2:StateNode)-[r1:HAS_OBSERVATION]->(o2:ObservationNode) " +
            "MATCH (o2)-[r2:OBSERVE]->(p2:PropertyNode) " +
            "MATCH (s2)-[r3:LEADS]->(a2:ActionNode) " +
            "MATCH (a2)-[r4:OPTIONS]->(op:OptionNode) " +
            "MATCH(s2)-[r5:OBSERVE]->(p3:PropertyNode) " +
            "WHERE " +
            "p2.stringId in {observationPropertyStringIdSet} " +
            "and p3.stringId in {statePropertyStringIdSet} " +
            "and not o2 in group1 " +
            "and not s2 in group2 " +
            "RETURN s2, r1, o2, collect(p2), collect(r2), r3, a2, collect(r4), collect(p3), collect(r5)")
    StateNode findStateByStateAndObservation(Set<String>observationPropertyStringIdSet, Set<String>statePropertyStringIdSet);

    @Query("MATCH (f:GenericNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l*]->(to) detach delete f,to")
    void deletePathStartingWithId(Long id);

}
