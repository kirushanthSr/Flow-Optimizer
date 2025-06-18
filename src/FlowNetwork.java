/*
 * Student ID: w2083048
 * Name: Selvaratnam Kirushanth
 */

package src;

import java.util.*;

/**
 * FlowNetwork class represents a flow network using adjacency list representation
 * Each edge stores capacity and current flow
 */
public class FlowNetwork {
    private final int V; // number of vertices
    private final Map<Integer, List<Edge>> adj; // adjacency list
    
    /**
     * Edge class represents a directed edge with capacity and flow
     */
    public static class Edge {
        private final int from;
        private final int to;
        private final int capacity;
        private int flow;
        
        public Edge(int from, int to, int capacity) {
            this.from = from;
            this.to = to;
            this.capacity = capacity;
            this.flow = 0;
        }
        
        // Given one vertex, returns the other vertex of the edge
public int other(int vertex) {
            return vertex == from ? to : from;
        }
        
        // Returns the residual capacity of the edge to the given vertex
public int residualCapacityTo(int vertex) {
            if (vertex == to) return capacity - flow;
            else if (vertex == from) return flow;
            throw new IllegalArgumentException("Invalid vertex");
        }
        
        // Adds flow to the edge in the direction of the given vertex (forward or backward)
public void addResidualFlowTo(int vertex, int delta) {
            if (vertex == to) flow += delta;
            else if (vertex == from) flow -= delta;
            else throw new IllegalArgumentException("Invalid vertex");
        }
        
        // Returns the source vertex of this edge
public int getFrom() { return from; }
        // Returns the destination vertex of this edge
public int getTo() { return to; }
        // Returns the capacity of this edge
public int getCapacity() { return capacity; }
        // Returns the current flow through this edge
public int getFlow() { return flow; }
    }
    
    public FlowNetwork(int V) {
        this.V = V;
        this.adj = new HashMap<>();
        for (int v = 0; v < V; v++) {
            adj.put(v, new ArrayList<>());
        }
    }
    
    public void addEdge(int from, int to, int capacity) {
        Edge edge = new Edge(from, to, capacity);
        adj.get(from).add(edge);
        adj.get(to).add(edge); // Add reverse edge for residual graph
    }
    
    public List<Edge> adj(int v) {
        return adj.get(v);
    }
    
    public int V() {
        return V;
    }
    
    public List<Edge> edges() {
        List<Edge> edges = new ArrayList<>();
        for (int v = 0; v < V; v++) {
            for (Edge e : adj(v)) {
                if (e.getFrom() == v) { // Only add edge once
                    edges.add(e);
                }
            }
        }
        return edges;
    }
}
