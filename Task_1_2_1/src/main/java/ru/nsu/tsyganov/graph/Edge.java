package ru.nsu.tsyganov.graph;

import java.util.Objects;

/**
 * Class for Edges.
 * @param <V> type for vertex labels.
 * @param <E> type for edge labels.
 */
public class Edge<V, E> {

    private Vertex<V> from;
    private Vertex<V> to;
    private E label;
    private Double weight;

    /**
     * constructor.
     */
    public Edge(Vertex<V> from, Vertex<V> to, E label, Double weight) {
        this.from = from;
        this.to = to;
        this.label = label;
        this.weight = weight;
    }

    public Vertex<V> getFrom() {
        return from;
    }

    public Vertex<V> getTo() {
        return to;
    }

    public Double getWeight() {
        return weight;
    }

    @Override
    public String toString() {
        return "(" + this.from + ", " + this.to + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof Edge<?, ?>)) {
            return false;
        }
        Edge<?, ?> other = (Edge<?, ?>) obj;
        return this.to.equals(other.to) && this.from.equals(other.from)
                && Objects.equals(this.toString(), other.toString()) &&
                Objects.equals(this.weight, other.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, from, label, weight);
    }

}
