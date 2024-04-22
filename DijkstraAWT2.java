import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class DijkstraAWT2 extends Frame implements ActionListener {

    private static final int V = 28;
    private TextField sourceField, destinationField, distanceField;
    private TextArea outputArea;
    private Button calculateButton;
    private ArrayList<String> buildingList;

    public DijkstraAWT2() {
        super("Dijkstra Shortest Path Algorithm");

        buildingList = new ArrayList<>();
        buildingList.add("temple");
        buildingList.add("placement office");
        buildingList.add("mens hostel");
        buildingList.add("security gate");
        buildingList.add("TCE CAR");
        buildingList.add("main canteen");
        buildingList.add("NSS cell");
        buildingList.add("bank");
        buildingList.add("NCC");
        buildingList.add("xerox");
        buildingList.add("Physical department");
        buildingList.add("Parking");
        buildingList.add("ground");
        buildingList.add("library");
        buildingList.add("mechanical");
        buildingList.add("tennis");
        buildingList.add("Basket ball");
        buildingList.add("ECE");
        buildingList.add("open auditorium");
        buildingList.add("KM auditorium");
        buildingList.add("KS auditorium");
        buildingList.add("EEE");
        buildingList.add("CIVIL");
        buildingList.add("Fountain");
        buildingList.add("Food court");
        buildingList.add("CSE");
        buildingList.add("IT");
        buildingList.add("b-hALLS");
        buildingList.add("MECT");
        buildingList.add("Girls rest area");
        buildingList.add("back gate");
        // ... add remaining building names

        // Layout components
        setLayout(new FlowLayout());
        GridBagConstraints c = new GridBagConstraints();

        // Source label and field
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridx = 0;
        c.gridy = 0;
        add(new Label("Source Node:"), c);
        sourceField = new TextField(5);
        c.gridx = 1;
        add(sourceField, c);

        // Destination label and field
        c.gridy = 1;
        add(new Label("Destination Node:"), c);
        destinationField = new TextField(5);
        c.gridx = 2;
        add(destinationField, c);

        // Calculate button
        calculateButton = new Button("Calculate Path");
        calculateButton.addActionListener(this);
        c.gridy = 2;
        c.gridwidth = 2;
        add(calculateButton, c);

        // Output area
        outputArea = new TextArea(10, 30);
        outputArea.setEditable(false);
        c.gridy = 3;
        c.gridwidth = 2;
        add(outputArea, c);

        setSize(500, 400);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == calculateButton) {
            String sourceStr = sourceField.getText();
            String destinationStr = destinationField.getText();

            try {
                int src = Integer.parseInt(sourceStr);
                int dest = Integer.parseInt(destinationStr);
                if (src < 0 || src >= V || dest < 0 || dest >= V) {
                    throw new NumberFormatException("Source and destination must be between 0 and " + (V - 1));
                }

                int[][] graph = {
                        {0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {1, 0, 3, 0, 0, 0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 3, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 5, 0, 10, 15, 0, 0, 0, 0, 5, 10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 15, 0, 0, 4, 3, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 3, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 3, 0, 5, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 10, 0, 7, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 14, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 10, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 10, 15, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 15, 0, 25, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 25, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 10, 0, 0, 0, 0, 0, 0, 0, 0, 23, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 5, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 5, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
                };

                int distance = dijkstra(graph, src, dest);

                outputArea.setText("");
                if (distance != Integer.MAX_VALUE) {
                    outputArea.append("Distance from " + buildingList.get(src) + " to " + buildingList.get(dest) + " is: " + distance + "\n");
                    printPath(graph, src, dest, calculateParent(graph, src));
                } else {
                    outputArea.append("Source " + src + " is not connected to vertex " + dest + "\n");
                }
            } catch (NumberFormatException ex) {
                outputArea.setText("Invalid input: Source and destination must be integers.");
            }
        }
    }

    private static int minDistance(int[] dist, boolean[] sptSet) {
        int min = Integer.MAX_VALUE, minIndex = -1;

        for (int v = 0; v < V; v++) {
            if (!sptSet[v] && dist[v] <= min) {
                min = dist[v];
                minIndex = v;
            }
        }

        return minIndex;
    }
    private static int dijkstra(int[][] graph, int src, int dest) {
        int[] dist = new int[V]; // The output array.  dist[i] will hold the shortest distance from src to i
        boolean[] sptSet = new boolean[V]; // sptSet[i] will be true if vertex i is included in shortest path tree or shortest distance from src to i is finalized

        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(sptSet, false);

        dist[src] = 0; // Always set the distance of source vertex to 0

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet); // Pick the minimum distance vertex from the set of vertices not yet processed.
            sptSet[u] = true; // Mark the picked vertex as processed

            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE && dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                }
            }
        }

        return dist[dest];
    }

    private void printPath(int[][] graph, int src, int dest, int[] parent) {
        if (parent[dest] == -1) {
            outputArea.append("No path exists!");
            return;
        }

        if (src == dest) {
            outputArea.append("Source and destination are same");
        } else {
            ArrayList<String> path = new ArrayList<>();
            int v = dest;
            path.add(buildingList.get(v));
            while (v != src) {
                v = parent[v];
                path.add(buildingList.get(v));
            }

            Collections.reverse(path);
            outputArea.append("Path is: ");
            for (String node : path) {
                outputArea.append(node + " -> ");
            }
            outputArea.append("\n");
        }
    }

    private int[] calculateParent(int[][] graph, int src) {
        int[] dist = new int[V];
        boolean[] sptSet = new boolean[V];
        int[] parent = new int[V];


        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(parent, -1);
        dist[src] = 0;

        for (int count = 0; count < V - 1; count++) {
            int u = minDistance(dist, sptSet);
            sptSet[u] = true;

            for (int v = 0; v < V; v++) {
                if (!sptSet[v] && graph[u][v] != 0 && dist[u] != Integer.MAX_VALUE &&
                        dist[u] + graph[u][v] < dist[v]) {
                    dist[v] = dist[u] + graph[u][v];
                    parent[v] = u;
                }
            }
        }

        return parent;
    }

    public static void main(String[] args) {
        new DijkstraAWT2();
    }
}