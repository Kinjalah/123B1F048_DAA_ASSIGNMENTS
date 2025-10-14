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
