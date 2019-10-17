package com.huawei.vca.repository.controller;

import com.huawei.vca.repository.entity.DialogueEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DialogueRepository extends MongoRepository<DialogueEntity, String> {

}
