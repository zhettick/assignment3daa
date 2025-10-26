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

            JSONArray results = new JSONArray();

            for(int i = 0; i < graphs.length(); i++) {
                JSONObject graph = graphs.getJSONObject(i);

                int id = graph.getInt("id");
                JSONArray nodes = graph.getJSONArray("nodes");
                JSONArray edges = graph.getJSONArray("edges");

                List<String> nodeList = new ArrayList<>();
                for (int n = 0; n < nodes.length(); i++) {
                    nodeList.add(nodes.getString(n));
                }

                List<Edge> edgeList = new ArrayList<>();
                for (int e = 0 ; e < edges.length(); e++) {
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
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}