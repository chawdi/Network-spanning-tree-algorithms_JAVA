package mst;

import java.util.Arrays;
//class describing the graph
class Graph {
    private int V;
    private Edge[] edge;
//class describing graph edges
    class Edge implements Comparable<Edge> {
        int src, dest, weight;

        public int compareTo(Edge compareEdge) {
            return this.weight - compareEdge.weight;
        }
    }
// class describing a subset 
    class subset {
        int parent, rank;
    }

    private Graph(int v, int e) {
        V = v;
        edge = new Edge[e];
        for (int i = 0; i < e; ++i)
            edge[i] = new Edge();
    }
//finding a set from element i
    private int find(subset[] subsets, int i) {
        if (subsets[i].parent != i)
            subsets[i].parent = find(subsets, subsets[i].parent);

        return subsets[i].parent;
    }
//union of sets
    private void Union(subset[] subsets, int x, int y) {
        int xroot = find(subsets, x);
        int yroot = find(subsets, y);

        if (subsets[xroot].rank < subsets[yroot].rank)
            subsets[xroot].parent = yroot;
        else if (subsets[xroot].rank > subsets[yroot].rank)
            subsets[yroot].parent = xroot;

        else {
            subsets[yroot].parent = xroot;
            subsets[xroot].rank++;
        }
    }
//Kruskal's algorithm
    private void KruskalMST() {
        Edge[] result = new Edge[V];
        int e = 0;
        int i;
        for (i = 0; i < V; ++i)
            result[i] = new Edge();
//sort edges in ascending order
        Arrays.sort(edge);

        subset[] subsets = new subset[V];
        for (i = 0; i < V; ++i)
            subsets[i] = new subset();

        for (int v = 0; v < V; ++v) {
            subsets[v].parent = v;
            subsets[v].rank = 0;
        }

        i = 0;

        while (e < V - 1) {
            Edge next_edge;
            next_edge = edge[i++];

            int x = find(subsets, next_edge.src);
            int y = find(subsets, next_edge.dest);

            if (x != y) {
                result[e++] = next_edge;
                Union(subsets, x, y);
            }
        }
//output on display
        for (i = 0; i < e; ++i)
            System.out.println(result[i].src + " -- " +
                    result[i].dest + " == " + result[i].weight);
    }
//random filling of the graph
    private static void generateGraph(int V, Graph graph) {
        int value;
        int e = 0;
        int[][] iGraph = new int[V][V];
        for (int i = 0; i < V; i++) {
            for (int j = 0; j < V; j++) {
                if (i == j) {
                    iGraph[i][j] = 0;
                } else {
                    value = (int) Math.round((Math.abs(Math.random())) * (V * V));
                    iGraph[i][j] = value;
                    iGraph[j][i] = value;
                }
            }
        }
        for (int i = 0; i < V; i++) {
            for (int j = 1 + i; j < V; j++) {
                if(iGraph[i][j] != 0){
                    graph.edge[e].src = i;
                    graph.edge[e].dest = j;
                    graph.edge[e].weight = iGraph[i][j];
                    e++;
                }
            }
        }
    }
//func main
    public static void main(String[] args) {
        int V = 50;
        int E = V*(V-1)/2;

        int c = 100000;
        long start, end;
        double allTime = 0, avgTime;

        Graph graph = new Graph(V, E);

        for (int i = 0; i < c; i++) {
            generateGraph(V, graph);
            start = System.currentTimeMillis();
            graph.KruskalMST();
            end = System.currentTimeMillis();
            System.out.println("-----");
            allTime += (double) ((end - start));
        }
        avgTime = allTime / c;
        System.out.println("\nTime: " + avgTime);
    }
}
