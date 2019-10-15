package com.huawei.vca.repository;

import com.huawei.vca.repository.controller.MealUserGoalRepository;
import com.huawei.vca.repository.entity.MealUserGoalEntity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Arrays;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestMealUserGoalEntity {

    @Autowired
    private MealUserGoalRepository mealUserGoalRepository;

    @Test
    public void testFindAll() {
        List<MealUserGoalEntity> all = mealUserGoalRepository.findAll();
        assert all.size() == 97;
    }
}
