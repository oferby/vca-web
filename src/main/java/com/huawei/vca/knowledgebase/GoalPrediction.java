package com.huawei.vca.knowledgebase;

import com.huawei.vca.message.Slot;
import com.huawei.vca.repository.entity.SlotEntity;
import com.huawei.vca.repository.entity.MenuItemEntity;

import java.util.ArrayList;
import java.util.List;

public class GoalPrediction {

    private List<Slot> informSlots;
    private List<Slot> denySlots;
    private List<MenuItemEntity> possibleGoals;
    private SlotEntity bestNextQuestion;

    public List<Slot> getInformSlots() {
        return informSlots;
    }

    public void setInformSlots(List<Slot> informSlots) {
        this.informSlots = informSlots;
    }

    public List<Slot> getDenySlots() {
        return denySlots;
    }

    public void setDenySlots(List<Slot> denySlots) {
        this.denySlots = denySlots;
    }

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
                "informSlots=" + informSlots +
                ", denySlots=" + denySlots +
                ", possibleGoals=" + possibleGoals +
                ", bestNextQuestion=" + bestNextQuestion +
                '}';
    }
}
