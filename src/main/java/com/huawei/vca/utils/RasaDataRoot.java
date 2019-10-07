package com.huawei.vca.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

public class RasaDataRoot {

    private RasaNluData rasa_nlu_data;

    public RasaNluData getRasa_nlu_data() {
        return rasa_nlu_data;
    }

    public void setRasa_nlu_data(RasaNluData rasa_nlu_data) {
        this.rasa_nlu_data = rasa_nlu_data;
    }
}
