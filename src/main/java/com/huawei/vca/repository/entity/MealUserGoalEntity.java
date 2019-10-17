package com.huawei.vca.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.Objects;

@Document(collection = "meal_user_goals")
public class MealUserGoalEntity implements Comparable<MealUserGoalEntity>{
    @Id
    private String _id;
    private Map<String, String> properties;

    public Map<String, String> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, String> properties) {
        this.properties = properties;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MealUserGoalEntity)) return false;
        MealUserGoalEntity that = (MealUserGoalEntity) o;
        return Objects.equals(getProperties(), that.getProperties());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getProperties());
    }

    @Override // NOT IMPLEMENTED!!!!
    public int compareTo(MealUserGoalEntity entity) {
        throw new java.lang.UnsupportedOperationException("MealUserGoalEntity comparison not supported yet.");
    }
}

