package com.huawei.vca.repository.graph;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConversationRepository extends Neo4jRepository<RootNode, Long> {


}
