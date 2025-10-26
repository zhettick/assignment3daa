package org.sabulla;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KruskalAlgorithm {
    public void runKruskal(int id, List<String> nodeList, List<Edge> edgeList, Metrics metrics) {
        metrics.reset();
        metrics.start();

        edgeList.sort(Comparator.comparingInt(Edge::getWeight));

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < nodeList.size(); i++) {
            indexMap.put(nodeList.get(i), i);
        }

        //  to track connected components
        DSU dsu = new DSU(nodeList.size());

        //  iterate over edges sorted by weight, if an edge connects different sets, union them and include the edge in the MST.
        for (Edge edge : edgeList) {
            int u = indexMap.get(edge.getFrom()), v = indexMap.get(edge.getTo());
            int rootU = dsu.find(u);
            metrics.addOperations();
            int rootV = dsu.find(v);
            metrics.addOperations();

            if (rootU != rootV) {
                dsu.union(u, v);
                metrics.addEdge(edge);
                metrics.addOperations();
            }

            if (metrics.getEdgeMST().size() == nodeList.size() - 1) {
                break;
            }
        }
        metrics.stop();
    }
}