package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.entity.FeatureGroupEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface FeatureGroupRepository extends MongoRepository<FeatureGroupEntity, String> {
}
