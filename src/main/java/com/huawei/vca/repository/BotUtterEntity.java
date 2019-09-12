package com.huawei.vca.repository;

import com.huawei.vca.message.Event;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Document(collection = "bot_utters")
public class BotUtterEntity extends BotEvent {

    @Id
    private String id;

    private Set<String> textSet;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
        if (!(o instanceof BotUtterEntity)) return false;
        BotUtterEntity entity = (BotUtterEntity) o;
        return Objects.equals(getId(), entity.getId()) &&
                Objects.equals(getTextSet(), entity.getTextSet());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTextSet());
    }
}
