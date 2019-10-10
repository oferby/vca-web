package com.huawei.vca.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document(collection = "slots")
public class SlotEntity {

    @Id
    private String name;

    private Set<String> values;

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

}
