package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.entity.SlotEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeatureGroupRepository extends MongoRepository<SlotEntity, String> {
}
