package com.huawei.vca.repository.entity;

import com.huawei.vca.message.BotUtterEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "feature_group")
public class FeatureGroupEntity {

    @Id
    private String name;

    private Set<String> values;

    private BotUtterEntity relatedQuestion;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getValues() {
        return values;
    }

    public void setValues(Set<String> values) {
        this.values = values;
    }

    public void addValue(String value){
        if (this.values == null){
            this.values = new HashSet<>();
        }
        this.values.add(value);
    }

    public BotUtterEntity getRelatedQuestion() {
        return relatedQuestion;
    }

    public void setRelatedQuestion(BotUtterEntity relatedQuestion) {
        this.relatedQuestion = relatedQuestion;
    }
}
