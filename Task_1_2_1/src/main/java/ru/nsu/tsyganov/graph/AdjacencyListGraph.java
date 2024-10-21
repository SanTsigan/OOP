package ru.nsu.tsyganov.graph;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdjacencyListGraph<V, E> implements Graph<V, E> {
    private Map<Vertex<V>, List<Vertex<V>>> adjList;

    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    @Override
    public void addVertex(Vertex<V> vertex) {

    }

    @Override
    public void removeVertex(Vertex<V> vertex) {

    }

    @Override
    public void addEdge(Edge<V, E> edge) {

    }

    @Override
    public void removeEdge(Edge<V, E> edge) {

    }


    @Override
    public List<Vertex<V>> getNeighbors(Vertex<V> vertex) {
        return List.of();
    }

    @Override
    public void readFromFile(String filename) {

    }
}
