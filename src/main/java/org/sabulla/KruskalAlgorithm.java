package org.sabulla;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KruskalAlgorithm {
    public void runKruskal(int id, List<String> nodeList, List<Edge> edgeList, Metrics metrics) {
        metrics.start();

        edgeList.sort(Comparator.comparingInt(Edge::getWeight));

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < nodeList.size(); i++) {
            indexMap.put(nodeList.get(i), i);
        }

        DSU dsu = new DSU(nodeList.size());

        for (int i = 0; i < edgeList.size(); i++) {
            Edge edge = edgeList.get(i);
            int u = indexMap.get(edge.getFrom());
            int v = indexMap.get(edge.getTo());
            int rootU = dsu.find(u);
            metrics.addOperations();
            int rootV = dsu.find(v);
            metrics.addOperations();

            if (rootU == rootV) {
                continue;
            } else {
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
