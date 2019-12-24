package com.huawei.vca.repository;

import com.huawei.vca.message.Slot;
import com.huawei.vca.message.ValueType;
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

import java.util.*;

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

    //    @Test
    public void testCRUD() {


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

        List<MenuItemEntity> notResult = mongoTemplate.find(query(where("slots").not().all(notInSlotList)), MenuItemEntity.class);
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

    //    @Test
    public void addMenuItems() {

        List<MenuItemEntity> menuItemEntities = new ArrayList<>();

        MenuItemEntity menuItemEntity = new MenuItemEntity();
        menuItemEntity.setDescription("Medium Beef Burger with white bread, BBQ sauce, pickles, chips and beer.");
        menuItemEntity.addSlot("food:ingredient:meat", "beef");
        menuItemEntity.addSlot("food:main_dish", "burger");
        menuItemEntity.addSlot("food:main_dish:burger:cook_level", "medium");
        menuItemEntity.addSlot("food:ingredient:plant:vegetable", "pickles");
        menuItemEntity.addSlot("food:condiment:sauce:burger_sauce", "barbecue sauce");
        menuItemEntity.addSlot("test", "test");
        menuItemEntity.setImageUrl("/images/food/burger1.jpg");
        menuItemEntities.add(menuItemEntity);

        menuItemEntity = new MenuItemEntity();
        menuItemEntity.setDescription("Medium Beef Burger with white bread, BBQ sauce, onions, chips and beer.");
        menuItemEntity.addSlot("food:ingredient:meat", "beef");
        menuItemEntity.addSlot("food:main_dish", "burger");
        menuItemEntity.addSlot("food:main_dish:burger:cook_level", "medium");
        menuItemEntity.addSlot("food:ingredient:plant:vegetable", "onions");
        menuItemEntity.addSlot("food:condiment:sauce:burger_sauce", "barbecue sauce");
        menuItemEntity.addSlot("test", "test");
        menuItemEntity.setImageUrl("/images/food/burger2.jpg");
        menuItemEntities.add(menuItemEntity);

        menuItemRepository.saveAll(menuItemEntities);

    }

    @Test
    public void queryMenuItems() {

        this.addMenuItems();

        List<Slot> slotList = new ArrayList<>();
        List<Slot> notInSlotList = new ArrayList<>();

        slotList.add(new Slot("food:ingredient:meat", "beef"));
        slotList.add(new Slot("food:main_dish", "burger"));
        slotList.add(new Slot("food:main_dish:burger:cook_level", "medium"));
        slotList.add(new Slot("food:condiment:sauce:burger_sauce", "barbecue sauce"));

        List<MenuItemEntity> result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert result.size() == 2;

        slotList.add(new Slot("food:ingredient:plant:vegetable", "onions"));

        result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert result.size() == 1;

//        this.clean();


    }

    private void clean() {
        List<Slot> slotList = new ArrayList<>();
        slotList.add(new Slot("test", "test"));

        List<MenuItemEntity> result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert result.size() == 2;

        menuItemRepository.deleteAll(result);

    }


    @Test
    public void testUserGoal() {

        List<Slot> slotList = new ArrayList<>();
        slotList.add(new Slot("food:main_dish", "burger"));

        List<MenuItemEntity> result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert !result.isEmpty();

        slotList.add(new Slot("food:ingredient:meat", "beef"));
        result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert !result.isEmpty();

        slotList.add(new Slot("food:main_dish:burger:cook_level", "medium"));

        result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert !result.isEmpty();

        slotList.add(new Slot("food:condiment:sauce:burger_sauce","barbecue sauce"));

        result = mongoTemplate.find(query(where("slots").all(slotList)), MenuItemEntity.class);

        assert !result.isEmpty();

        Set<Slot> toAsk = new HashSet<>();
        for (Slot slot : result.get(0).getSlots()) {
            if (!result.get(1).getSlots().contains(slot)){
                toAsk.add(slot);
            }
        }

        for (Slot slot : result.get(1).getSlots()) {
            if (!result.get(0).getSlots().contains(slot)){
                toAsk.add(slot);
            }
        }

        assert !toAsk.isEmpty();
    }


//    @Test
    public void addContinuousSlot(){


        Random r = new Random();

        List<MenuItemEntity> all = menuItemRepository.findAll();

        for (MenuItemEntity menuItemEntity : all) {

            Slot calories = new Slot();
            calories.setValueType(ValueType.CONTINUOUS);
            calories.setMinValue(0);
//            calories.setMaxValue(1500);
            calories.setValue(String.valueOf(r.nextInt(1500)));
            menuItemEntity.addSlot(calories);

        }

        menuItemRepository.saveAll(all);
    }

}
