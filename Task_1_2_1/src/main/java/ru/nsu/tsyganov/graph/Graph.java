package ru.nsu.tsyganov.graph;

import java.io.File;
import java.io.IOException;
import java.util.List;

public interface Graph<V, E> {
    int vertices();
    int edges();
    List<Vertex<V>> vertexList();
    void addVertex(Vertex<V> vertex);
    void removeVertex(Vertex<V> vertex);
    void addEdge(Edge<V, E> edge);
    void removeEdge(Edge<V, E> edge);
    List<Vertex<V>> getNeighbors(Vertex<V> vertex);
    void readFromFile(String filename);
}
