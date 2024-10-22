package ru.nsu.tsyganov.graph;

import java.util.Objects;

public class Vertex<V>{

    private final V label;
    
    public Vertex(V label) {
        this.label = label;
    }

    public V getLabel() {
        return label;
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
        return Objects.equals(this.toString(), other.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(label);
    }
}
