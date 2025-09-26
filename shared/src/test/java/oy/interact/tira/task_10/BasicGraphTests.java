package oy.interact.tira.task_10;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.condition.EnabledIf;

import oy.interact.tira.student.graph.Edge;
import oy.interact.tira.student.graph.Vertex;
import oy.interact.tira.util.NotYetImplementedException;


/**
 * Basic unit tests for the Graph class.
 * Tests if the graph has the vertices and edges it should have.
 */
@DisplayName("Basic tests for creating a graph correctly")
@EnabledIf("checkIfImplemented")
class BasicGraphTests {

    static boolean checkIfImplemented() {
		try {
            new IntegerTestGraph();
        } catch (NotYetImplementedException e) {
            return false;
        }
        return true;
	}

    @Test
    @Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testSimpleGraph()
    {
        IntegerTestGraph testGraph = IntegerTestGraph.createSimpleUndirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        Set<Vertex<Integer>> vertices = testGraph.graph.getVertices();
        assertEquals(5, vertices.size(), () -> "Graph does not have the expected number of vertices");
        for (Vertex<Integer> vertex : vertices) {
            List<Edge<Integer>> edges = testGraph.graph.getEdges(vertex);
            switch (vertex.getElement()) {
                case 1:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    break;
                case 2:
                    assertEquals(4, edges.size(), () -> "Vertex does not have the expected number of edges");
                    break;
                case 3, 4, 5:
                    assertEquals(3, edges.size(), () -> "Vertex does not have the expected number of edges");
                    break;
                default:
                    fail("Vertex ids should be 1..5");
                break;
            }
        }
    }

    @Test 
    @Timeout(value = 60, threadMode = Timeout.ThreadMode.SEPARATE_THREAD)
    void testSimpleDirectedGraph()
    {
        IntegerTestGraph testGraph = IntegerTestGraph.createSimpleDirectedGraph();
        assertNotNull(testGraph, () -> "Test graph not created");
        Set<Vertex<Integer>> vertices = testGraph.graph.getVertices();
        assertEquals(5, vertices.size(), () -> "Graph does not have the expected number of vertices");
        for (Vertex<Integer> vertex : vertices) {
            List<Edge<Integer>> edges = testGraph.graph.getEdges(vertex);
            switch (vertex.getElement()) {
                case 1:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    assertEquals(1.0, edges.get(0).getWeigth(), () -> "Edge weight was not correct");
                    break;
                case 2:
                    assertEquals(2, edges.size(), () -> "Vertex does not have the expected number of edges");
                    double weight = edges.get(0).getWeigth() + edges.get(1).getWeigth();
                    assertEquals(3.0, weight, () -> "Edge weight was not correct");
                    break;
                case 3:
                    assertEquals(2, edges.size(), () -> "Vertex does not have the expected number of edges");
                    weight = edges.get(0).getWeigth() + edges.get(1).getWeigth();
                    assertEquals(5.5, weight, () -> "Edge weight was not correct");
                    break;
                case 4:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    assertEquals(11.5, edges.get(0).getWeigth(), () -> "Edge weight was not correct");
                    break;
                case 5:
                    assertEquals(1, edges.size(), () -> "Vertex does not have the expected number of edges");
                    assertEquals(3.5, edges.get(0).getWeigth(), () -> "Edge weight was not correct");
                    break;
                default:
                    fail("Vertex ids should be 1..5");
                    break;
            }
        }
    }

}
