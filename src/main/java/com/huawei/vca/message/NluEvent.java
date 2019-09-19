package com.huawei.vca.message;

import java.util.*;

public class NluEvent {

    private Intent bestIntent;
    private Set<Slot> slots;

    public Intent getBestIntent() {
        return bestIntent;
    }

    public void setBestIntent(Intent bestIntent) {
        this.bestIntent = bestIntent;
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
}
