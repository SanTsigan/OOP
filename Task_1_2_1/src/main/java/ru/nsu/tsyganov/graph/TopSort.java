package ru.nsu.tsyganov.graph;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class TopSort<V, E> implements Alg<V, E> {
    private void dfs(Graph<V, E> g, Vertex<V> v, boolean[] visited, Deque<Vertex<V>> stack) {
        int index = g.vertexList().indexOf(v);
        visited[index] = true;

        for (Vertex<V> neighbour : g.getNeighbors(v)) {
            if (!visited[g.vertexList().indexOf(neighbour)]) {
                dfs(g, neighbour, visited, stack);
            }
        }

        stack.addFirst(v); //push
    }

    @Override
    public List<Vertex<V>> perform(Graph<V, E> g) {
        Deque<Vertex<V>> stack = new ArrayDeque<>();
        boolean[] visited = new boolean[g.vertices()];

        for (int i = g.vertexList().indexOf(g.vertexList().getFirst()); i < g.vertices(); i++) {
            if (!visited[i]) {
                dfs(g, g.vertexList().get(i), visited, stack);
            }
        }

        List<Vertex<V>> sortedOrder = new ArrayList<>();
        while (!stack.isEmpty()) {
            sortedOrder.add(stack.removeFirst()); //pop
        }
        return sortedOrder;
    }
}
