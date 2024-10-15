package ru.nsu.tsyganov.graph;

import java.util.Objects;

public class Vertex<V> implements Comparable<Vertex<V>>{

    private final V label;
    private Double minWeight;

    public Vertex(V label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return this.label.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Vertex<?>)) {
            return false;
        }
        Vertex<?> other = (Vertex<?>) obj;
        return this.label == other.label;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }

    @Override
    public int compareTo(Vertex<V> o) {
        int cmp = Double.compare(this.minWeight, o.minWeight);
        if(cmp == 0) {
            cmp = this.toString().compareTo(o.toString());
        }
        return cmp;
    }
}
