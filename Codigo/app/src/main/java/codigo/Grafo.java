package codigo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Grafo {
    private int V;
    private LinkedList<Integer> adj[];
    private Set<Par> arestas;

    Grafo(int v) {
        V = v;
        adj = new LinkedList[v];
        arestas = new HashSet<>();
        for (int i=0; i<v; ++i)
            adj[i] = new LinkedList();
    }

    void addAresta(int v, int w) {
        Par aresta = new Par(v, w);
        if (!arestas.contains(aresta)) {
            adj[v].add(w);
            arestas.add(aresta);
        }
    }

    void generateRandomGraph(int numArestas) {
        Random rand = new Random();
        while (arestas.size() < numArestas) {
            int src = rand.nextInt(V);
            int dest = rand.nextInt(V);
            addAresta(src, dest);
        }
    }

    void saveGraphToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Par aresta : arestas) {
                writer.write(aresta.v + " " + aresta.w);
                writer.newLine();
            }
        }
    }

    void loadGraphFromFile(String filename) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                int v = Integer.parseInt(parts[0]);
                int w = Integer.parseInt(parts[1]);
                addAresta(v, w);
            }
        }
    }
    
    void naiveSearch() {
        for (int i = 0; i < V; i++) {
            boolean visited[] = new boolean[V];
            DFS(i, visited);
        }
    }

    void DFS(int v, boolean visited[]) {
        visited[v] = true;
        Iterator<Integer> i = adj[v].listIterator();
        while (i.hasNext()) {
            int n = i.next();
            if (!visited[n])
                DFS(n, visited);
        }
    }

    void warshall() {
        boolean reach[][] = new boolean[V][V];

        for (int i = 0; i < V; i++)
            for (int j = 0; j < V; j++)
                reach[i][j] = adj[i].contains(j);

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
                }
            }
        }
    }

    void findBaseAndAntiBase() {
        for (int i = 0; i < V; i++) {
            LinkedList<Integer> base = new LinkedList<>();
            LinkedList<Integer> antibase = new LinkedList<>();
            for (int j = 0; j < V; j++) {
                if (adj[i].contains(j))
                    base.add(j);
                if (adj[j].contains(i))
                    antibase.add(j);
            }
            System.out.println("Base of " + i + ": " + base);
            System.out.println("Antibase of " + i + ": " + antibase);
        }
    }
}

