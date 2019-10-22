package com.huawei.vca.repository;

import com.huawei.vca.message.Slot;

import com.huawei.vca.repository.controller.MealUserGoalRepository;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.entity.MealUserGoalEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//import sun.tools.java.Constants;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMealUserGoalEntity {

    @Autowired
    private MealUserGoalRepository mealUserGoalRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;


    @Test
    public void testFindAll() {
        List<MealUserGoalEntity> all = mealUserGoalRepository.findAll();
        for (MealUserGoalEntity meal : all) {
            MenuItemEntity menuItemEntity = new MenuItemEntity();
            Map props = meal.getProperties();
            props.forEach((k, v) -> {
                if ((v == null || v == "")) {
                    return; }
                if (k.toString().contains("description")) {
                    menuItemEntity.setDescription(v.toString());
                    return;
                }
                if (v.toString().contains(",")) {
                    String[] values = v.toString().split(",");
                    for (String val : values) {
                        Slot slot = new Slot(k.toString(), val);
                        menuItemEntity.addSlot(slot);
                    }
                }
                else {
                    Slot slot = new Slot(k.toString(), v.toString());
                    menuItemEntity.addSlot(slot);
                }
            });
            menuItemRepository.save(menuItemEntity);
        }
    }
}
