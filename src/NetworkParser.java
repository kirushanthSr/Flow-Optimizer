/*
 * Student ID: w2083048
 * Name: Selvaratnam Kirushanth
 */

package src;

import java.io.*;
import java.util.*;

/**
 * NetworkParser class handles reading network definitions from input files
 */
public class NetworkParser {
    /**
     * Reads a flow network from a file
     * @param filename the input file path
     * @return FlowNetwork object representing the network
     */
    // Reads a flow network from a file and returns a FlowNetwork object
public static FlowNetwork readFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            // Read number of nodes
            int V = Integer.parseInt(reader.readLine().trim());
            FlowNetwork network = new FlowNetwork(V);
            
            String line;
            while ((line = reader.readLine()) != null) {
    // Skip empty lines
    if (line.trim().isEmpty()) continue;

                // Skip empty lines
                if (line.trim().isEmpty()) continue;
                
                // Parse edge definition
                String[] parts = line.trim().split("\\s+");
                if (parts.length != 3) {
                    throw new IllegalArgumentException("Invalid edge format: " + line);
                }
                
                int from = Integer.parseInt(parts[0]);
                int to = Integer.parseInt(parts[1]);
                int capacity = Integer.parseInt(parts[2]);
                
                // Validate node indices
                if (from < 0 || from >= V || to < 0 || to >= V) {
                    throw new IllegalArgumentException(
                        "Invalid node indices: " + from + " -> " + to);
                }
                
                // Add edge to network
                network.addEdge(from, to, capacity);
            }
            
            return network;
        }
    }
}
