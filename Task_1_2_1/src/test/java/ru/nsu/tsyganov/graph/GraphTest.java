package ru.nsu.tsyganov.graph;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GraphTest {
    private Graph<String, String> graphAdjMat;
    private Graph<String, String> graphAdjList;
    private Graph<String, String> graphIncMat;
    private Edge<String, String> edgeFtoA;
    private Edge<String, String> edgeEtoA;
    private Edge<String, String> edgeFtoC;
    private Edge<String, String> edgeEtoB;
    private Edge<String, String> edgeCtoD;
    private Edge<String, String> edgeDtoB;
    private Vertex<String> vertexA;
    private Vertex<String> vertexB;
    private Vertex<String> vertexC;
    private Vertex<String> vertexD;
    private Vertex<String> vertexE;
    private Vertex<String> vertexF;
    private String filename;

    @BeforeEach
    void setup() throws URISyntaxException {
        graphAdjMat = new AdjacencyMatrixGraph<>(6);
        graphAdjList = new AdjacencyListGraph<>();
        graphIncMat = new IncidenceMatrixGraph<>(6);
        vertexA = new Vertex<>("A");
        vertexB = new Vertex<>("B");
        vertexC = new Vertex<>("C");
        vertexD = new Vertex<>("D");
        vertexE = new Vertex<>("E");
        vertexF = new Vertex<>("F");
        edgeFtoA = new Edge<>(vertexF, vertexA, "(F, A)", (double) 1);
        edgeEtoA = new Edge<>(vertexE, vertexA, "(E, A)", (double) 2);
        edgeFtoC = new Edge<>(vertexF, vertexC, "(F, C)", (double) 3);
        edgeEtoB = new Edge<>(vertexE, vertexB, "(E, B)", (double) 4);
        edgeCtoD = new Edge<>(vertexC, vertexD, "(C, D)", (double) 5);
        edgeDtoB = new Edge<>(vertexD, vertexB, "(D, B)", (double) 6);
        URL res =
                GraphTest.class.getClassLoader().getResource("Graph.txt");
        assert res != null;
        File file = Paths.get(res.toURI()).toFile();
        filename = file.getAbsolutePath();
    }

    @Test
    void checkAdjMatFileRead() {
        graphAdjMat.readFromFile(filename);
        Graph<String, String> graph = new AdjacencyMatrixGraph<>(6);
        graph.addVertex(vertexF);
        graph.addVertex(vertexA);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFtoA);
        graph.addEdge(edgeEtoA);
        graph.addEdge(edgeFtoC);
        graph.addEdge(edgeEtoB);
        graph.addEdge(edgeCtoD);
        graph.addEdge(edgeDtoB);
        assertEquals(graphAdjMat, graph);
    }

    @Test
    void checkAdjListFileRead() {
        graphAdjList.readFromFile(filename);
        Graph<String, String> graph = new AdjacencyListGraph<>();
        graph.addVertex(vertexF);
        graph.addVertex(vertexA);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFtoA);
        graph.addEdge(edgeEtoA);
        graph.addEdge(edgeFtoC);
        graph.addEdge(edgeEtoB);
        graph.addEdge(edgeCtoD);
        graph.addEdge(edgeDtoB);
        assertEquals(graphAdjList, graph);
    }

    @Test
    void checkIncMatFileRead() {
        graphIncMat.readFromFile(filename);
        Graph<String, String> graph = new IncidenceMatrixGraph<>(6);
        graph.addVertex(vertexF);
        graph.addVertex(vertexA);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFtoA);
        graph.addEdge(edgeEtoA);
        graph.addEdge(edgeFtoC);
        graph.addEdge(edgeEtoB);
        graph.addEdge(edgeCtoD);
        graph.addEdge(edgeDtoB);
        assertEquals(graphIncMat, graph);
    }

    @Test
    void checkTopSortAdjMat() {
        graphAdjMat.readFromFile(filename);
        List<Vertex<String>> vertexList = new ArrayList<>();
        vertexList.add(vertexE); // [E, F, C, D, B, A]
        vertexList.add(vertexF);
        vertexList.add(vertexC);
        vertexList.add(vertexD);
        vertexList.add(vertexB);
        vertexList.add(vertexA);
        Alg<String, String> tsort = new TopSort<>();
        List<Vertex<String>> sortedList = tsort.perform(graphAdjMat);
        assertEquals(sortedList, vertexList);
    }

    @Test
    void checkTopSortAdjList() {
        graphAdjList.readFromFile(filename);
        List<Vertex<String>> vertexList = new ArrayList<>();
        vertexList.add(vertexE); // [E, F, C, D, B, A]
        vertexList.add(vertexF);
        vertexList.add(vertexC);
        vertexList.add(vertexD);
        vertexList.add(vertexB);
        vertexList.add(vertexA);
        Alg<String, String> tsort = new TopSort<>();
        List<Vertex<String>> sortedList = tsort.perform(graphAdjList);
        assertEquals(sortedList, vertexList);
    }

    @Test
    void checkTopSortIncMat() {
        graphIncMat.readFromFile(filename);
        List<Vertex<String>> vertexList = new ArrayList<>();
        vertexList.add(vertexE); // [E, F, C, D, B, A]
        vertexList.add(vertexF);
        vertexList.add(vertexC);
        vertexList.add(vertexD);
        vertexList.add(vertexB);
        vertexList.add(vertexA);
        Alg<String, String> tsort = new TopSort<>();
        List<Vertex<String>> sortedList = tsort.perform(graphIncMat);
        assertEquals(sortedList, vertexList);
    }

    @Test
    void removePartsAdjMat() {
        graphAdjMat.readFromFile(filename);
        graphAdjMat.removeVertex(vertexA);
        graphAdjMat.removeEdge(edgeDtoB);
        Graph<String, String> graph = new AdjacencyMatrixGraph<>(6);
        graph.addVertex(vertexF);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFtoC);
        graph.addEdge(edgeEtoB);
        graph.addEdge(edgeCtoD);
        assertEquals(graphAdjMat, graph);
    }

    @Test
    void removePartsAdjList() {
        graphAdjList.readFromFile(filename);
        graphAdjList.removeVertex(vertexA);
        graphAdjList.removeEdge(edgeDtoB);
        Graph<String, String> graph = new AdjacencyListGraph<>();
        graph.addVertex(vertexF);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFtoC);
        graph.addEdge(edgeEtoB);
        graph.addEdge(edgeCtoD);
        assertEquals(graphAdjList, graph);
    }

    @Test
    void removePartsIncMat() {
        graphIncMat.readFromFile(filename);
        graphIncMat.removeVertex(vertexA);
        graphIncMat.removeEdge(edgeDtoB);
        Graph<String, String> graph = new IncidenceMatrixGraph<>(6);
        graph.addVertex(vertexF);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFtoC);
        graph.addEdge(edgeEtoB);
        graph.addEdge(edgeCtoD);
        assertEquals(graphIncMat, graph);
    }
}