package com.huawei.vca.repository;

import com.huawei.vca.message.Slot;
import com.huawei.vca.repository.controller.BotUtterRepository;
import com.huawei.vca.repository.controller.FeatureGroupRepository;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.entity.BotUtterEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMenuItem {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private FeatureGroupRepository featureGroupRepository;

    @Autowired
    private BotUtterRepository botUtterRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testCRUD(){


//        BotUtterEntity botUtterEntity = new BotUtterEntity();
//        botUtterEntity.setId("ask_if_f1_v1");
//        botUtterEntity.addToTextList("this is the question about f1_v1");
//
//        botUtterEntity = botUtterRepository.save(botUtterEntity);





        List<Slot> slotList = new ArrayList<>();
        List<Slot> notInSlotList = new ArrayList<>();

        Slot slot1 = new Slot("k1", "v1");
        Slot slot2 = new Slot("k2", "v2");
        Slot slot3 = new Slot("k3", "v3");



        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setDescription("this is menu item 1");
        menuItemEntity.addSlot(slot1);
        menuItemEntity.addSlot(slot2);

        menuItemEntity = menuItemRepository.save(menuItemEntity);
        String firstId = menuItemEntity.getId();

        assert menuItemEntity.getId() != null;

        menuItemEntity = new MenuItemEntity();
        menuItemEntity.setDescription("this is menu item 2");
        menuItemEntity.addSlot(slot1);
        menuItemEntity = menuItemRepository.save(menuItemEntity);
        String secondId = menuItemEntity.getId();


        slotList.clear();
        slotList.add(slot1);

        List<MenuItemEntity> result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert result.size() == 2;

        slotList.clear();
        slotList.add(slot1);
        slotList.add(slot2);
        result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert result.size() == 1;

        slotList.clear();
        slotList.add(slot1);
        slotList.add(slot2);
        notInSlotList.clear();
        notInSlotList.add(slot3);

        result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        List<MenuItemEntity> notResult =  mongoTemplate.find(query(where("slots").not().all(notInSlotList)), MenuItemEntity.class);
        List<MenuItemEntity> intersection = MenuItemEntity.intersection(result, notResult);

        assert intersection.size() == 1;

        slotList.add(slot3);
        result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert result.size() == 0;





        menuItemRepository.deleteById(firstId);
        menuItemRepository.deleteById(secondId);

        Optional<MenuItemEntity> menuItemRepositoryById = menuItemRepository.findById(firstId);
        assert !menuItemRepositoryById.isPresent();

    }

    @Test
    public void addMenuItems(){

        menuItemRepository.deleteAll();
        List<MenuItemEntity> menuItemEntities = new ArrayList<>();

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setDescription("Medium Beef Burger with white bread, BBQ sauce, pickles, chips and beer.");
        menuItemEntity.addSlot("food:ingredient:meat","beef");
        menuItemEntity.addSlot("food:main_dish","burger");
        menuItemEntity.addSlot("food:main_dish:burger:cook_level","medium");
        menuItemEntity.addSlot("food:ingredient:plant:vegetable","pickles");
        menuItemEntity.addSlot("food:condiment:sauce:burger_sauce", "barbecue sauce");
        menuItemEntities.add(menuItemEntity);

        menuItemEntity = new MenuItemEntity();
        menuItemEntity.setDescription("Medium Beef Burger with white bread, BBQ sauce, onions, chips and beer.");
        menuItemEntity.addSlot("food:ingredient:meat","beef");
        menuItemEntity.addSlot("food:main_dish","burger");
        menuItemEntity.addSlot("food:main_dish:burger:cook_level","medium");
        menuItemEntity.addSlot("food:ingredient:plant:vegetable","onions");
        menuItemEntity.addSlot("food:condiment:sauce:burger_sauce", "barbecue sauce");
        menuItemEntities.add(menuItemEntity);

        menuItemRepository.saveAll(menuItemEntities);

    }

    @Test
    public void queryMenuItems(){

        List<Slot> slotList = new ArrayList<>();
        List<Slot> notInSlotList = new ArrayList<>();

        Slot slot1 = new Slot("food:main_dish","burger");
        Slot slot2 = new Slot("food:ingredient:plant:vegetable","onions");

        slotList.add(slot1);
        List<MenuItemEntity> result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert result.size() == 2;

        slotList.add(slot2);

        assert result.size() == 1;


    }
}
