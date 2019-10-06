package com.huawei.vca.repository.nlu;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface SynonymRepository extends MongoRepository<EntitySynonyms, String> {

}
