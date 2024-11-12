package ru.nsu.tsyganov.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * Realisation of interface Graph through Adjacency List.
 */
public class AdjacencyListGraph<V, E> implements Graph<V, E> {
    private Map<Vertex<V>, List<Vertex<V>>> adjList;
    private List<Vertex<V>> vertexList;
    private List<Edge<V, E>> edgeList;

    /**
     * constructor.
     */
    public AdjacencyListGraph() {
        adjList = new HashMap<>();
        vertexList = new ArrayList<>();
        edgeList = new ArrayList<>();
    }

    public Map<Vertex<V>, List<Vertex<V>>> getAdjList() {
        return adjList;
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
    public List<Vertex<V>> vertexList() {
        return vertexList;
    }

    @Override
    public void addVertex(Vertex<V> vertex) {
        adjList.putIfAbsent(vertex, new ArrayList<>());
        if (!vertexList.contains(vertex)) {
            vertexList.add(vertex);
        }
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        adjList.remove(vertex);
        for (List<Vertex<V>> neighbours : adjList.values()) {
            neighbours.remove(vertex);
        }

        edgeList.removeIf(edge -> edge.getFrom().equals(vertex) || edge.getTo().equals(vertex));

        vertexList.remove(vertex);
    }

    @Override
    public void addEdge(Edge<V, E> edge) {
        adjList.putIfAbsent(edge.getFrom(), new ArrayList<>());
        adjList.putIfAbsent(edge.getTo(), new ArrayList<>());
        adjList.get(edge.getFrom()).add(edge.getTo());
        edgeList.add(edge);
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        List<Vertex<V>> neighbors = adjList.get(edge.getFrom());
        if (neighbors != null) {
            neighbors.remove(edge.getTo());
        }
        edgeList.remove(edge);
    }


    @Override
    public List<Vertex<V>> getNeighbors(Vertex<V> vertex) {
        return adjList.getOrDefault(vertex, new ArrayList<>());
    }

    @Override
    public void readFromFile(String filename, Function<String, V> vertexParser) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    Vertex<V> from = new Vertex<>(vertexParser.apply(parts[0]));
                    Vertex<V> to = new Vertex<>(vertexParser.apply(parts[1]));
                    Double weight = Double.parseDouble(parts[2]);

                    addVertex(from);
                    addVertex(to);
                    addEdge(new Edge<>(from, to,
                            (E)("(" + parts[0] + ", " + parts[1] + ")"),
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
        } else if (!(obj instanceof AdjacencyListGraph<?, ?>)) {
            return false;
        }

        AdjacencyListGraph<?, ?> other = (AdjacencyListGraph<?, ?>) obj;
        boolean vertexEquals = (other.vertices() == this.vertices());
        boolean edgesEquals = (other.edges() == this.edges());
        if (vertexEquals && edgesEquals) {
            for (int i = 0; i < vertexList.size(); i++) {
                vertexEquals &= (other.vertexList.get(i).equals(this.vertexList.get(i)));
            }

            for (int i = 0; i < edgeList.size(); i++) {
                edgesEquals &= (other.edgeList.get(i).equals(this.edgeList.get(i)));
            }
        }
        return (vertexEquals && edgesEquals);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjList);
    }
}
