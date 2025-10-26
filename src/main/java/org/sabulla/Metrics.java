package org.sabulla;

import java.util.ArrayList;
import java.util.List;

public class Metrics {
    private List<Edge> edgeMST = new ArrayList<>();
    private int costMST;
    private int operations;
    private long startTime;
    private double time;

    public void addEdge(Edge edge) {
        edgeMST.add(edge);
        costMST += edge.getWeight();
    }

    public void addOperations() {
        operations++;
    }

    public void start() {
        startTime = System.nanoTime();
    }

    public void stop() {
        time = System.nanoTime() - startTime;
    }

    public void reset() {
        costMST = 0;
        operations = 0;
        startTime = 0;
        time = 0;
    }

    public List<Edge> getEdgeMST() {
        return edgeMST;
    }

    public int getCostMST() {
        return costMST;
    }

    public int getOperations() {
        return operations;
    }

    public double getTime() {
        return time / 1000000.0;
    }
}
