package com.huawei.vca.message;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BotUtterEvent extends BotEvent{

    private String id;
    private String text;
    private Set<Option> options;
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

    public void setOptions(Set<Option> options) {
        this.options = options;
    }

    public void addOption(Option option) {
        if (this.options == null)
            this.options = new HashSet<>();
        options.add(option);
    }

    public Set<Option> getOptions() {
        return options;
    }

    public List<ImageInfo> getImageInfoList() {
        return imageInfoList;
    }

    public void addImage(ImageInfo imageInfo){
        if (imageInfoList == null)
            imageInfoList = new ArrayList<>();
        imageInfoList.add(imageInfo);
    }

    public void setImageInfoList(List<ImageInfo> imageInfoList) {
        this.imageInfoList = imageInfoList;
    }
}
