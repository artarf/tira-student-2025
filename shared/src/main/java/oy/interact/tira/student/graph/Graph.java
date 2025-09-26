package oy.interact.tira.student.graph;

import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

import oy.interact.tira.student.graph.Edge.EdgeType;
import oy.interact.tira.util.NotYetImplementedException;

import java.util.ArrayDeque;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * Implementation of the graph data structure and associated algorithms.
 * <p>
 * This class implements the graph data structure and various
 * algorithms like breadth-first search, depth-first search and the Dijkstra
 * path finding algorithm.
 * <p>
 * The class needs your attention, dear student. Implement the methods
 * marked TODO in the comments, based on what you have learned about
 * graphs.
 * <p>
 * The Graph as a generic (template) class can use any data types conforming to 
 * the Comparable interface.
 * <p>
 * This implementation uses edge lists to store the graph vertices
 * and edges.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class Graph<T> {

   /** A listener that must be told whenever a search algorithm meets a vertex
    * it has not met before. This must be done in BFS and DFS algorithms.
    */
   private GraphPathListener<T> listener;

   /** The edge list of the graph. Select and instantiate
    * a suitable type of Map, depending on application needs.
    */
   private Map<Vertex<T>, List<Edge<T>>> edgeList = null;
   
   /**
    * Constructor instantiates a suitable Map data structure.
    *
    * A listener is provided as a parameter. Graph must notify the
    * listener when searching the graph, by calling listener.pathChanged(),
    * whenever a new vertex is met on the search path.
    */
   public Graph(GraphPathListener<T> listener) {
      this.listener = listener;
      // STUDENT TODO: remove throw and initialize the edge list for the graph
      throw new NotYetImplementedException("Graph not yet implemented");
   }

   /**
    * Clears the graph from all data.
    */
   public void clear() {
      // STUDENT TODO
   }

   /**
    * Returns the map of vertices with their edges.
    *
    * @return The edge list for the graph.
    */
   public Map<Vertex<T>, List<Edge<T>>> getEdgeList() {
      return edgeList;
   }

   /**
    * Creates a vertex holding the dataItem (node in a vertex) in the graph.
    * Use this method always to add vertices to the graph.
    *
    * Create the vertex object with the data item, then create an empty
    * list of edges, and put the vertex and the empty list to the Map.
    *
    * The newly created vertex must have an empty list of edges.
    * 
    * @param element The data item to put in the vertex of the graph.
    * @return Returns the created vertex, placed in the graph's edge list.
    */
   public Vertex<T> createVertexFor(T element) {
      // STUDENT TODO
      return null;
   }

   /**
    * Get all the vertices of the graph in a Set.

    * @return A Set with all the vertices of the graph.
    */
   public Set<Vertex<T>> getVertices() {
      // STUDENT TODO
      return null;
   }

   /**
    * Adds an edge to the graph. Note that the vertices MUST have been created 
    * earlier by calling {@code createVertexFor(T)} and are already in the graph.
    *
    * @param type The type of the edge, either directed or undirected.
    * @param source The source vertex of the edge.
    * @param destination The destination vertex of the edge.
    * @param weight The weight of the edge.
    */
   public void addEdge(Edge.EdgeType type, Vertex<T> source, Vertex<T> destination, double weight) {
      // STUDENT TODO
   }

   /**
    * Adds a directed edge to the graph. Note that the vertices MUST have been created 
    * earlier by calling  {@code createVertexFor(T)}.
    *
    * @param source The source vertex of the edge.
    * @param destination The destination vertex of the edge.
    * @param weight The weight of the edge.
    */
   public void addDirectedEdge(Vertex<T> source, Vertex<T> destination, double weight) {
      // STUDENT TODO
   }

   /**
    * Gets the edges of the specified vertex. The vertex must be
    * already in the graph.
    *
    * @param source The vertex edges of which we wish to get.
    * @return Returns the edges of the vertex or an empty list if no edges from the source.
    */
   public List<Edge<T>> getEdges(Vertex<T> source) {
      // STUDENT TODO
      return null;
   }

   /**
    * Gets a vertex for the specified node (contents) in a vertex, if found.
    * If the vertex with the node value is not found, returns null.
    * Use `equals` to search for the element from the vertices.
    *
    * @param element The value of T that is in some Vertex in the graph.
    * @return The vertex containing the node, or null if no vertex contains the element.
    */
   public Vertex<T> getVertexFor(T element) {
      // STUDENT TODO
      return null;
   }

   /**
    * Does a breadth first search on the graph.
    *
    * Algorithm MUST call listener.pathChanged(vertex) whenever a 
    * new vertex is visited when doing the search.
    *
    * If target is null, search is done for the whole graph. Otherwise,
    * search MUST be stopped when the target vertex is found.
    *
    * Returns a list of vertices in the order they were met in the graph.
    * If graph is empty, returns an empty list.
    * 
    * @param from   The vertex where the search is started from.
    * @param target An optional ending vertex, null if not given.
    * @return Returns all the visited vertices traversed while doing BFS, in order
    *         they were found.
    */
   public List<Vertex<T>> breadthFirstSearch(Vertex<T> from, Vertex<T> target) {
      // STUDENT TODO      
      return null;
   }

   /**
    * Does depth first search (DFS) of the graph starting from a vertex.
    * 
    * Algorithm MUST call listener.pathChanged(vertex) whenever a 
    * new vertex is visited when doing the search.
    *
    * If target is null, search is done for the whole graph. Otherwise,
    * search MUST be stopped when the target vertex is found.
    *
    * Returns a list of vertices in the order they were met in the graph.
    * If graph is empty, returns an empty list.
    * 
    * @param from   The vertex where the search is started from.
    * @param target An optional ending vertex, null if not given.
    * @return Returns all the visited vertices traversed while doing DFS.
    */
   public List<Vertex<T>> depthFirstSearch(Vertex<T> from, Vertex<T> target) {
      // STUDENT TODO
      return null;
   }
   
   // Dijkstra starts here.

   /**
    * The result of the Dijkstra's search.
    */
   public class DijkstraResult<E> {
      public boolean pathFound = false;
      public List<E> path;
      public int steps = 0;
      public double totalWeigth = 0.0;

      @Override
      public String toString() {
         StringBuilder builder = new StringBuilder();
         builder.append(String.format("Dikstra result:\n- Path found: %s%n", (pathFound ? "yes" : "no")));
         if (pathFound) {
            builder.append(String.format("- steps: %d%n", steps));
            builder.append(String.format("- total edge weights: %.2f%n", totalWeigth));
            if (null != path) {
               builder.append(String.format("- path: %s", path.toString()));
            } else {
               builder.append("Path not found\n");
            }
         }
         return builder.toString();
      }
   }

   /**
    * Finds the shortest path from start to end using Dijkstra's algorithm.
    * 
    * NOTE: Always when you add an element to the path found,
    * you MUST call listener.pathChanged with the vertex of the element.
    *
    * Call listener.pathChanged from the route's source and destination vertices,
    * as you find them. Ask help if you do not know how to do it, or the
    * animation in the GUI does not look right.
    *
    * The return value contains information about the shortest path found.
    *
    * @param start The vertex to start from.
    * @param end The vertex to search the shortest path to.
    * @return An object containing information about the result of the search.
    */
   public DijkstraResult<T> shortestPathDijkstra(Vertex<T> start, Vertex<T> end) {
      DijkstraResult<T> result = new DijkstraResult<>();
      result.pathFound = false; // set this to true if a path is found in search
      result.path = null;
      result.steps = 0;
      result.totalWeigth = 0.0;
      // This current path will then be returned with DijkstraResult as the path found
      ArrayList<T> currentPath = new ArrayList<>();
      // STUDENT: TODO implement the algorithm below


      // STUDENT: implement the algorithm above under the previous comment.
      if (result.pathFound) {
         result.path = currentPath;
      }
      return result;
   }


   /**
    * Finds a route to a destination using paths already constructed.
    * Before calling this method, cal {@link shortestPathsFrom} to construct
    * the paths from the staring vertex of Dijkstra algorithm.
    *
    * A helper method for implementing the Dijkstra algorithm.
    * 
    * @param toDestination The destination vertex to find the route to.
    * @param paths The paths to search the destination.
    * @return Returns the vertices forming the route to the destination.
    */
   public List<Edge<T>> route(Vertex<T> toDestination, Map<Vertex<T>, Visit<T>> paths) {
      List<Edge<T>> path = new ArrayList<>();
      // STUDENT: TODO implement the algorithm below

      return path;
   }
   
   /**
    * Finds the shortest paths in the graph from the starting vertex.
    *
    * In doing Dijkstra, first call this method, then call {@link route}
    * with the paths collected using this method, to get the shortest path to the destination.
    *
    * @param start The starting vertex for the path searching.
    * @return Returns the visits from the starting vertex.
    * @see oy.tol.tira.graph.Graph#route(Vertex, Map)
    */
   public Map<Vertex<T>, Visit<T>> shortestPathsFrom(Vertex<T> start) {
      Visit<T> visit = new Visit<>();
      visit.type = Visit.Type.START;
      Map<Vertex<T>, Visit<T>> paths = new HashMap<>();

      paths.put(start, visit);

      // STUDENT: TODO implement the algorithm below using:
      // - a priority queue
      // - helper method distance you implement

      return paths;
   }

   // STUDENTS: TODO: Uncomment the code below and use it as a sample on how
   // to interate over vertices and edges in one situation.
   // If you use some other name for your edge list than edgeList, then
   // rename that in the code below! Otherwise you will have compiler errors.
   /**
    * Provides a string representation of the graph, printing  out the vertices and edges.
    * <p>
    * Quite useful if you need to debug issues with algorithms. You can see is the graph
    * what it is supposed to be like.
    * <p>
    * Simple graph as a string would look like this:<br/>
    * <pre>
    * Created simple undirected graph where integers are vertice values:
    * [1] -> [ 2 ]
    * [2] -> [ 1, 3, 4, 5 ]
    * [3] -> [ 2, 4, 5 ]
    * [4] -> [ 2, 3, 5 ]
    * [5] -> [ 2, 3, 4 ]
    * </pre> 
    * @return The graph as a string.
    */
   @Override
   public String toString() {
      StringBuilder output = new StringBuilder();
      // for (Map.Entry<Vertex<T>, List<Edge<T>>> entry : edgeList.entrySet()) {
      //    output.append("[");
      //    output.append(entry.getKey().toString());
      //    output.append("] -> [ ");
      //    int counter = 0;
      //    int count = entry.getValue().size();
      //    for (Edge<T> edge : entry.getValue()) {
      //       output.append(edge.getDestination().toString());
      //       if (counter < count - 1) {
      //          output.append(", ");
      //       }
      //       counter++;
      //    }
      //    output.append(" ]\n");
      // }
      return output.toString();
   }
}
