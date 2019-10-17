package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.entity.IntentEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface IntentRepository extends MongoRepository<IntentEntity, String> {

}
