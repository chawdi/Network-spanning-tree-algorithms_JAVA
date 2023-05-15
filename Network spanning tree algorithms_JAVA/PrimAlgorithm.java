package mst;

import java.lang.*;

class MST {
    private int V;
//finding the minimum edge
    private int minKey(int[] key, Boolean[] mstSet) {
        int min = Integer.MAX_VALUE, min_index = -1;

        for (int v = 0; v < V; v++)
            if (!mstSet[v] && key[v] < min) {
                min = key[v];
                min_index = v;
            }
        return min_index;
    }
//output on display
    private void printMST(int[] parent, int[][] graph) {
        for (int i = 1; i < V; i++)
            System.out.println(parent[i] + " -- " + i + " == " +
                    graph[i][parent[i]]);
    }
//Prim's algorithm
    private void primMST(int[][] graph, int V) {
        this.V = V;
        int[] parent = new int[V];

        int[] key = new int[V];

        Boolean[] mstSet = new Boolean[V];

        for (int i = 0; i < V; i++) {
            key[i] = Integer.MAX_VALUE;
            mstSet[i] = false;
        }

        key[0] = 0;
        parent[0] = -1;
        for (int count = 0; count < V - 1; count++) {
            int u = minKey(key, mstSet);

            mstSet[u] = true;
            for (int v = 0; v < V; v++)
                if (graph[u][v] != 0 && !mstSet[v] &&
                        graph[u][v] < key[v]) {
                    parent[v] = u;
                    key[v] = graph[u][v];
                }
        }
        printMST(parent, graph);
    }
//random filling of the graph
    private static void generateGraph(int V, int[][] graph) {
        int value;
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    graph[i][j] = 0;
                } else {
                    value = (int) Math.round((Math.abs(Math.random()))*(V*V));
                    graph[i][j] = value;
                    graph[j][i] = value;
                }
            }
        }
    }
//func main
    public static void main(String[] args) {
        int V = 50;
        int c = 10000;
        long start, end;
        double allTime = 0, avgTime;

        MST minGraph = new MST();

        int[][] graph = new int[V][V];
        for (int i = 0; i < c; i++) {
            generateGraph(V,graph);
            start = System.currentTimeMillis();
            minGraph.primMST(graph, V);
            System.out.println("-----");
            end = System.currentTimeMillis();
            allTime += (double) ((end - start));
        }
        avgTime = allTime / c;
        System.out.println("\nTime: " + avgTime);
    }
}
