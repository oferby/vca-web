package com.huawei.vca.knowledgebase;

import com.huawei.vca.repository.entity.FeatureGroupEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;

import java.util.HashSet;
import java.util.Set;

public class GoalPrediction {

    private Set<MenuItemEntity> possibleGoals;
    private FeatureGroupEntity bestNextQuestion;

    public Set<MenuItemEntity> getPossibleGoals() {
        return possibleGoals;
    }

    public void setPossibleGoals(Set<MenuItemEntity> possibleGoals) {
        this.possibleGoals = possibleGoals;
    }

    public FeatureGroupEntity getBestNextQuestion() {
        return bestNextQuestion;
    }

    public void setBestNextQuestion(FeatureGroupEntity bestNextQuestion) {
        this.bestNextQuestion = bestNextQuestion;
    }

    public void addPossibleGoal(MenuItemEntity menuItemEntity) {
        if (possibleGoals == null){
            this.possibleGoals = new HashSet<>();
        }

        this.possibleGoals.add(menuItemEntity);
    }
}
