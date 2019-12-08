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


    @Query("MATCH (o1:ObservationNode)-[:OBSERVE]->(p1:PropertyNode) " +
            "where not p1.stringId in {propStringIdSet} " +
            "with collect(o1) as g1 " +
            "MATCH (o2:ObservationNode)-[h:OBSERVE]->(p2:PropertyNode) " +
            "where p2.stringId in {propStringIdSet} and not o2 in g1 " +
            "return o2, collect(p2), collect(h)")
    ObservationNode findObservationByProperties(Set<String> propStringIdSet);


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


    @Query("MATCH (s1:StateNode)-[:HAS_PROPERTY]->(p1:PropertyNode) " +
            "WHERE " +
            "not p1.stringId in {optionStringIdSet} " +
            "WITH collect(s1) as group1 " +
            "MATCH (s2:StateNode)-[:HAS_PROPERTY]->(p2:PropertyNode) " +
            "MATCH (s2)-[:HAS_OBSERVATION]->(o2:ObservationNode) " +
            "MATCH (s2)-[l:LEADS]->(a2:ActionNode) " +
            "WHERE " +
            "p2.stringId in {optionStringIdSet} " +
            "and not s2 in group1 and id(o2)={observationId} " +
            "RETURN s2,a2,l")
    StateNode findStateByPropertiesAndObservation(Set<String>optionStringIdSet, Long observationId);

    @Query("MATCH (s1:StateNode)-[:HAS_OBSERVATION]->(o1:ObservationNode) " +
            "MATCH (o1)-[:OBSERVE]->(p1:PropertyNode) " +
            "WHERE " +
            "not p1.stringId in {optionStringIdSet} " +
            "WITH collect(o1) as group1 " +
            "MATCH (s2:StateNode)-[r1:HAS_OBSERVATION]->(o2:ObservationNode) " +
            "MATCH (o2)-[r2:OBSERVE]->(p2:PropertyNode) " +
            "MATCH (s2)-[r3:LEADS]->(a2:ActionNode) " +
            "WHERE " +
            "p2.stringId in {optionStringIdSet} " +
            "and not o2 in group1 " +
            "RETURN s2, r1, o2, collect(p2), collect(r2), r3, a2")
    StateNode findStateByProperties(Set<String>optionStringIdSet);


    @Query("MATCH (s:StateNode) " +
            "MATCH (s)-[ho:HAS_OBSERVATION]->(o:ObservationNode) " +
            "MATCH (s)-[l:LEADS]->(a:ActionNode) " +
            "MATCH (a)-[opt:OPTIONS]->(op:OptionNode)" +
            "WHERE " +
            "not (s)-[:HAS_PROPERTY]->(:PropertyNode) " +
            "and id(o)={observationId} " +
            "return s,collect(a),collect(l), collect(o), collect(ho), collect(opt), collect(op)")
    StateNode findStateWithoutPropertiesAndObservation(Long observationId);


    @Query("MATCH (f:ObservationNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l:LEADS]->(to) RETURN f, collect(l), collect(to)")
    ObservationNode findObservationNodeById(Long id);

    @Query("MATCH (f:GenericNode) WHERE id(f)={0} OPTIONAL MATCH (f)-[l*]->(to) detach delete f,to")
    void deletePathStartingWithId(Long id);

}
