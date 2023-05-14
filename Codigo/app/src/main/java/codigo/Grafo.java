package codigo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class Grafo {
    private int V;
    private boolean[][] adj;

    /**
    * Cria um novo Grafo com um número específico de vértices.
    *
    * @param v o número de vértices do grafo.
    */
    Grafo(int v) {
        V = v;
        adj = new boolean[V][V];
    }

    /**
    * Adiciona uma aresta ao grafo.
    *
    * @param v o vértice de origem da aresta.
    * @param w o vértice de destino da aresta.
    */
    void addAresta(int v, int w) {
        adj[v][w] = true;
    }

    /**
    * Gera um grafo aleatório com um número específico de arestas.
    *
    * @param numArestas o número de arestas do grafo.
    */
    void generateRandomGraph(int numArestas) {
        Random rand = new Random();
        int count = 0;
        while (count < numArestas) {
            int src = rand.nextInt(V);
            int dest = rand.nextInt(V);
            if (!adj[src][dest]) {
                addAresta(src, dest);
                count++;
            }
        }
    }

    /**
    * Salva o grafo em um arquivo de texto.
    *
    * @param filename o nome do arquivo onde o grafo será salvo.
    * @throws IOException se ocorrer um erro durante a escrita do arquivo.
    */
    void salvarGrafoParaArquivo(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (int v = 0; v < V; v++) {
                for (int w = 0; w < V; w++) {
                    if (adj[v][w]) {
                        writer.write(v + " " + w);
                        writer.newLine();
                    }
                }
            }
        }
    }

    /**
    * Carrega um grafo a partir de um arquivo de texto.
    *
    * @param filename o nome do arquivo a partir do qual o grafo será carregado.
    * @throws IOException se ocorrer um erro durante a leitura do arquivo.
    */
    void carregarGrafoDeArquivo(String filename) throws IOException {
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
            boolean[] visited = new boolean[V];
            DFS(i, visited);
        }
    }

    /**
    * Executa uma busca em profundidade a partir de um vértice específico.
    *
    * @param v o vértice de onde a busca começará.
    * @param visited o conjunto de vértices visitados.
    */
    void DFS(int v, boolean[] visited) {
        visited[v] = true;
        for (int n = 0; n < V; n++) {
            if (adj[v][n] && !visited[n]) {
                DFS(n, visited);
            }
        }
    }

    void warshall() {
        boolean[][] reach = new boolean[V][V];

        for (int i = 0; i < V; i++) {
            System.arraycopy(adj[i], 0, reach[i], 0, V);
        }

        for (int k = 0; k < V; k++) {
            for (int i = 0; i < V; i++) {
                for (int j = 0; j < V; j++) {
                    reach[i][j] = reach[i][j] || (reach[i][k] && reach[k][j]);
                }
            }
        }
    }

    /**
    * Encontra a base e a antibase do grafo.
    */
    void AcharBaseEAntiBase() {
        for (int i = 0; i < V; i++) {
            Set<Integer> base = new HashSet<>();
            Set<Integer> antibase = new HashSet<>();
            for (int j = 0; j < V; j++) {
                if (adj[i][j]) {
                    base.add(j);
                }
                if (adj[j][i]) {
                    antibase.add(j);
                }
            }
            //System.out.println("Bases de " + i + ": " + base + "\nAntibases de " + i + ": " + antibase);
        }
    }
}

