package org.sabulla;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String content = Files.readString(Paths.get("input.json"));
            JSONObject jsonObject = new JSONObject(content);
            JSONArray graphs = jsonObject.getJSONArray("graphs");

            List<String> graphsJsonStrings = new ArrayList<>();

            for (int i = 0; i < graphs.length(); i++) {
                JSONObject graph = graphs.getJSONObject(i);

                int id = graph.getInt("id");
                JSONArray nodes = graph.getJSONArray("nodes");
                JSONArray edges = graph.getJSONArray("edges");

                List<String> nodeList = new ArrayList<>();
                for (int n = 0; n < nodes.length(); n++) {
                    nodeList.add(nodes.getString(n));
                }

                List<Edge> edgeList = new ArrayList<>();
                for (int e = 0; e < edges.length(); e++) {
                    JSONObject edge = edges.getJSONObject(e);
                    String from = edge.getString("from");
                    String to = edge.getString("to");
                    int weight = edge.getInt("weight");
                    edgeList.add(new Edge(from, to, weight));
                }

                KruskalAlgorithm kruskal = new KruskalAlgorithm();
                PrimAlgorithm prim = new PrimAlgorithm();
                Metrics kruskalMetrics = new Metrics();
                Metrics primMetrics = new Metrics();

                kruskal.runKruskal(id, nodeList, edgeList, kruskalMetrics);
                prim.runPrim(id, nodeList, edgeList, primMetrics);

                // input_stats
                JSONObject inputs = new JSONObject();
                inputs.put("vertices", nodeList.size());
                inputs.put("edges", edgeList.size());

                // kruskal
                JSONObject kruskalResults = new JSONObject();
                JSONArray kruskalEdges = new JSONArray();
                for (Edge e : kruskalMetrics.getEdgeMST()) {
                    JSONObject kruskalEdge = new JSONObject();
                    kruskalEdge.put("from", e.getFrom());
                    kruskalEdge.put("to", e.getTo());
                    kruskalEdge.put("weight", e.getWeight());
                    kruskalEdges.put(kruskalEdge);
                }
                kruskalResults.put("mst_edges", kruskalEdges);
                kruskalResults.put("total_cost", kruskalMetrics.getCostMST());
                kruskalResults.put("operations_count", kruskalMetrics.getOperations());
                kruskalResults.put("execution_time_ms", kruskalMetrics.getTime());

                // prim
                JSONObject primResults = new JSONObject();
                JSONArray primEdges = new JSONArray();
                for (Edge e : primMetrics.getEdgeMST()) {
                    JSONObject primEdge = new JSONObject();
                    primEdge.put("from", e.getFrom());
                    primEdge.put("to", e.getTo());
                    primEdge.put("weight", e.getWeight());
                    primEdges.put(primEdge);
                }
                primResults.put("mst_edges", primEdges);
                primResults.put("total_cost", primMetrics.getCostMST());
                primResults.put("operations_count", primMetrics.getOperations());
                primResults.put("execution_time_ms", primMetrics.getTime());

                //  structure
                StringBuilder jsonBuilder = new StringBuilder();
                jsonBuilder.append("{\n");
                jsonBuilder.append("  \"graph_id\": ").append(id).append(",\n");
                jsonBuilder.append("  \"input_stats\": ").append(inputs.toString(2)).append(",\n");
                jsonBuilder.append("  \"kruskal\": ").append(kruskalResults.toString(2)).append(",\n");
                jsonBuilder.append("  \"prim\": ").append(primResults.toString(2)).append("\n");
                jsonBuilder.append("}");

                graphsJsonStrings.add(jsonBuilder.toString());
            }

            String finalJson = "{\n  \"results\": [\n" + String.join(",\n", graphsJsonStrings) + "\n  ]\n}";

            Files.write(Paths.get("output.json"), finalJson.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}