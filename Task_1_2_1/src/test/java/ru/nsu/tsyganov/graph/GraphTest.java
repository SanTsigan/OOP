package ru.nsu.tsyganov.graph;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    void setup() {
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
        filename = String.valueOf(
                GraphTest.class.getClassLoader().getResource("Graph.txt"));
    }

    @Test
    void checkAMFileRead() {
        graphAM.readFromFile(filename);
//        graphAM.readFromFile("/home/aleksander/Документы/Универ/OOP" +
//                "/OOP/Task_1_2_1/src/test/resources/Graph.txt");
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
//        graphAL.readFromFile("/home/aleksander/Документы/Универ/OOP" +
//                "/OOP/Task_1_2_1/src/test/resources/Graph.txt");
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
//        graphIM.readFromFile("/home/aleksander/Документы/Универ/OOP" +
//                "/OOP/Task_1_2_1/src/test/resources/Graph.txt");
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
}