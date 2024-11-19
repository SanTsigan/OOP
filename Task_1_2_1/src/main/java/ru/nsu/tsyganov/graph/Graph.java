package ru.nsu.tsyganov.graph;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * Graph interface.
 */
public interface Graph<V, E> {
    int vertices();

    int edges();

    List<Vertex<V>> vertexList();

    void addVertex(Vertex<V> vertex);

    void removeVertex(Vertex<V> vertex);

    void addEdge(Edge<V, E> edge);

    void removeEdge(Edge<V, E> edge);

    List<Vertex<V>> getNeighbors(Vertex<V> vertex);

    default void readFromFile(String filename, Function<String, V> vertexParser){
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
                            (E) ("(" + parts[0] + ", " + parts[1] + ")"),
                            weight));
                }
            }
        } catch (IOException e) {
            System.out.println("Ошибка чтения файла: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Ошибка формат числа: " + e.getMessage());
        }
    }
}
