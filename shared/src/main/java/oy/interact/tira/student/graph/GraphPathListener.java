package oy.interact.tira.student.graph;

/**
 * An interface for observing graph operations where a path
 * is navigated, like BFS, DFS and Dijkstra's algorithm.
 * 
 * Graph must call pathChanged() method whenever it visits a new vertex
 * on the search path.
 */
public interface GraphPathListener<T> {
	void pathChanged(final Vertex<T> vertex);
}
