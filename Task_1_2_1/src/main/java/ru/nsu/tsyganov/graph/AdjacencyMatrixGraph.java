package ru.nsu.tsyganov.graph;

import java.io.IOException;
import java.util.*;
import java.io.BufferedReader;
import java.io.FileReader;

public class AdjacencyMatrixGraph<V, E> implements Graph<V, E>{
    private boolean[][] adjacencyMatrix;
    private List<Vertex<V>> vertexList;
    private List<Edge<V, E>> edgeList;
    private Map<V, Integer> vertexIntegerMap;

    public AdjacencyMatrixGraph(int size) {
        adjacencyMatrix = new boolean[size][size];
        vertexList = new ArrayList<>();
        vertexIntegerMap = new HashMap<>();
        edgeList = new ArrayList<>();
    }

    public boolean[][] getAdjacencyMatrix() {
        return adjacencyMatrix;
    }

    @Override
    public int vertices() {
        return vertexList.size();
    }

    @Override
    public int edges() {
        return edgeList.size();
    }

    @Override
    public List<Vertex<V>> vertexList(){
        return vertexList;
    }

    @Override
    public void addVertex(Vertex<V> vertex) {
        if(!vertexIntegerMap.containsKey(vertex.getLabel())) {
            vertexIntegerMap.put(vertex.getLabel(), vertexList.size());
            vertexList.add(vertex);
        }
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        Integer index = vertexIntegerMap.remove(vertex.getLabel());
        vertexList.remove((int)index);
        for(int i = 0; i < adjacencyMatrix.length; i++) {
            adjacencyMatrix[index][i] = false;
            adjacencyMatrix[i][index] = false;
        }

//        for (int i = 0; i < edgeList.size(); i++) {
//            if (edgeList.get(i).getFrom().equals(vertex) || edgeList.get(i).getTo().equals(vertex)) {
//                edgeList.remove(i);
//                i--;
//            }
//        }

        edgeList.removeIf(edge -> edge.getFrom().equals(vertex) || edge.getTo().equals(vertex));

        for(int i = index; i < vertexList.size(); i++) {
            vertexIntegerMap.put(vertexList.get(i).getLabel(), i);
        }
    }

    @Override
    public void addEdge(Edge<V, E> edge) {
        int fromIndex = vertexIntegerMap.get(edge.getFrom().getLabel());
        int toIndex = vertexIntegerMap.get(edge.getTo().getLabel());
        adjacencyMatrix[fromIndex][toIndex] = true;
        edgeList.add(edge);
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        int fromIndex = vertexIntegerMap.get(edge.getFrom().getLabel());
        int toIndex = vertexIntegerMap.get(edge.getTo().getLabel());
        adjacencyMatrix[fromIndex][toIndex] = false;
        edgeList.remove(edge);
    }

    @Override
    public List<Vertex<V>> getNeighbors(Vertex<V> vertex) {
        List<Vertex<V>> neighbours = new ArrayList<>();
        int index = vertexIntegerMap.get(vertex.getLabel());
        for(int i = 0; i < adjacencyMatrix.length; i++) {
            if(adjacencyMatrix[index][i]) {
                neighbours.add(vertexList.get(i));
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
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формат числа: " + e.getMessage());
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof AdjacencyMatrixGraph<?,?>)) {
            return false;
        }

        AdjacencyMatrixGraph<?, ?> other = (AdjacencyMatrixGraph<?, ?>) obj;
        boolean vertexEquals = (other.vertices() == this.vertices()) ,
                edgesEquals = (other.edges() == this.edges());
        if(vertexEquals && edgesEquals) {
            for(int i = 0; i < vertexList.size(); i++) {
                vertexEquals = vertexEquals && (this.vertexList.get(i).equals(other.vertexList.get(i)));
            }

            for(int i = 0; i < edgeList.size(); i++) {
                edgesEquals = edgesEquals && (other.edgeList.get(i).equals(this.edgeList.get(i)));
            }
        }
        return (vertexEquals && edgesEquals);
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) adjacencyMatrix);
    }
}
