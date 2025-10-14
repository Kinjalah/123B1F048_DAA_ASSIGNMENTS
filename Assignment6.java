/*
Scenario: Disaster Relief Resource Allocation
A massive earthquake has struck a remote region, and a relief organization is transporting
essential supplies to the affected area. The organization has a limited-capacity relief truck that
can carry a maximum weight of W kg. They have N different types of essential items, each
with a specific weight and an associated utility value (importance in saving lives and meeting
urgent needs).
Since the truck has limited capacity, you must decide which items to include to maximize the
total utility value while ensuring the total weight does not exceed the truck's limit.
Your Task as a Logistics Coordinator:
1. Model this problem using the 0/1 Knapsack approach, where each item can either be
included in the truck (1) or not (0).
2. Implement an algorithm to find the optimal set of items that maximizes utility while
staying within the weight constraint.
3. Analyze the performance of different approaches (e.g., Brute Force, Dynamic
Programming, and Greedy Algorithms) for solving this problem efficiently.
4. Optimize for real-world constraints, such as perishable items (medicines, food) having
priority over less critical supplies.
Extend the model to consider multiple trucks or real-time decision-making for dynamic supply
chain management.
*/
import java.util.*;

public class Assignment6 {

    // maximum utility using Dynamic Programming
    public static void getMaxUtility(int[] itemWeight, int[] itemUtility, int maxTruckCapacity, int totalItems) {
        int[] dp = new int[maxTruckCapacity + 1];
        int[][] selectedTrack = new int[totalItems][maxTruckCapacity + 1]; // Track selected items

        for (int i = 0; i < totalItems; i++) {
            for (int currentCapacity = maxTruckCapacity; currentCapacity >= itemWeight[i]; currentCapacity--) {
                if (dp[currentCapacity] < dp[currentCapacity - itemWeight[i]] + itemUtility[i]) {
                    dp[currentCapacity] = dp[currentCapacity - itemWeight[i]] + itemUtility[i];
                    selectedTrack[i][currentCapacity] = 1; // Mark item as selected
                }
            }
        }
        System.out.println("\n Maximum Utility (Optimal Relief Load): " + dp[maxTruckCapacity]);

        // Backtrack to identify selected items
        int[] selectedItems = new int[totalItems];
        int remainingCapacity = maxTruckCapacity;
        for (int i = totalItems - 1; i >= 0; i--) {
            if (selectedTrack[i][remainingCapacity] == 1) {
                selectedItems[i] = 1;
                remainingCapacity -= itemWeight[i];
            }
        }

        System.out.println("\n Selected Items (1 = included, 0 = not included):");
        for (int i = 0; i < totalItems; i++) {
            System.out.println("Item " + (i + 1) + " -> " + selectedItems[i]);
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter total number of essential items: ");
        int totalItems = sc.nextInt();

        System.out.print("Enter maximum truck capacity (in kg): ");
        int maxTruckCapacity = sc.nextInt();

        int[] itemWeight = new int[totalItems];
        int[] itemUtility = new int[totalItems];

        System.out.println("\nEnter details for each item (weight in kg and utility value):");
        for (int i = 0; i < totalItems; i++) {
            System.out.print("Item " + (i + 1) + " weight (kg): ");
            itemWeight[i] = sc.nextInt();
            System.out.print("Item " + (i + 1) + " utility value: ");
            itemUtility[i] = sc.nextInt();
        }

        getMaxUtility(itemWeight, itemUtility, maxTruckCapacity, totalItems);

        sc.close();
    }
}
