package ru.nsu.tsyganov.graph;

import java.util.List;

/**
 * Algorithm interface.
 */
public interface Alg<V, E> {
    List<Vertex<V>> perform(Graph<V, E> g);
}
