package com.huawei.vca.message;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.time.LocalDateTime;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = BotDefaultUtterEvent.class, name = "BotDefaultUtterEvent"),
        @JsonSubTypes.Type(value = BotUtterEvent.class, name = "BotUtterEvent"),
        @JsonSubTypes.Type(value = UserUtterEvent.class, name = "UserUtter")
})
public class Event {

    private LocalDateTime localDateTime;
    private String location;

    public Event() {
        localDateTime = LocalDateTime.now();
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
