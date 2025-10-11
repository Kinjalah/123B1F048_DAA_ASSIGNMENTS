/*
Scenario: Smart Traffic Management for Emergency Vehicles
A smart city is implementing an intelligent traffic management system to assist ambulances
in reaching hospitals as quickly as possible. The city’s road network is represented as a
graph, where:
● Intersections (junctions) are nodes.
● Roads between intersections are edges with weights representing travel time (in minutes)
considering traffic congestion.
An ambulance is currently at Source (S) and needs to reach the nearest hospital (Destination
D) in the shortest possible time. Due to dynamic traffic conditions, the weight of each road
segment may change in real time.
As a transportation engineer, you are assigned to:
1. Implement Dijkstra’s algorithm to find the shortest path from the ambulance's current
location (S) to all possible hospitals.
2. Account for dynamic weight updates as traffic conditions change.
3. Optimize the system to work efficiently for a large city with thousands of intersections
and roads.
4. Provide a visual representation of the optimal path for navigation.
Expected Outcome:
The system should suggest the quickest route for the ambulance, updating dynamically
based on real-time traffic conditions, ensuring minimal response time to emergencies.
*/
import java.util.*;

class Road {
    int destination;
    int travelTime; // in minutes

    Road(int destination, int travelTime) {
        this.destination = destination;
        this.travelTime = travelTime;
    }
}

public class Assignment4 {

    // Dijkstra's algorithm 
    public static int[] dijkstra(List<List<Road>> graph, int start, int[] previousNode) {
        int n = graph.size();
        int[] travelTime = new int[n];
        Arrays.fill(travelTime, Integer.MAX_VALUE);
        Arrays.fill(previousNode, -1); // to reconstruct path
        travelTime[start] = 0;

        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingInt(a -> a[1]));
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] curr = pq.poll();
            int node = curr[0], time = curr[1];

            if (time > travelTime[node]) continue;

            for (Road r : graph.get(node)) {
                int neighbor = r.destination;
                int newTime = travelTime[node] + r.travelTime;
                if (newTime < travelTime[neighbor]) {
                    travelTime[neighbor] = newTime;
                    previousNode[neighbor] = node;
                    pq.add(new int[]{neighbor, newTime});
                }
            }
        }
        return travelTime;
    }

    public static void printPath(int[] previousNode, int destination) {
        if (previousNode[destination] == -1) {
            System.out.print(destination);
            return;
        }
        printPath(previousNode, previousNode[destination]);
        System.out.print(" -> " + destination);
    }

    // Find the nearest hospital 
    public static int findNearestHospital(int[] travelTime, int[] hospitals) {
        int nearest = -1, minTime = Integer.MAX_VALUE;
        for (int h : hospitals) {
            if (travelTime[h] < minTime) {
                minTime = travelTime[h];
                nearest = h;
            }
        }
        return nearest;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Number of intersections: ");
        int numIntersections = sc.nextInt();
        System.out.print("Number of roads: ");
        int numRoads = sc.nextInt();

        List<List<Road>> graph = new ArrayList<>();
        for (int i = 0; i < numIntersections; i++) graph.add(new ArrayList<>());

        System.out.println("Enter roads as: start end travelTime");
        for (int i = 0; i < numRoads; i++) {
            int u = sc.nextInt(), v = sc.nextInt(), t = sc.nextInt();
            graph.get(u).add(new Road(v, t));
            graph.get(v).add(new Road(u, t)); // undirected
        }

        System.out.print("Ambulance starts at intersection: ");
        int start = sc.nextInt();

        System.out.print("Number of hospitals: ");
        int numHospitals = sc.nextInt();
        int[] hospitals = new int[numHospitals];
        System.out.print("Enter hospital nodes: ");
        for (int i = 0; i < numHospitals; i++) hospitals[i] = sc.nextInt();

        int[] previousNode = new int[numIntersections];

        System.out.println("\nCalculating shortest paths...");
        long startTime = System.nanoTime();
        int[] travelTime = dijkstra(graph, start, previousNode);
        long endTime = System.nanoTime();

        int nearestHospital = findNearestHospital(travelTime, hospitals);

        System.out.println("\n--- Emergency Route ---");
        System.out.println("Ambulance start: " + start);
        System.out.println("Hospitals: " + Arrays.toString(hospitals));
        if (nearestHospital == -1 || travelTime[nearestHospital] == Integer.MAX_VALUE) {
            System.out.println("No reachable hospital!");
        } else {
            System.out.println("Nearest hospital: " + nearestHospital + " (time: " + travelTime[nearestHospital] + " mins)");
            System.out.print("Shortest path: ");
            printPath(previousNode, nearestHospital);
            System.out.println();
        }

        System.out.printf("Execution time: %.3f ms%n", (endTime - startTime) / 1_000_000.0);

        sc.close();
    }
}
