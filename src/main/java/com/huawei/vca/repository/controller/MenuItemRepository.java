package com.huawei.vca.repository.controller;

import com.huawei.vca.message.Slot;
import com.huawei.vca.repository.entity.MenuItemEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Set;

public interface MenuItemRepository extends MongoRepository<MenuItemEntity, String> {


}
