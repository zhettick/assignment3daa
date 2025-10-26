# Assignment 3: Optimization of a City Transportation Network (MST)

## Introduction

The purpose of this project was to optimise a city’s transportation network using two Minimum Spanning Tree (MST)
algorithms: Prim’s algorithm and Kruskal’s algorithm.

The city is represented as a weighted undirected graph, where:
• vertices represent city districts,
• edges represent potential roads,
• edge weights represent construction costs.

The goal was to find the minimum set of roads that connects all districts with the lowest total construction cost, while
ensuring that every district is reachable.

---

## Methodology

How do algorithms work:

1) Kruskal’s algorithm:

- All edges are sorted in non-decreasing order by weight;
- The edge with the smallest weight is selected;
- It is checked whether this edge connects two different connected components (using Union-Find);
- If the edge does not create a cycle, it is added to the MST.
- These steps are repeated until (V - 1) edges are added, where V is the number of vertices in the graph.

2) Prim’s algorithm — grows MST starting from one node, always adding the minimum outgoing edge to a new vertex.

For both algorithms, the following performance metrics were recorded:

1) list of edges in the MST
2) total cost of MST
3) number of vertices and edges in the original graph
4) number of algorithmic operations (e.g., comparisons, union/find calls)
5) execution time in milliseconds

---

## Results

The experiments were performed on three graphs of different sizes:

| Graph ID | Vertices | Edges | Kruskal Cost | Prim Cost | Kruskal Time (ms) | Prim Time (ms) | Kruskal Ops | Prim Ops |
|----------|----------|-------|--------------|-----------|-------------------|----------------|-------------|----------|
| 1        | 6        | 11    | 36           | 36        | 3.24              | 0.71           | 17          | 27       |
| 2        | 15       | 29    | 80           | 80        | 0.10              | 0.38           | 52          | 72       |
| 3        | 26       | 30    | 256          | 256       | 0.09              | 0.36           | 83          | 85       |

1) Total MST cost is the same for both algorithms in all cases, as expected.
2) Execution time: Kruskal was faster on larger graphs.
3) Operation count: Kruskal used fewer operations than Prim for most graphs.

---

## Analysis

1) Kruskal’s algorithm shows good performance even on large graphs due to its efficient edge processing. Sorting the
   edges once keeps the algorithm fast, and the union–find structure helps to avoid unnecessary searches.
2) Prim’s algorithm works well, but it performs more operations since it repeatedly searches through edges using a
   priority queue.
3) Both algorithms produced the same total MST cost, which confirms the correctness of the implementation.

For Graph 1 (small):

- The execution time of Kruskal’s algorithm is noticeably higher despite the lower number of operations. This happens
  because the main overhead in Kruskal’s algorithm is sorting all edges.
- Prim’s algorithm, on the other hand, performs more operations since it works with a priority queue, but it does not
  sort all edges in advance—it adds them gradually, which makes it faster on smaller graphs.
- When the number of vertices is small, Prim’s algorithm is often faster because traversing a priority queue is simpler
  than sorting the entire edge list.

For Graph 2 and Graph 3:

- n Kruskal’s algorithm, sorting the edges is done only once, and the DSU structure handles unions in almost O(1) time,
  making the algorithm highly efficient as the graph size grows.
- In Prim’s algorithm, as the number of vertices increases, the load on the priority queue grows, which leads to longer
  processing times.
- On larger graphs, Kruskal’s algorithm runs faster due to efficient edge sorting, while Prim’s algorithm takes longer
  because of the gradual addition and management of edges in the priority queue.

---

## Conclusion

This project successfully demonstrated how MST algorithms can be applied to optimise a city’s
transportation network by minimizing the total cost of connecting all districts. Both Kruskal’s and Prim’s algorithms
produced correct MSTs with identical total costs, confirming the validity of the implementation.

However, the performance characteristics of the two algorithms varied depending on the size of the graph:

1. Small graphs (Graph 1)
    - Prim’s algorithm performed faster despite a higher number of operations.
    - The main reason is that it does not require sorting the entire edge list in advance and can efficiently select
      edges through a priority queue.
    - Kruskal’s algorithm was slower because the sorting step introduces a noticeable overhead when the graph is small.
2. Medium and large graphs (Graph 2 and Graph 3)
    - Kruskal’s algorithm outperformed Prim’s in execution time. Sorting edges once and using the DSU (Disjoint Set
      Union) structure for cycle checks makes it highly efficient and scalable.
    - Prim’s algorithm, on the other hand, became slower as the number of vertices increased. Managing a larger priority
      queue during each iteration introduced additional overhead.
3. Scalability and efficiency
    - The number of operations was consistently lower for Kruskal’s algorithm.
    - The difference in execution time became more significant as the graph grew, showing that Kruskal’s algorithm
      scales better for larger networks.
    - This reflects the algorithmic complexity:
        - Kruskal → O(E \log E) (dominated by sorting once)
        - Prim → O(E \log V) (priority queue operations in each iteration)
4. Practical implication
    - For small transportation networks, Prim’s algorithm can be advantageous due to lower overhead and simplicity.
    - For larger and more complex networks, Kruskal’s algorithm is more efficient in terms of execution time and
      operation count.

In conclusion, both algorithms are suitable for solving MST problems, but the choice depends on the graph size and
structure. Kruskal’s algorithm provides better scalability, making it a preferred choice for real-world city
transportation networks with many districts and potential roads. Prim’s algorithm remains an effective option for
smaller or denser graphs where sorting overhead may not be justified.

---

## References

• Lecture materials #6.
