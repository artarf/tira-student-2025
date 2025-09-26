package oy.interact.tira.task_10;

import java.util.ArrayList;
import java.util.List;

import oy.interact.tira.student.graph.Edge;
import oy.interact.tira.student.graph.Graph;
import oy.interact.tira.student.graph.GraphPathListener;
import oy.interact.tira.student.graph.Vertex;
import oy.interact.tira.student.graph.Edge.EdgeType;

/**
 * Class to test the Graph implementation.
 */
public class IntegerTestGraph implements GraphPathListener<Integer> {

   private List<Vertex<Integer>> currentPath = new ArrayList<>();
   Graph<Integer> graph;

   public IntegerTestGraph() {
      graph = new Graph<>(this);
   }

   List<Vertex<Integer>> getCurrentPath() {
      return currentPath;
   }

   public List<Vertex<Integer>> doBreadthFirstSearch(Vertex<Integer> from) {
      return graph.breadthFirstSearch(from, null);
   }

   public List<Vertex<Integer>> doDepthFirstSearch(Vertex<Integer> from) {
      return graph.depthFirstSearch(from, null);
   }

   public Graph<Integer>.DijkstraResult<Integer> doDijkstra(Vertex<Integer> from, Vertex<Integer> to) {
      return graph.shortestPathDijkstra(from, to);
   }

   public static IntegerTestGraph createSimpleUndirectedGraph() {
      IntegerTestGraph testGraph = new IntegerTestGraph();
      // Undirected graph from lecture Graphs 1, slide "What is a graph?".
      for (int id = 1; id <= 5; id++) {
         testGraph.graph.createVertexFor(id);
      }
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(1), testGraph.graph.getVertexFor(2), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(3), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(4), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(5), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(3), testGraph.graph.getVertexFor(4), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(3), testGraph.graph.getVertexFor(5), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(4), testGraph.graph.getVertexFor(5), 1.0);
      System.out.println("Created simple undirected graph:");
      System.out.println(testGraph.graph.toString());
      return testGraph;
   }

   public static IntegerTestGraph createSimpleDirectedGraph() {
      IntegerTestGraph testGraph = new IntegerTestGraph();
      // Directed graph from lecture Graphs 1, slide "What is a graph?".
      for (int id = 1; id <= 5; id++) {
         testGraph.graph.createVertexFor(id);
      }
      testGraph.graph.addEdge(Edge.EdgeType.DIRECTED, testGraph.graph.getVertexFor(1), testGraph.graph.getVertexFor(2), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.DIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(4), 0.5);
      testGraph.graph.addEdge(Edge.EdgeType.DIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(5), 2.5);
      testGraph.graph.addEdge(Edge.EdgeType.DIRECTED, testGraph.graph.getVertexFor(3), testGraph.graph.getVertexFor(2), 4.0);
      testGraph.graph.addEdge(Edge.EdgeType.DIRECTED, testGraph.graph.getVertexFor(3), testGraph.graph.getVertexFor(5), 1.5);
      testGraph.graph.addEdge(Edge.EdgeType.DIRECTED, testGraph.graph.getVertexFor(4), testGraph.graph.getVertexFor(3), 11.5);
      testGraph.graph.addEdge(Edge.EdgeType.DIRECTED, testGraph.graph.getVertexFor(5), testGraph.graph.getVertexFor(4), 3.5);
      System.out.println("Created simple directed graph:");
      System.out.println(testGraph.graph.toString());
      return testGraph;
   }

   public static IntegerTestGraph createSimpleUndirectedDisconnectedGraph() {
      IntegerTestGraph testGraph = new IntegerTestGraph();
      // Undirected graph from lecture Graphs 1, slide "Disconnected or not?".
      for (int id = 1; id <= 5; id++) {
         testGraph.graph.createVertexFor(id);
      }
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(1), testGraph.graph.getVertexFor(2), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(3), 1.0);
      testGraph.graph.addEdge(Edge.EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(4), testGraph.graph.getVertexFor(5), 1.0);
      System.out.println("Created simple undirected graph:");
      System.out.println(testGraph.graph.toString());
      return testGraph;
   }

   public static IntegerTestGraph createGraphForDijkstraSearch() {
      IntegerTestGraph testGraph = new IntegerTestGraph();

      testGraph.graph.createVertexFor(1);
      testGraph.graph.createVertexFor(2);
      testGraph.graph.createVertexFor(3);
      testGraph.graph.createVertexFor(4);
      testGraph.graph.createVertexFor(5);
      testGraph.graph.createVertexFor(6);
      testGraph.graph.createVertexFor(7);
      
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(1), testGraph.graph.getVertexFor(2), 4.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(1), testGraph.graph.getVertexFor(5), 1.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(3), 11.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(5), 2.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(2), testGraph.graph.getVertexFor(6), 5.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(3), testGraph.graph.getVertexFor(6), 4.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(3), testGraph.graph.getVertexFor(4), 9.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(3), testGraph.graph.getVertexFor(7), 9.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(4), testGraph.graph.getVertexFor(7), 4.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(5), testGraph.graph.getVertexFor(6), 8.0);
      testGraph.graph.addEdge(EdgeType.UNDIRECTED, testGraph.graph.getVertexFor(6), testGraph.graph.getVertexFor(7), 7.0);
      System.out.println("Created graph for dijkstra testing:");
      System.out.println(testGraph.graph.toString());
      return testGraph;
   }

   @Override
   public void pathChanged(final Vertex<Integer> vertex) {
      System.out.format("  ...Advancing to vertex %s%n", vertex.getElement().toString());
   }


}
