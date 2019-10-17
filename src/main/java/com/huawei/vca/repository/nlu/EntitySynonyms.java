package com.huawei.vca.repository.nlu;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "synonyms")
public class EntitySynonyms {

    @Id
    private String value;

    private List<String> synonyms;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<String> getSynonyms() {
        return synonyms;
    }

    public void setSynonyms(List<String> synonyms) {
        this.synonyms = synonyms;
    }

    public void addSynonym(String s){
        if (synonyms == null)
            synonyms = new ArrayList<>();
        synonyms.add(s);
    }
}
