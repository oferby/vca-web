package com.huawei.vca.repository.nlu.rasa;
import java.util.List;

public class RasaNluData {

    private List<CommonExample> common_examples;
    private List<RegexFeature> regex_features;
    private List<LookupTable> lookup_tables;
    private List<EntitySynonym> entity_synonyms;

    public List<CommonExample> getCommon_examples() {
        return common_examples;
    }

    public void setCommon_examples(List<CommonExample> common_examples) {
        this.common_examples = common_examples;
    }

    public List<RegexFeature> getRegex_features() {
        return regex_features;
    }

    public void setRegex_features(List<RegexFeature> regex_features) {
        this.regex_features = regex_features;
    }

    public List<LookupTable> getLookup_tables() {
        return lookup_tables;
    }

    public void setLookup_tables(List<LookupTable> lookup_tables) {
        this.lookup_tables = lookup_tables;
    }

    public List<EntitySynonym> getEntity_synonyms() {
        return entity_synonyms;
    }

    public void setEntity_synonyms(List<EntitySynonym> entity_synonyms) {
        this.entity_synonyms = entity_synonyms;
    }
}
