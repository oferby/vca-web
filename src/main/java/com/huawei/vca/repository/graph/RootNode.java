package com.huawei.vca.repository.graph;

import org.neo4j.ogm.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@NodeEntity
public class RootNode extends HasObservationsNode implements Comparable<StateNode>{

    private Integer visited;

    public int getVisited() {
        return visited;
    }

    public void setVisited(int visited) {
        this.visited = visited;
    }

    public void increaseVisited() {

        if (this.visited == null)
            visited = 0;

        this.visited++;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        RootNode rootNode = (RootNode) o;
        return Objects.equals(visited, rootNode.visited);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), visited);
    }

    @Override
    public int compareTo(StateNode stateNode) {

        return this.getStringId().compareTo(stateNode.getStringId());

    }
}
