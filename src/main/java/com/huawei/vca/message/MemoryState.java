package com.huawei.vca.message;

import java.util.Set;

public class MemoryState {

    private NluEvent lastNluEvent;
    private Set<Slot> inform;
    private Set<Slot> deny;
    private Set<Slot> dontCare;

    public NluEvent getLastNluEvent() {
        return lastNluEvent;
    }

    public void setLastNluEvent(NluEvent lastNluEvent) {
        this.lastNluEvent = lastNluEvent;
    }

    public Set<Slot> getInform() {
        return inform;
    }

    public void setInform(Set<Slot> inform) {
        this.inform = inform;
    }

    public Set<Slot> getDeny() {
        return deny;
    }

    public void setDeny(Set<Slot> deny) {
        this.deny = deny;
    }

    public Set<Slot> getDontCare() {
        return dontCare;
    }

    public void setDontCare(Set<Slot> dontCare) {
        this.dontCare = dontCare;
    }
}
