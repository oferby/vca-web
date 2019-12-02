package com.huawei.vca.message;

import java.util.HashMap;
import java.util.Map;

public enum Act {

    INFORM("inform"),
    REQMORE("reqmore"),
    NEGATE("negate"),
    AFFIRM("affirm"),
    DENY("deny"),
    QUERY("query"),
    CHOOSE("choose");

    private final String value;
    static private Map<String,Act> stringActMap = new HashMap<>();

    static {
        for (Act act : Act.values()) {
            stringActMap.put(act.getValue(), act);
        }

    }

    Act(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Act getByValue(String act){
        return stringActMap.get(act);
    }
}
