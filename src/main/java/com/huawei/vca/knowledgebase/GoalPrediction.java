package com.huawei.vca.knowledgebase;

import com.huawei.vca.repository.entity.SlotEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GoalPrediction {

    private List<MenuItemEntity> possibleGoals;
    private SlotEntity bestNextQuestion;

    public List<MenuItemEntity> getPossibleGoals() {
        return possibleGoals;
    }

    public void setPossibleGoals(List<MenuItemEntity> possibleGoals) {
        this.possibleGoals = possibleGoals;
    }

    public SlotEntity getBestNextQuestion() {
        return bestNextQuestion;
    }

    public void setBestNextQuestion(SlotEntity bestNextQuestion) {
        this.bestNextQuestion = bestNextQuestion;
    }

    public void addPossibleGoal(MenuItemEntity menuItemEntity) {
        if (possibleGoals == null){
            this.possibleGoals = new ArrayList<>();
        }

        this.possibleGoals.add(menuItemEntity);
    }

    @Override
    public String toString() {
        return "GoalPrediction{" +
                "possibleGoals=" + possibleGoals +
                ", bestNextQuestion=" + bestNextQuestion +
                '}';
    }
}
