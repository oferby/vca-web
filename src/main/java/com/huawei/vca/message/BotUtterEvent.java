package com.huawei.vca.message;

import java.util.ArrayList;
import java.util.List;

public class BotUtterEvent extends BotEvent{

    private String id;
    private String text;
    private List<Option> options;
    private List<ImageInfo> imageInfoList;

    public BotUtterEvent() {
    }

    public BotUtterEvent(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public void addOption(Option option) {
        if (this.options == null)
            this.options = new ArrayList<>();
        options.add(option);
    }

    public List<Option> getOptions() {
        return options;
    }

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void setImageInfoList(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }
}
