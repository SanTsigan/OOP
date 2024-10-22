package ru.nsu.tsyganov.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class AdjacencyListGraph<V, E> implements Graph<V, E> {
    private Map<Vertex<V>, List<Vertex<V>>> adjList;
    private List<Vertex<V>> vertexList;
    private int vertices = 0;

    public AdjacencyListGraph() {
        adjList = new HashMap<>();
    }

    @Override
    public int vertices() {
        return vertices;
    }

    @Override
    public List<Vertex<V>> vertexList(){
        return vertexList;
    }

    @Override
    public void addVertex(Vertex<V> vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
        vertexList.add(vertex);
        vertices++;
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        adjList.remove(vertex);
        for(List<Vertex<V>> neighbours : adjList.values()) {
            neighbours.remove(vertex);
        }
        vertexList.remove(vertex);
        vertices--;
    }

    @Override
    public void addEdge(Edge<V, E> edge) {
        adjList.putIfAbsent(edge.getFrom(), new ArrayList<>());
        adjList.putIfAbsent(edge.getTo(), new ArrayList<>());
        adjList.get(edge.getFrom()).add(edge.getTo());
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        List<Vertex<V>> neighbors = adjList.get(edge.getFrom());
        if (neighbors != null) {
            neighbors.remove(edge.getTo());
        }
    }


    @Override
    public List<Vertex<V>> getNeighbors(Vertex<V> vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if(parts.length == 3) {
                    Vertex<V> from = (Vertex<V>) new Vertex<String>(parts[0]);
                    Vertex<V> to = (Vertex<V>) new Vertex<String>(parts[1]);
                    Double weight = Double.parseDouble(parts[2]);

                    addVertex(from);
                    addVertex(to);
                    addEdge((Edge<V, E>) new Edge<>(from, to,
                            "(" + parts[0] + ", " + parts[1] + ")",
                            weight));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формат числа: " + e.getMessage());
        }
    }
}
