/*
Name: Kinjal Amit Heda
PRN: 123B1F048
Date: 05/08/25

Scenario: Emergency Relief Supply Distribution
A devastating flood has hit multiple villages in a remote area, and the government, along

with NGOs, is organizing an emergency relief operation. A rescue team has a limited-
capacity boat that can carry a maximum weight of W kilograms. The boat must transport

critical supplies, including food, medicine, and drinking water, from a relief center to the
affected villages.
Each type of relief item has:
● A weight (wi) in kilograms.
● Utility value (vi) indicating its importance (e.g., medicine has higher value than food).
● Some items can be divided into smaller portions (e.g., food and water), while others must
be taken as a whole (e.g., medical kits).
As the logistics manager, you must:
1. Implement the Fractional Knapsack algorithm to maximize the total utility value of the
supplies transported.
2. Prioritize high-value items while considering weight constraints.
3. Allow partial selection of divisible items (e.g., carrying a fraction of food packets).
4. Ensure that the boat carries the most critical supplies given its weight limit W.
*/
import java.util.*;

class Item {
    String name;        
    double weight;      
    double utility;     
    boolean divisible;  

    Item(String name, double weight, double utility, boolean divisible) {
        this.name = name;
        this.weight = weight;
        this.utility = utility;
        this.divisible = divisible;
    }

    double utilityPerKg() {
        return utility / weight;
    }
}

public class Assignment3 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=== Emergency Relief Supply Loading ===");

        System.out.print("Enter number of relief items: ");
        int n = sc.nextInt();
        sc.nextLine(); 

        if (n <= 0) {
            System.out.println("No items to load. Exiting.");
            sc.close();
            return;
        }

        List<Item> items = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            System.out.println("\nItem #" + (i + 1));

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Weight (kg): ");
            double weight = sc.nextDouble();
            if (weight <= 0) {
                System.out.println("Invalid weight. Must be > 0. Exiting.");
                sc.close();
                return;
            }

            System.out.print("Utility Value: ");
            double utility = sc.nextDouble();
            if (utility < 0) {
                System.out.println("Invalid utility. Must be >= 0. Exiting.");
                sc.close();
                return;
            }

            System.out.print("Divisible? (1 = yes, 0 = no): ");
            boolean divisible = sc.nextInt() == 1;
            sc.nextLine(); // consume newline

            items.add(new Item(name, weight, utility, divisible));
        }

        System.out.print("\nEnter boat capacity (kg): ");
        double capacity = sc.nextDouble();
        if (capacity <= 0) {
            System.out.println("Boat capacity must be > 0. Exiting.");
            sc.close();
            return;
        }

        long start = System.nanoTime();

        // Fractional Knapsack 
        // Sort items by utility per kg in descending order
        items.sort((a, b) -> Double.compare(b.utilityPerKg(), a.utilityPerKg()));

        double totalUtility = 0;
        double remainingCapacity = capacity;

        System.out.println("\nItems loaded on boat:");

        for (Item item : items) {
            if (remainingCapacity <= 0) break; // boat full

            if (item.divisible) {
                double takenWeight = Math.min(item.weight, remainingCapacity);
                double takenUtility = item.utilityPerKg() * takenWeight;
                totalUtility += takenUtility;
                remainingCapacity -= takenWeight;

                System.out.printf(" - %s: %.2f kg, Utility: %.2f%n", item.name, takenWeight, takenUtility);
            } else {
                if (item.weight <= remainingCapacity) {
                    totalUtility += item.utility;
                    remainingCapacity -= item.weight;
                    System.out.printf(" - %s: %.2f kg, Utility: %.2f%n", item.name, item.weight, item.utility);
                }
            }
        }

        System.out.printf("\nTotal utility carried: %.2f%n", totalUtility);

        long end = System.nanoTime();
        System.out.printf("Execution time: %.3f ms%n", (end - start) / 1_000_000.0);

        sc.close();
    }
}
