package com.huawei.vca.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "users")
public class UserEntity {

    @Id
    private String userId;
    private String name;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity)) return false;
        UserEntity userEntity = (UserEntity) o;
        return Objects.equals(getUserId(), userEntity.getUserId()) &&
                Objects.equals(getName(), userEntity.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getName());
    }
}
