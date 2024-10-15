package ru.nsu.tsyganov.graph;

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class AdjcencyMatrixGraph<V, E> implements Graph<V, E>{

    @Override
    public void addVertex(Vertex<V> vertex) {

    }

    @Override
    public void removeVertex(Vertex<V> vertex) {

    }

    @Override
    public void addEdge(Vertex<V> source, Vertex<V> destination, E label, Double weight) {

    }

    @Override
    public void removeEdge(Vertex<V> source, Vertex<V> destination) {

    }

    @Override
    public List<Edge<V, E>> getNeighbors(Vertex<V> vertex) {
        return List.of();
    }

    @Override
    public void readFromFile(String filename) {

    }

    @Override
    public List<Vertex<V>> topologicalSort(Graph<V, E> g) {
        return List.of();
    }
}
