package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.IntentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IntentRepository extends MongoRepository<IntentEntity, String> {

}
