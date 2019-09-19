package com.huawei.vca.message;

import java.util.ArrayList;
import java.util.List;

public class NluEvent {

    private Intent bestIntent;

    public Intent getBestIntent() {
        return bestIntent;
    }

    public void setBestIntent(Intent bestIntent) {
        this.bestIntent = bestIntent;
    }


}
