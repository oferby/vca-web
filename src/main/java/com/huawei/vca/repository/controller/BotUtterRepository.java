package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.entity.BotUtterEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface BotUtterRepository extends MongoRepository<BotUtterEntity, String> {

}
