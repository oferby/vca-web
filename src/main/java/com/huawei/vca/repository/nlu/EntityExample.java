package com.huawei.vca.repository.nlu;

import java.util.Objects;

public class EntityExample {

    private String entity;
    private String value;
    private Integer start;
    private Integer end;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getEnd() {
        return end;
    }

    public void setEnd(Integer end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EntityExample)) return false;
        EntityExample entity1 = (EntityExample) o;
        return Objects.equals(getEntity(), entity1.getEntity()) &&
                Objects.equals(getValue(), entity1.getValue()) &&
                Objects.equals(getStart(), entity1.getStart()) &&
                Objects.equals(getEnd(), entity1.getEnd());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntity(), getValue(), getStart(), getEnd());
    }

    @Override
    public String toString() {
        return "Entity{" +
                "entity='" + entity + '\'' +
                ", value='" + value + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
