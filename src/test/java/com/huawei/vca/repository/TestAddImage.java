package com.huawei.vca.repository;


import com.huawei.vca.repository.controller.MealUserGoalRepository;
import com.huawei.vca.repository.controller.MenuItemRepository;
import com.huawei.vca.repository.entity.MenuItemEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestAddImage {

    @Autowired
    private MealUserGoalRepository mealUserGoalRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;



    @Test
    public void addImageURL() {
        List<MenuItemEntity> all = menuItemRepository.findAll();
        for (MenuItemEntity menuItemEntity : all) {
            if (menuItemEntity.getDescription().contains("burger") && menuItemEntity.getDescription().contains("chips")) {
                menuItemEntity.setImageUrl("burger_and_fries.jpg");
            } else if (menuItemEntity.getDescription().contains("burger")) {
                menuItemEntity.setImageUrl("burger3.jpg");
            } else if (menuItemEntity.getDescription().contains("spaghetti") && menuItemEntity.getDescription().contains("bolognese")) {
                menuItemEntity.setImageUrl("spaghetti_bolognese.jpg");
            } else if (menuItemEntity.getDescription().contains("spaghetti")) {
                menuItemEntity.setImageUrl("spaghetti.jpg");
            } else if (menuItemEntity.getDescription().contains("penne")) {
                menuItemEntity.setImageUrl("penne.jpg");
            } else if (menuItemEntity.getDescription().contains("lasagna")) {
                menuItemEntity.setImageUrl("lasagna.jpg");
            } else if (menuItemEntity.getDescription().contains("ravioli")) {
                menuItemEntity.setImageUrl("ravioli.jpg");
            }
            menuItemRepository.save(menuItemEntity);
        }
    }
}

