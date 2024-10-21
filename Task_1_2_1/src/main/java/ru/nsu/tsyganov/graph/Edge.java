package ru.nsu.tsyganov.graph;

import java.util.Objects;

public class Edge<V, E> implements Comparable<Edge<V,E>>{

    private Vertex<V> from;
    private Vertex<V> to;
    private E label;
    private Double weight;

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
        } else if (!(obj instanceof Vertex<?>)) {
            return false;
        }
        Edge<?, ?> other = (Edge<?, ?>) obj;
        return this.to == other.to && this.from == other.from
                && this.label == other.label && this.weight.equals(other.weight);
    }

    @Override
    public int hashCode() {
        return Objects.hash(to, from, label, weight);
    }

    @Override
    public int compareTo(Edge<V, E> e) {
        int cmp = Double.compare(this.weight, e.weight);
        if (cmp == 0) {
            cmp = this.from.toString().compareTo(e.from.toString());
            if (cmp == 0) {
                cmp = this.to.toString().compareTo(e.to.toString());
            }
        }
        return cmp;
    }
}
