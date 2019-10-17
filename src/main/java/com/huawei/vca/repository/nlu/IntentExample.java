package com.huawei.vca.repository.nlu;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Document(collection = "intent_examples")
public class IntentExample {

    @Id
    private String text;
    private String intent;
    private List<EntityExample> entities;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public List<EntityExample> getEntities() {
        return entities;
    }

    public void setEntities(List<EntityExample> entities) {
        this.entities = entities;
    }

    public void addEntity(EntityExample entity){
        if (entities ==null)
            entities = new ArrayList<>();
        entities.add(entity);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntentExample)) return false;
        IntentExample that = (IntentExample) o;
        return Objects.equals(getText(), that.getText()) &&
                Objects.equals(getIntent(), that.getIntent()) &&
                Objects.equals(getEntities(), that.getEntities());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getText(), getIntent(), getEntities());
    }

    @Override
    public String toString() {
        return "IntentExample{" +
                "text='" + text + '\'' +
                ", intent='" + intent + '\'' +
                ", entityList=" + entities +
                '}';
    }
}
