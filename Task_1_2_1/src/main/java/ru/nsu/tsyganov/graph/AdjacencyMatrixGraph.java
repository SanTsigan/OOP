package ru.nsu.tsyganov.graph;

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class AdjacencyMatrixGraph<V, E> implements Graph<V, E>{
    private boolean[][] adjacencyMatrix;
    private List<Vertex<V>> vertices;
    private Map<V, Integer> vertexIntegerMap;

    public AdjacencyMatrixGraph(int size) {
        adjacencyMatrix = new boolean[size][size];
        vertices = new ArrayList<>();
        vertexIntegerMap = new HashMap<>();
    }

    @Override
    public void addVertex(Vertex<V> vertex) {
        if(!vertexIntegerMap.containsKey(vertex.getLabel())) {
            vertexIntegerMap.put(vertex.getLabel(), vertices.size());
            vertices.add(vertex);
        }
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        Integer index = vertexIntegerMap.remove(vertex.getLabel());
        if(index != null) {
            vertices.remove((int)index);
            for(int i = 0; i < adjacencyMatrix.length; i++) {
                adjacencyMatrix[index][i] = false;
                adjacencyMatrix[i][index] = false;
            }

            for(int i = index; i < vertices.size(); i++) {
                vertexIntegerMap.put(vertices.get(i).getLabel(), i);
            }
        }
    }

    @Override
    public void addEdge(Edge<V, E> edge) {
        int fromIndex = vertexIntegerMap.get(edge.getFrom().getLabel());
        int toIndex = vertexIntegerMap.get(edge.getTo().getLabel());
        adjacencyMatrix[fromIndex][toIndex] = true;
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        int fromIndex = vertexIntegerMap.get(edge.getFrom().getLabel());
        int toIndex = vertexIntegerMap.get(edge.getTo().getLabel());
        adjacencyMatrix[fromIndex][toIndex] = false;
    }

    @Override
    public List<Vertex<V>> getNeighbors(Vertex<V> vertex) {
        List<Vertex<V>> neighbours = new ArrayList<>();
        int index = vertexIntegerMap.get(vertex.getLabel());
        for(int i = 0; i < adjacencyMatrix.length; i++) {
            if(adjacencyMatrix[index][i]) {
                neighbours.add(vertices.get(i));
            }
        }
        return neighbours;
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line;
            while((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if(parts.length == 3) {
                    String from = parts[0];
                    String to = parts[1];
                    Double weight = Double.parseDouble(parts[2]);

                    addVertex((Vertex<V>) new Vertex<>(from));
                    addVertex((Vertex<V>) new Vertex<>(to));
                    addEdge((Edge<V, E>) new Edge<>(vertices.get(Integer.parseInt(from)),
                            vertices.get(Integer.parseInt(to)),
                            from + " " + to,
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
