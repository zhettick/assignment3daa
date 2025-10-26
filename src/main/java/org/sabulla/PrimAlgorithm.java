package org.sabulla;

import java.util.*;

public class PrimAlgorithm {
    public void runPrim(int id, List<String> nodeList, List<Edge> edgeList, Metrics metrics) {
        metrics.reset();
        metrics.start();

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < nodeList.size(); i++) {
            indexMap.put(nodeList.get(i), i);
        }

        //  select the starting node
        boolean[] visited = new boolean[nodeList.size()];
        int start = 0;
        visited[start] = true;

        PriorityQueue<Edge> pq = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));

        //  add edges connected to the starting node to the priority queue
        for (Edge edge : edgeList) {
            if (edge.getFrom().equals(nodeList.get(start)) || edge.getTo().equals(nodeList.get(start))) {
                pq.add(edge);
                metrics.addOperations();
            }
        }

        //  build MST by selecting the smallest free edge
        while (!pq.isEmpty()) {
            Edge edge = pq.poll();
            metrics.addOperations();

            int u = indexMap.get(edge.getFrom()), v = indexMap.get(edge.getTo());
            int next = -1;

            if (visited[u] && !visited[v]) {
                next = v;
            } else if (visited[v] && !visited[u]) {
                next = u;
            } else {
                continue;
            }

            visited[next] = true;
            metrics.addEdge(edge);
            metrics.addOperations();

            //  add edges connected to the next node to the priority queue
            for (Edge e : edgeList) {
                int from = indexMap.get(e.getFrom());
                int to = indexMap.get(e.getTo());
                if ((from == next && !visited[to]) || (to == next && !visited[from])) {
                    pq.add(e);
                    metrics.addOperations();
                }
            }
        }
        metrics.stop();
    }
}