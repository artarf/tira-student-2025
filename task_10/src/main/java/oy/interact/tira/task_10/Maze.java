package oy.interact.tira.task_10;

import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import oy.interact.tira.student.graph.Edge;
import oy.interact.tira.student.graph.Edge.EdgeType;
import oy.interact.tira.student.graph.Graph;
import oy.interact.tira.student.graph.GraphPathListener;
import oy.interact.tira.student.graph.Vertex;
import oy.interact.tira.task_10.MazeObserver.Event;

import java.util.Map;
import java.util.List;

/**
 * Maze is a sublcass of Graph to model rectangular graph of a maze.
 * <p>
 * Maze assumes the following limitations and implementation choices from graph data structure:
 * <ul>
 *  <li>All edges are undirectional.
 *  <li>{@code MazeNode} is used as the content (template parameter) of the vertices.
 *  <li>Vertices are drawn in a rectangle, based on the coordinates in {@code MazeNode}.
 *  <li>Graph is implemented as an <strong>edge list</strong>.
 * </ul>
 * Maze implements the role of the Subject in the Observer design pattern.
 * <p>
 * The various algorithms and the graph construction are animated. The animation speed is
 * adjustable. Animation is implemented in {@link #addToPath(Vertex)} which MUST be called 
 * by the algorithm implementations in Graph. After each call to addToPath, the algorithm
 * execution thread sleeps for the specified duration, allowing the GUI to update the UI
 * as new vertex was added to the path to animate.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class Maze implements GraphPathListener<MazeNode> {

   private Graph<MazeNode> graph;
   private int width = 0;
   private int height = 0;
   private List<MazeObserver> observers = null;
   private List<Vertex<MazeNode>> currentPath = null;
   private Vertex<MazeNode> start = null;
   private Vertex<MazeNode> end = null;

   /** When animation has speed none, no animations are shown, the view is constructed without any delay. */
   public static final int ANIMATION_SPEED_NONE = -1;
   /** For fast animations of the algorithms. */
   public static final int ANIMATION_SPEED_FAST = 50;
   /** For medium fast animations of the algorithms. */
   public static final int ANIMATION_SPEED_MEDIUM = 150;
   /** For slow animations of the algorithms. */
   public static final int ANIMATION_SPEED_SLOW = 200;

   private int animationSpeed = ANIMATION_SPEED_FAST;

   private boolean inGameMode = false;

   /**
    * Constructor for the Maze.
    * Calls the super class constructor and allocates room for observers.
    */
   public Maze() {
      graph = new Graph<>(this);
      observers = new ArrayList<>();
      currentPath = new ArrayList<>();
   }

   /**
    * Gets the graph of the Maze.
    *
    * @return The graph data structure
    */
   public Graph<MazeNode> getGraph() {
      return graph;
   }

   /**
    * Adds a new observer to the subject.
    * @param observer The observer.
    */
   public void addObserver(MazeObserver observer) {
      observers.add(observer);
   }

   /**
    * Removes an observer from the observers.
    * @param observer The observer to remove.
    */
   public void removeObserver(MazeObserver observer) {
      observers.remove(observer);
   }

   /**
    * Notifies all observers of a state change in the Maze.
    * @param event The event notified about.
    */
   private void notifyObservers(MazeObserver.Event event) {
      for (MazeObserver observer : observers) {
         observer.update(event);
      }
   }

   /**
    * Notifies all observers of a state change in the Maze.
    * @param event The event notified about.
    * @param message The message related to the event, shown to the user.
    */
    private void notifyObservers(MazeObserver.Event event, String message) {
      for (MazeObserver observer : observers) {
         observer.update(event, message);
      }
   }

   /**
    * The current animation speed.
    * @return Current animation speed.
    */
   public int getAnimationSpeed() {
      return animationSpeed;
   }

   /**
    * Changes the current animation speed.
    * <p>
    * The animation speed is the pause in milliseconds until the next
    * step in the algorithm is taken.
    * @param newSpeed The new animation speed in milliseconds.
    */
   public void setAnimationSpeed(int newSpeed) {
      animationSpeed = newSpeed;
   }

   /**
    * Creates a maze as a graph, notifying the observers of the process.
    * <p>
    * Maze has width and height, specifying the number of vertices placed
    * horizontally and vertically in the maze.
    * <p>
    * If no random edges are created, then the caller MUST add the edges to the
    * maze graph later. 
    * @param width The width of the maze.
    * @param height The height of the maze.
    * @param createRandomEdges If true, random edges are created for the maze.
    * @throws Exception If something goes wrong, an execption is thrown.
    */
   public void createMaze(int width, int height, boolean createRandomEdges) throws Exception {
      assert (observers != null);
      this.width = width;
      this.height = height;

      notifyObservers(Event.BEGIN_PROCESSING);
      graph.clear();
      currentPath.clear();
      start = null;
      end = null;

      for (int y = 0; y < this.height; y++) {
         for (int x = 0; x < this.width; x++) {
            graph.createVertexFor(new MazeNode(x, y));
         }
      }

      if (createRandomEdges) {
         Thread thread = new Thread(() -> {
            try {
               Vertex<MazeNode> startVertex = graph.getVertexFor(new MazeNode(0, 0));
               Set<Vertex<MazeNode>> visited = new HashSet<>();
               randomlyCreateEdges(startVertex, visited);
               notifyObservers(Event.DONE);                  
            } catch (Exception e) {
               notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
               e.printStackTrace();
            }
         });
         thread.start();
      } else {
         notifyObservers(Event.DONE);
      }
   }

   /**
    * Reads the maze vertice and edge data from a text file and constructs a maze.
    * @param file The file containing the maze description.
    * @throws Exception Throws exception if the file cannot be read or maze constructed from it.
    */
   public void createFromFile(File file) throws Exception {
      AtomicInteger newWidth = new AtomicInteger(0);
      AtomicInteger newHeight = new AtomicInteger(0);
      AtomicBoolean isFirstLine = new AtomicBoolean(true);

      try (Stream<String> stream = Files.lines(Paths.get(file.toURI()))) {
         stream.forEach(line -> {
            try {
               if (line.length() > 0 && line.charAt(0) != '#') {
                  if (isFirstLine.get()) {
                     String[] items = line.split(" ");
                     if (items.length != 3 || !items[0].equalsIgnoreCase("MAZE")) {
                        throw new IOException("Invalid file format for Maze file");
                     }
                     newWidth.set(Integer.parseInt(items[1]));
                     newHeight.set(Integer.parseInt(items[2]));
                     isFirstLine.set(false);
                     width = newWidth.get();
                     height = newHeight.get();
                     createMaze(width, height, false);
                  } else {
                     String[] items = line.split(" ");
                     if (items.length != 3) {
                        throw new IOException("Invalid edge for Maze file");
                     }
                     String[] nodeCoords = items[0].split(",");
                     int x = Integer.parseInt(nodeCoords[0]);
                     int y = Integer.parseInt(nodeCoords[1]);
                     Vertex<MazeNode> vertexFrom = graph.getVertexFor(new MazeNode(x, y));
                     nodeCoords = items[1].split(",");
                     x = Integer.parseInt(nodeCoords[0]);
                     y = Integer.parseInt(nodeCoords[1]);
                     double weight = Double.parseDouble(items[2]);
                     Vertex<MazeNode> vertexTo = graph.getVertexFor(new MazeNode(x, y));
                     graph.addEdge(EdgeType.UNDIRECTED, vertexFrom, vertexTo, weight);
                  }
               }
            } catch (Exception e) {
               throw new RuntimeException(e);
            }
         });
      }
   }

   private void randomlyCreateEdges(Vertex<MazeNode> fromVertex, Set<Vertex<MazeNode>> visited) {
      visited.add(fromVertex);

      List<Vertex<MazeNode>> neighbours = immediateNeighboursOf(fromVertex.getElement());
      while (!neighbours.isEmpty()) {
         int randomIndex = ThreadLocalRandom.current().nextInt(neighbours.size());
         Vertex<MazeNode> nextVertex = neighbours.get(randomIndex);
         neighbours.remove(randomIndex);
         if (nextVertex != null && !visited.contains(nextVertex)) {
            graph.addEdge(EdgeType.UNDIRECTED, fromVertex, nextVertex, 1.0);
            if (animationSpeed > 0) {
               notifyObservers(Event.MAZE_CHANGED);
               try {
                  Thread.sleep(animationSpeed);
               } catch (InterruptedException ex) {
                  Thread.currentThread().interrupt();
               }
            }
            randomlyCreateEdges(nextVertex, visited);
         }
      }
   }

   public int getWidth() {
      return width;
   }

   public int getHeight() {
      return height;
   }

   public Vertex<MazeNode> getStart() {
      return start;
   }

   public Vertex<MazeNode> getEnd() {
      return end;
   }

   public List<EdgeInformation> getNeighbourDirections(int x, int y) {
      assert (observers != null);
      assert (width > 0 && height > 0);
      List<EdgeInformation> whereNeighbours = new ArrayList<>();
      Vertex<MazeNode> vertex = graph.getVertexFor(new MazeNode(x, y));
      if (null != vertex) {
         List<Edge<MazeNode>> neighbours = graph.getEdges(vertex);
         if (null != neighbours) {
            for (Edge<MazeNode> edge : neighbours) {
               MazeNode.Direction direction = edge.getDestination().getElement().orientationTo(vertex.getElement());
               float weight = (float) edge.getWeigth();
               EdgeInformation information = new EdgeInformation(direction, weight);
               whereNeighbours.add(information);
            }
         }
      }
      return whereNeighbours;
   }

   private List<Vertex<MazeNode>> immediateNeighboursOf(MazeNode node) {
      List<Vertex<MazeNode>> neighbours = new ArrayList<>();
      Vertex<MazeNode> vertex = null;
      if (node.getX() > 0) {
         vertex = graph.getVertexFor(new MazeNode(node.getX() - 1, node.getY()));
         if (vertex != null)
            neighbours.add(vertex);         
      }
      if (node.getY() > 0) {
         vertex = graph.getVertexFor(new MazeNode(node.getX(), node.getY() - 1));
         if (vertex != null)
            neighbours.add(vertex);
      }
      vertex = graph.getVertexFor(new MazeNode(node.getX() + 1, node.getY()));
      if (vertex != null)
         neighbours.add(vertex);
      vertex = graph.getVertexFor(new MazeNode(node.getX(), node.getY() + 1));
      if (vertex != null)
         neighbours.add(vertex);
      return neighbours;
   }

   public void doBreadthFirstSearch() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = graph.getVertexFor(new MazeNode(x, 0));
      Thread thread = new Thread(() -> {
         try {
            System.out.println("Starting BFS from " + x + "," + 0);
            graph.breadthFirstSearch(start, null);
            notifyObservers(Event.DONE);
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   public void doBreadthFirstSearchWithTarget() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = graph.getVertexFor(new MazeNode(x, 0));
      x = ThreadLocalRandom.current().nextInt(getWidth());
      end = graph.getVertexFor(new MazeNode(x, getHeight() - 1));
      Thread thread = new Thread(() -> {
         try {
            graph.breadthFirstSearch(start, end);
            notifyObservers(Event.DONE);
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   public void doDepthFirstSearch() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = graph.getVertexFor(new MazeNode(x, 0));
      Thread thread = new Thread(() -> {
         try {
            System.out.println("Starting DFS from " + x + "," + 0);
            graph.depthFirstSearch(start, null);
            notifyObservers(Event.DONE);               
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   public void doDepthFirstSearchWithTarget() {
      currentPath.clear();
      start = null;
      end = null;
      notifyObservers(Event.BEGIN_PROCESSING);
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = graph.getVertexFor(new MazeNode(x, 0));
      x = ThreadLocalRandom.current().nextInt(getWidth());
      end = graph.getVertexFor(new MazeNode(x, getHeight() - 1));
      Thread thread = new Thread(() -> {
         try {
            graph.depthFirstSearch(start, end);
            notifyObservers(Event.DONE);
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   class MazeDijkstraResult {
      boolean pathFound = false;
      int steps = 0;
      double totalWeigth = 0.0;
   }
   
   /**
    * Initiates the path finding using Dijkstra's algorithm
    */
   public void doDijkstra() {
      int x = ThreadLocalRandom.current().nextInt(getWidth());
      start = graph.getVertexFor(new MazeNode(x, 0));
      x = ThreadLocalRandom.current().nextInt(getWidth());
      end = graph.getVertexFor(new MazeNode(x, getHeight() - 1));
      MazeDijkstraResult mazeResult = new MazeDijkstraResult();
      mazeResult.pathFound = false;
      mazeResult.steps = 0;
      mazeResult.totalWeigth = 0.0;

      currentPath.clear();
      notifyObservers(Event.BEGIN_PROCESSING);
      Thread thread = new Thread(() -> {
         try {
            Graph<MazeNode>.DijkstraResult<MazeNode> result = graph.shortestPathDijkstra(start, end);
            mazeResult.pathFound = result.pathFound;
            if (result.pathFound) {
            mazeResult.steps = result.path.size();
               String message = String.format("Path has %d steps, total weigth is %.1f", result.steps, result.totalWeigth);
               notifyObservers(Event.DONE, message);
            } else {
               notifyObservers(Event.ERROR, "Path could not be found");
            }            
         } catch (Exception e) {
            notifyObservers(Event.ERROR, "Exception: " + e.getMessage());
            e.printStackTrace();
         }
      });
      thread.start();
   }

   private MazeDijkstraResult gameDijkstra(Vertex<MazeNode> start, Vertex<MazeNode> end) {
      currentPath.clear();
      notifyObservers(Event.BEGIN_PROCESSING);
      MazeDijkstraResult mazeResult = new MazeDijkstraResult();
      mazeResult.pathFound = false;
      mazeResult.steps = 0;
      mazeResult.totalWeigth = 0.0;
      if (start != null && end != null && !start.getElement().equals(end.getElement())) {
         System.out.println("Starting Dijkstra from " + start.getElement().getX() + "," + start.getElement().getY() + " to...");
         System.out.println("... " + end.getElement().getX() + "," + end.getElement().getY());
         Graph<MazeNode>.DijkstraResult<MazeNode> result = graph.shortestPathDijkstra(start, end);
         mazeResult.pathFound = result.pathFound;
         mazeResult.steps = result.path.size();
         mazeResult.totalWeigth = result.totalWeigth;
      }
      return mazeResult;
   }

   public List<Vertex<MazeNode>> getCurrentPath() {
      return currentPath;
   }

   public void exportMaze() {
      final String fileName = "new_maze.txt";
      try {
         Set<Edge<MazeNode>> writtenEdgesReversed = new HashSet<>();
         BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
         StringBuilder builder = new StringBuilder();
         writer.write("# Generated maze from MazeApp");
         writer.newLine();
         writer.write("# Maze of width x height y grid");
         writer.newLine();         
         builder.append("MAZE ");
         builder.append(width);
         builder.append(" ");
         builder.append(height);
         writer.write(builder.toString());
         writer.newLine();
         builder.setLength(0);
         for (Map.Entry<Vertex<MazeNode>, List<Edge<MazeNode>>> entry : graph.getEdgeList().entrySet()) {
            for (Edge<MazeNode> edge : entry.getValue()) {
               if (!writtenEdgesReversed.contains(edge)) {
                  builder.append(edge.getSource().toString());
                  builder.append(" ");
                  builder.append(edge.getDestination().toString() + " ");
                  builder.append(edge.getWeigth());
                  writer.write(builder.toString());
                  writer.newLine();
                  builder.setLength(0);
                  writtenEdgesReversed.add(edge.reversed());
               }
            }
         }
         writer.write("# End");
         writer.close();
      } catch (Exception e) {
         notifyObservers(Event.ERROR, "Failed to export graph to file: " + e.getMessage());
         e.printStackTrace();
      }
   }

   // Gaming

   private Timer gameTimer = null;

   public boolean isPlaying() {
      return inGameMode;
   }

   public void startTheGame() {
      inGameMode = !inGameMode;
      if (inGameMode) {
         currentPath.clear();
         start = graph.getVertexFor(new MazeNode(0,0));
         end = graph.getVertexFor(new MazeNode(getWidth() - 1, getHeight() - 1));
         notifyObservers(Event.GAME_ON);
         animationSpeed = ANIMATION_SPEED_NONE;
         gameTimer = new Timer();
         TimerTask gameTimerTask = new TimerTask() {
            @Override
            public void run() {
               if (null != currentPath && currentPath.size() >= 2) {
                  start = currentPath.get(1);
               }
               gameDijkstra(start, end);
               notifyObservers(Event.PATH_CHANGED);
               if (start.getElement().equals(end.getElement())) {
                  startTheGame();
               }
            }
         };
         gameTimer.scheduleAtFixedRate(gameTimerTask, 1000, 500);
      } else {
         stopTheGame();
      }
   }

   private void stopTheGame() {
      inGameMode = false;
      gameTimer.cancel();
      gameTimer = null;
      start = null;
      end = null;
      notifyObservers(Event.GAME_OFF, "The bug got ya!");
      currentPath.clear();
      animationSpeed = ANIMATION_SPEED_FAST;
   }

   public boolean moveTo(MazeNode.Direction direction) {
      if (inGameMode) {
         List<EdgeInformation> neighbours = getNeighbourDirections(end.getElement().getX(), end.getElement().getY());
         for (EdgeInformation neighbour : neighbours) {
            if (neighbour.direction == direction) {
               end = graph.getVertexFor(end.getElement().inDirectionOf(direction));
               if (start.getElement().equals(end.getElement())) {
                  stopTheGame();
               }
               return true;
            }
         }
      }
      return false;
   }

   @Override
   public void pathChanged(Vertex<MazeNode> vertex) {
      currentPath.add(vertex);
      if (animationSpeed > 0) {
         notifyObservers(Event.PATH_CHANGED);
         try {
            Thread.sleep(animationSpeed);
         } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
         }
      }
   }

} // class
