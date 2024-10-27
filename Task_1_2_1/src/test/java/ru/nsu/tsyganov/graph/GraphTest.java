package ru.nsu.tsyganov.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GraphTest{
    private Graph<String, String> graphAM;
    private Graph<String, String> graphAL;
    private Graph<String, String> graphIM;
    private Edge<String, String> edgeFA;
    private Edge<String, String> edgeEA;
    private Edge<String, String> edgeFC;
    private Edge<String, String> edgeEB;
    private Edge<String, String> edgeCD;
    private Edge<String, String> edgeDB;
    private Vertex<String> vertexA;
    private Vertex<String> vertexB;
    private Vertex<String> vertexC;
    private Vertex<String> vertexD;
    private Vertex<String> vertexE;
    private Vertex<String> vertexF;
    private String filename;

    @BeforeEach
    void setup() throws URISyntaxException {
        graphAM = new AdjacencyMatrixGraph<>(6);
        graphAL = new AdjacencyListGraph<>();
        graphIM = new IncidenceMatrixGraph<>(6);
        vertexA = new Vertex<>("A");
        vertexB = new Vertex<>("B");
        vertexC = new Vertex<>("C");
        vertexD = new Vertex<>("D");
        vertexE = new Vertex<>("E");
        vertexF = new Vertex<>("F");
        edgeFA = new Edge<>(vertexF, vertexA, "(F, A)", (double)1);
        edgeEA = new Edge<>(vertexE, vertexA, "(E, A)", (double)2);
        edgeFC = new Edge<>(vertexF, vertexC, "(F, C)", (double)3);
        edgeEB = new Edge<>(vertexE, vertexB, "(E, B)", (double)4);
        edgeCD = new Edge<>(vertexC, vertexD, "(C, D)", (double)5);
        edgeDB = new Edge<>(vertexD, vertexB, "(D, B)", (double)6);
        URL res =
                GraphTest.class.getClassLoader().getResource("Graph.txt");
        assert res != null;
        File file = Paths.get(res.toURI()).toFile();
        filename = file.getAbsolutePath();
    }

    @Test
    void checkAMFileRead() {
        graphAM.readFromFile(filename);
        Graph<String, String> graph = new AdjacencyMatrixGraph<>(6);
        graph.addVertex(vertexF);
        graph.addVertex(vertexA);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFA);
        graph.addEdge(edgeEA);
        graph.addEdge(edgeFC);
        graph.addEdge(edgeEB);
        graph.addEdge(edgeCD);
        graph.addEdge(edgeDB);
        assertEquals(graphAM, graph);
    }

    @Test
    void checkALFileRead() {
        graphAL.readFromFile(filename);
        Graph<String, String> graph = new AdjacencyListGraph<>();
        graph.addVertex(vertexF);
        graph.addVertex(vertexA);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFA);
        graph.addEdge(edgeEA);
        graph.addEdge(edgeFC);
        graph.addEdge(edgeEB);
        graph.addEdge(edgeCD);
        graph.addEdge(edgeDB);
        assertEquals(graphAL, graph);
    }

    @Test
    void checkIMFileRead() {
        graphIM.readFromFile(filename);
        Graph<String, String> graph = new IncidenceMatrixGraph<>(6);
        graph.addVertex(vertexF);
        graph.addVertex(vertexA);
        graph.addVertex(vertexE);
        graph.addVertex(vertexC);
        graph.addVertex(vertexB);
        graph.addVertex(vertexD);
        graph.addEdge(edgeFA);
        graph.addEdge(edgeEA);
        graph.addEdge(edgeFC);
        graph.addEdge(edgeEB);
        graph.addEdge(edgeCD);
        graph.addEdge(edgeDB);
        assertEquals(graphIM, graph);
    }

    @Test
    void checkTopSortAM() {
        Alg<String, String> tsort = new topSort<>();
        graphAM.readFromFile(filename);
        List<Vertex<String>> vertexList = new ArrayList<>();
        vertexList.add(vertexE); // [E, F, C, D, B, A]
        vertexList.add(vertexF);
        vertexList.add(vertexC);
        vertexList.add(vertexD);
        vertexList.add(vertexB);
        vertexList.add(vertexA);
        List<Vertex<String>> sortedList = tsort.perform(graphAM);
        assertEquals(sortedList, vertexList);
    }

    @Test
    void checkTopSortAL() {
        Alg<String, String> tsort = new topSort<>();
        graphAL.readFromFile(filename);
        List<Vertex<String>> vertexList = new ArrayList<>();
        vertexList.add(vertexE); // [E, F, C, D, B, A]
        vertexList.add(vertexF);
        vertexList.add(vertexC);
        vertexList.add(vertexD);
        vertexList.add(vertexB);
        vertexList.add(vertexA);
        List<Vertex<String>> sortedList = tsort.perform(graphAL);
        assertEquals(sortedList, vertexList);
    }

    @Test
    void checkTopSortIM() {
        Alg<String, String> tsort = new topSort<>();
        graphIM.readFromFile(filename);
        List<Vertex<String>> vertexList = new ArrayList<>();
        vertexList.add(vertexE); // [E, F, C, D, B, A]
        vertexList.add(vertexF);
        vertexList.add(vertexC);
        vertexList.add(vertexD);
        vertexList.add(vertexB);
        vertexList.add(vertexA);
        List<Vertex<String>> sortedList = tsort.perform(graphIM);
        assertEquals(sortedList, vertexList);
    }
}