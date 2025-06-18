/*
 * Student ID: w2083048
 * Name: Selvaratnam Kirushanth
 */

package src;

import java.util.*;

/**
 * NetworkFlow implements the Ford-Fulkerson algorithm for finding maximum flow
 */
public class NetworkFlow {
    private static final int INFINITY = Integer.MAX_VALUE;
    
    /**
     * Finds the maximum flow in the network from source to sink
     * using the Ford-Fulkerson algorithm with BFS (Edmonds-Karp)
     */
    // Computes the maximum flow from source to sink using Edmonds-Karp (BFS-based Ford-Fulkerson)
public static int maxFlow(FlowNetwork G, int source, int sink) {
        int maxFlow = 0;
        int iteration = 1;
        
        while (true) {
            // Find augmenting path using BFS
            Map<Integer, FlowNetwork.Edge> edgeTo = new HashMap<>();
            Queue<Integer> queue = new LinkedList<>();
            Set<Integer> visited = new HashSet<>();
            
            queue.offer(source);
            visited.add(source);
            
            // BFS to find augmenting path
            boolean foundPath = false;
            while (!queue.isEmpty() && !foundPath) {
                int v = queue.poll();
                
                for (FlowNetwork.Edge e : G.adj(v)) {
                    int w = e.other(v);
                    
                    // If residual capacity exists and vertex not visited
                    if (e.residualCapacityTo(w) > 0 && !visited.contains(w)) {
                        edgeTo.put(w, e);
                        visited.add(w);
                        queue.offer(w);
                        if (w == sink) {
                            foundPath = true;
                            break;
                        }
                    }
                }
            }
            
            // If no augmenting path found
            if (!foundPath) break;
            
            // Find bottleneck capacity
            int bottle = INFINITY;
            for (int v = sink; v != source; v = edgeTo.get(v).other(v)) {
                bottle = Math.min(bottle, edgeTo.get(v).residualCapacityTo(v));
            }
            
            // Augment flow
            for (int v = sink; v != source; v = edgeTo.get(v).other(v)) {
                edgeTo.get(v).addResidualFlowTo(v, bottle);
            }
            
            maxFlow += bottle;
            
            // Print output in the requested Ford-Fulkerson style
            List<Integer> pathNodes = new ArrayList<>();
            for (int v = sink; v != source; v = edgeTo.get(v).other(v)) {
                pathNodes.add(0, v);
            }
            pathNodes.add(0, source);
            System.out.printf("Iteration %d:%n", iteration++);
            System.out.print("Found augmenting path: ");
            for (int i = 0; i < pathNodes.size(); i++) {
                System.out.print(pathNodes.get(i));
                if (i < pathNodes.size() - 1) System.out.print(" -> ");
            }
            System.out.printf(" with flow %d%n", bottle);
            System.out.printf("Current maximum flow: %d%n%n", maxFlow);
        }
        
        return maxFlow;
    }
    
    /**
     * Program entry point. Reads the network, computes max flow, and prints results.
     * 
     * @param args Command line arguments (input file name)
     */
    public static void main(String[] args) {
        String inputFile;
        if (args.length == 1) {
            inputFile = args[0];
        } else {
            // Default to test_network.txt for one-click run in VS Code
            inputFile = "test_network.txt";
            System.out.println("No input file specified. Defaulting to test_network.txt");
        }

        try {
            FlowNetwork network = NetworkParser.readFromFile(inputFile);
            int source = 0;  // Source is always node 0
            int sink = network.V() - 1;  // Sink is always the last node

            System.out.println("Starting Ford-Fulkerson algorithm...\n");
            int maxFlow = maxFlow(network, source, sink);

            System.out.println("Final maximum flow: " + maxFlow);
            System.out.println("\nFinal edge flows:");
            for (FlowNetwork.Edge e : network.edges()) {
                System.out.printf("Edge %d->%d: flow=%d/%d\n",
                    e.getFrom(), e.getTo(), e.getFlow(), e.getCapacity());
            }

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
