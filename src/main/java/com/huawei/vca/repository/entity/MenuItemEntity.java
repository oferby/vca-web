package com.huawei.vca.repository.entity;

import com.huawei.vca.message.Slot;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document(collection = "menu_item")
public class MenuItemEntity {

    @Id
    private String id;

    private String description;

    private Set<Slot> slots;

    private String imageUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public void addSlot(Slot slot) {
        if (slots == null) {
            slots = new HashSet<>();
        }

        slots.add(slot);
    }

    public void addSlot(String key, String value) {
        Slot slot = new Slot(key, value);

        if (slots == null) {
            slots = new HashSet<>();
        }

        slots.add(slot);
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public static <MenuItemEntity> List<MenuItemEntity> intersection(List<MenuItemEntity> list1, List<MenuItemEntity> list2) {

        List<MenuItemEntity> list = new ArrayList<>();

        for (MenuItemEntity menuItemEntity : list1) {
            if(list2.contains(menuItemEntity)) {
                list.add(menuItemEntity);
            }
        }

        return list;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MenuItemEntity)) return false;
        MenuItemEntity that = (MenuItemEntity) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getDescription(), that.getDescription()) &&
                Objects.equals(getSlots(), that.getSlots()) &&
                Objects.equals(getImageUrl(), that.getImageUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getDescription(), getSlots(), getImageUrl());
    }

    @Override
    public String toString() {
        return "MenuItemEntity{" +
                "id='" + id + '\'' +
                ", description='" + description + '\'' +
                ", slots=" + slots +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}
