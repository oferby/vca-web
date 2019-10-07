package com.huawei.vca.repository.nlu.rasa;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface RasaSynonymRepository extends MongoRepository<EntitySynonym, String> {
}
