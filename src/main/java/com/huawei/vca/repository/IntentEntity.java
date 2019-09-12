package com.huawei.vca.repository;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document(collection = "intents")
public class IntentEntity {

    @Id
    private String intent;
    private Set<String> textSet;

    public String getIntent() {
        return intent;
    }

    public void setIntent(String intent) {
        this.intent = intent;
    }

    public Set<String> getTextSet() {
        return textSet;
    }

    public void setTextSet(Set<String> textSet) {
        this.textSet = textSet;
    }

    public void addToTextList(String text) {

        if (textSet == null) {
            textSet = new HashSet<>();
        }

        textSet.add(text);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof IntentEntity)) return false;
        IntentEntity that = (IntentEntity) o;
        return Objects.equals(getIntent(), that.getIntent()) &&
                Objects.equals(getTextSet(), that.getTextSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getIntent(), getTextSet());
    }
}
