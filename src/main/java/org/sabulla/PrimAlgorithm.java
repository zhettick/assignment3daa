package org.sabulla;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PrimAlgorithm {
    public void runPrim(int id, List<String> nodeList, List<Edge> edgeList, Metrics metrics) {
        metrics.start();

        Map<String, Integer> indexMap = new HashMap<>();
        for (int i = 0; i < nodeList.size(); i++) {
            indexMap.put(nodeList.get(i), i);
        }

        boolean[] visited = new boolean[nodeList.size()];
        visited[0] = true;

        for (int i = 0; i<nodeList.size() - 1;i++) {
            int minWeight= Integer.MAX_VALUE;
            int bestV = -1;
            Edge bestEdge = null;

            for (int j=0; j<edgeList.size();j++){
                Edge edge = edgeList.get(j);
                int u = indexMap.get(edge.getFrom());
                int v = indexMap.get(edge.getTo());
                if(visited[u] && !visited[v] && edge.getWeight() < minWeight) {
                    bestV = v;
                    minWeight = edge.getWeight();
                    bestEdge = edge;
                }
                metrics.addOperations();
            }
            if(bestEdge != null) {
                visited[bestV] =true;
                metrics.addEdge(bestEdge);
                metrics.addOperations();
            } else {
                break;
            }
        }
        metrics.stop();
    }
}
