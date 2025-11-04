/*
Name: Kinjal Amit Heda
PRN: 123B1F048
Date: 02/09/25

Scenario:
A logistics company, SwiftCargo, specializes in delivering packages across multiple cities.
To optimize its delivery process, the company divides the transportation network into
multiple stages (warehouses, transit hubs, and final delivery points). Each package must
follow the most cost-efficient or fastest route from the source to the destination while
passing through these predefined stages.
As a logistics optimization engineer, you must:
1. Model the transportation network as a directed, weighted multistage graph with
multiple intermediate stages.
2. Implement an efficient algorithm (such as Dynamic Programming or Dijkstra’s
Algorithm) to find the optimal delivery route.
3. Ensure that the algorithm scales for large datasets (handling thousands of cities and
routes).

T.Y. B.Tech Information Technology, PCCoE Pune Page 10
4. Analyze and optimize route selection based on real-time constraints, such as traffic
conditions, weather delays, or fuel efficiency.
Constraints & Considerations:
● The network is structured into N stages, and every package must pass through at least
one node in each stage.
● There may be multiple paths with different costs/times between stages.
● The algorithm should be flexible enough to handle real-time changes (e.g., road
closures or rerouting requirements).
● The system should support batch processing for multiple delivery requests.
*/
import java.util.*;

class Edge {
    int to;       // destination node
    int cost;     // cost/time to travel
    Edge(int to, int cost) {
        this.to = to;
        this.cost = cost;
    }
}

public class Assignment5 {

    // Find shortest path in a multistage graph using DP
    public static int[] findShortestPath(List<List<Edge>> graph, int N, int source, int destination) {
        int[] dist = new int[N];       // min cost to reach destination from node i
        int[] nextNode = new int[N];   // next node to go for shortest path
        Arrays.fill(dist, Integer.MAX_VALUE);
        Arrays.fill(nextNode, -1);

        dist[destination] = 0; // cost to reach destination from itself = 0

        for (int i = N - 2; i >= 0; i--) {
            for (Edge e : graph.get(i)) {
                if (dist[e.to] != Integer.MAX_VALUE && e.cost + dist[e.to] < dist[i]) {
                    dist[i] = e.cost + dist[e.to];
                    nextNode[i] = e.to;
                }
            }
        }

        return nextNode;
    }

    public static void printPath(int source, int[] nextNode) {
        int node = source;
        System.out.print(node);
        while (nextNode[node] != -1) {
            node = nextNode[node];
            System.out.print(" -> " + node);
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter number of nodes in multistage graph: ");
        int N = sc.nextInt(); 

        List<List<Edge>> graph = new ArrayList<>();
        for (int i = 0; i < N; i++) graph.add(new ArrayList<>());

        System.out.print("Enter number of edges: ");
        int E = sc.nextInt();

        System.out.println("Enter edges as: from to cost");
        for (int i = 0; i < E; i++) {
            int u = sc.nextInt();
            int v = sc.nextInt();
            int cost = sc.nextInt();
            graph.get(u).add(new Edge(v, cost)); // directed edge
        }

        System.out.print("Enter source node: ");
        int source = sc.nextInt();

        System.out.print("Enter destination node: ");
        int destination = sc.nextInt();

        long startTime = System.nanoTime();
        int[] nextNode = findShortestPath(graph, N, source, destination);
        long endTime = System.nanoTime();

        System.out.println("\n--- Optimal Delivery Route ---");
        System.out.print("Path: ");
        printPath(source, nextNode);

        System.out.println("Execution time: " + (endTime - startTime)/1_000_000.0 + " ms");

        sc.close();
    }
}
