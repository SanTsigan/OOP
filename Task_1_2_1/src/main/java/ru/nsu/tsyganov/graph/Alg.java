package ru.nsu.tsyganov.graph;

import java.util.List;

public interface Alg<V, E> {
    List<Vertex<V>> perform(Graph<V, E> g);
}
