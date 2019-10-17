package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.entity.MealUserGoalEntity;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.core.MongoTemplate;


import java.util.List;

public interface MealUserGoalRepository extends MongoRepository<MealUserGoalEntity, String> {

}
