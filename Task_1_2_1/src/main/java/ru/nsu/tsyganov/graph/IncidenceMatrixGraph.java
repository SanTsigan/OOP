package ru.nsu.tsyganov.graph;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class IncidenceMatrixGraph<V, E> implements Graph<V, E> {
    private boolean[][] incidenceMatrix;
    private List<Vertex<V>> vertices;
    private int edgeCount;
    private List<Edge<V, E>> edges;

    public IncidenceMatrixGraph(int size) {
        incidenceMatrix = new boolean[size][size];
        vertices = new ArrayList<>();
        edgeCount = 0;
        edges = new ArrayList<>();
    }

    public boolean[][] getIncidenceMatrix() {
        return incidenceMatrix;
    }

    @Override
    public int vertices() {
        return vertices.size();
    }

    @Override
    public int edges() {
        return edges.size();
    }

    @Override
    public List<Vertex<V>> vertexList() {
        return vertices;
    }

    @Override
    public void addVertex(Vertex<V> vertex) {
        if (!vertices.contains(vertex)) {
            vertices.add(vertex);
            boolean[][] newMatrix = new boolean[vertices.size()][edgeCount];
            for (int i = 0; i < vertices.size() - 1; i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
            }
            incidenceMatrix = newMatrix;
        }
    }

    @Override
    public void removeVertex(Vertex<V> vertex) {
        int index = vertices.indexOf(vertex);
        if (index != -1) {
            vertices.remove(index);
            boolean[][] newMatrix = new boolean[vertices.size()][edgeCount];
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < edgeCount; j++) {
                    if (i < index) {
                        newMatrix[i][j] = incidenceMatrix[i][j];
                    } else if (i > index) {
                        newMatrix[i - 1][j] = incidenceMatrix[i][j];
                    }
                }
            }
            incidenceMatrix = newMatrix;
            edges.removeIf(edge -> edge.getFrom().equals(vertex) || edge.getTo().equals(vertex));
            edgeCount = edges.size();
        }
    }

    @Override
    public void addEdge(Edge<V, E> edge) {
        int fromIndex = vertices.indexOf(edge.getFrom());
        int toIndex = vertices.indexOf(edge.getTo());

        if (fromIndex != -1 && toIndex != -1) {
            boolean[][] newMatrix = new boolean[vertices.size()][edgeCount + 1];
            for (int i = 0; i < vertices.size(); i++) {
                System.arraycopy(incidenceMatrix[i], 0, newMatrix[i], 0, edgeCount);
            }
            newMatrix[fromIndex][edgeCount] = true; // от from к edgeCount
            newMatrix[toIndex][edgeCount] = false; // от edgeCount к to

            incidenceMatrix = newMatrix;
            edges.add(edge);
            edgeCount++;
        }
    }

    @Override
    public void removeEdge(Edge<V, E> edge) {
        int fromIndex = vertices.indexOf(edge.getFrom());
        int toIndex = vertices.indexOf(edge.getTo());

        if (fromIndex != -1 && toIndex != -1) {
            boolean[][] newMatrix = new boolean[vertices.size()][edgeCount - 1];
            for (int i = 0; i < vertices.size(); i++) {
                for (int j = 0; j < edgeCount; j++) {
                    if (j < edgeCount - 1) {
                        newMatrix[i][j] = incidenceMatrix[i][j];
                    }
                }
            }
            incidenceMatrix = newMatrix;
            edges.remove(edge);
            edgeCount--;
        }
    }

    @Override
    public List<Vertex<V>> getNeighbors(Vertex<V> vertex) {
        List<Vertex<V>> neighbors = new ArrayList<>();

        for (Edge<V, E> edge : edges) {
            if (edge.getFrom().equals(vertex)) {
                neighbors.add(edge.getTo());
            }
        }

        return neighbors;
    }

    @Override
    public void readFromFile(String filename) {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length == 3) {
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof IncidenceMatrixGraph<?, ?>)) {
            return false;
        }

        IncidenceMatrixGraph<?, ?> other = (IncidenceMatrixGraph<?, ?>) obj;
        boolean vertexEquals = (other.vertices() == this.vertices());
        boolean edgesEquals = (other.edges() == this.edges());
        if (vertexEquals && edgesEquals) {
            for (int i = 0; i < vertices.size(); i++) {
                vertexEquals &= (other.vertices.get(i).equals(this.vertices.get(i)));
            }

            for (int i = 0; i < edges.size(); i++) {
                edgesEquals &= (other.edges.get(i).equals(this.edges.get(i)));
            }
        }
        return (vertexEquals && edgesEquals);
    }

    @Override
    public int hashCode() {
        return Objects.hash((Object) incidenceMatrix);
    }

}
