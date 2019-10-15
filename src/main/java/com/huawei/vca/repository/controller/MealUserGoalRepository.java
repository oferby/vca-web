package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.entity.MealUserGoalEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MealUserGoalRepository extends MongoRepository<MealUserGoalEntity, String> {

}
