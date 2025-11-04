# 123B1F048_DAA_LAB_ASSIGNMENTS_KINJAL_HEDA
Design and Analysis of Algorithms Laboratory Assignments (BIT25PC04)

Course Overview
This repository contains Java implementations of all laboratory assignments for the Design and Analysis of Algorithms (DAA) Laboratory, under the Savitribai Phule Pune University (SPPU) curriculum.
Each program demonstrates a core algorithmic strategy such as Divide and Conquer, Greedy Method, Dynamic Programming, Backtracking, and Branch and Bound.

Laboratory Assignments
No.	Assignment Title	Algorithm/Concept Used	Key Focus
1	Sorting customer orders based on timestamps	Merge Sort (Divide & Conquer)	Efficient sorting for large datasets (1M+ records)
2	Movie Recommendation System Optimization	Quick Sort	Fast data sorting under high load conditions
3	Emergency Relief Supply Distribution	Fractional Knapsack (Greedy)	Maximize utility under weight constraint
4	Smart Traffic Management for Emergency Vehicles	Dijkstra’s Algorithm (Greedy + Graphs)	Dynamic shortest path with real-time traffic updates
5	SwiftCargo Delivery Route Optimization	Dynamic Programming / Multistage Graph	Optimal cost path through multiple delivery stages
6	Disaster Relief Resource Allocation	0/1 Knapsack (Dynamic Programming)	Maximize utility for limited truck capacity
7	University Timetable Scheduling	Graph Coloring (Backtracking / Greedy)	Conflict-free exam scheduling
8	Optimizing Delivery Routes (SwiftShip)	Branch and Bound – Travelling Salesman Problem (TSP)	Least cost route visiting all cities

Technologies Used
Language: Java (JDK 17 or above recommended)
IDE: IntelliJ IDEA 
Libraries: Java Collections Framework (ArrayList, Scanner, etc.)

Time Complexity Summary
Algorithm	Best Case	Average Case	Worst Case
Merge Sort	O(n log n)	O(n log n)	O(n log n)
Quick Sort	O(n log n)	O(n log n)	O(n²)
Fractional Knapsack	O(n log n)	O(n log n)	O(n log n)
Dijkstra’s Algorithm	O(V + E log V)	O(V + E log V)	O(V²)
Dynamic Programming (Multistage)	—	O(V + E)	—
0/1 Knapsack	O(nW)	O(nW)	O(nW)
Graph Coloring	Depends on graph density	NP-Hard	NP-Hard
Branch & Bound (TSP)	—	O(n² · 2ⁿ) (optimized)	O(n!)
