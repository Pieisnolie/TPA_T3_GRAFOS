package grafo;

public class Grafo<T> {

    private Object[] vertices;
    private float[][] matrizAdjacencia;
    private int quantidadeVertices;

    public Grafo() {
        vertices = new Object[10];
        matrizAdjacencia = criarMatriz(10);
        quantidadeVertices = 0;
    }

    // Cria a matriz preenchendo as posições com NaN.
    // NaN indica que ainda não existe aresta entre os vértices.
    private float[][] criarMatriz(int capacidade) {
        float[][] matriz = new float[capacidade][capacidade];

        for (int i = 0; i < capacidade; i++) {
            for (int j = 0; j < capacidade; j++) {
                matriz[i][j] = Float.NaN;
            }
        }

        return matriz;
    }

    public boolean adicionarVertice(T valor) {
        if (valor == null) {
            throw new IllegalArgumentException("O vértice não pode ser nulo.");
        }

        if (indice(valor) != -1) {
            return false;
        }

        if (quantidadeVertices == vertices.length) {
            aumentarCapacidade();
        }

        vertices[quantidadeVertices] = valor;
        quantidadeVertices++;

        return true;
    }

    private void aumentarCapacidade() {
        int novaCapacidade = vertices.length * 2;

        Object[] novosVertices = new Object[novaCapacidade];
        float[][] novaMatriz = criarMatriz(novaCapacidade);

        for (int i = 0; i < quantidadeVertices; i++) {
            novosVertices[i] = vertices[i];

            for (int j = 0; j < quantidadeVertices; j++) {
                novaMatriz[i][j] = matrizAdjacencia[i][j];
            }
        }

        vertices = novosVertices;
        matrizAdjacencia = novaMatriz;
    }

    public void adicionarAresta(T origem, T destino, float peso) {
        int indiceOrigem = exigirIndice(origem);
        int indiceDestino = exigirIndice(destino);

        matrizAdjacencia[indiceOrigem][indiceDestino] = peso;
        matrizAdjacencia[indiceDestino][indiceOrigem] = peso;
    }

    public int quantidadeVertices() {
        return quantidadeVertices;
    }

    @SuppressWarnings("unchecked")
    public T getVertice(int indice) {
        validarIndice(indice);
        return (T) vertices[indice];
    }

    public float getPeso(int origem, int destino) {
        validarIndice(origem);
        validarIndice(destino);
        return matrizAdjacencia[origem][destino];
    }

    public int indice(T valor) {
        for (int i = 0; i < quantidadeVertices; i++) {
            if (vertices[i].equals(valor)) {
                return i;
            }
        }

        return -1;
    }

    private int exigirIndice(T valor) {
        int indice = indice(valor);

        if (indice == -1) {
            throw new IllegalArgumentException("Vértice inexistente: " + valor);
        }

        return indice;
    }

    private void validarIndice(int indice) {
        if (indice < 0 || indice >= quantidadeVertices) {
            throw new IllegalArgumentException("Índice de vértice inválido.");
        }
    }

    // Percorre o grafo em largura usando um vetor como fila.
    // Como a representação é por matriz, cada linha da matriz é verificada.
    public String caminharEmLargura(T origem) {
        int indiceInicial = exigirIndice(origem);

        boolean[] visitados = new boolean[quantidadeVertices];
        int[] fila = new int[quantidadeVertices];

        int inicioFila = 0;
        int fimFila = 0;

        fila[fimFila] = indiceInicial;
        fimFila++;
        visitados[indiceInicial] = true;

        StringBuilder resultado = new StringBuilder("[");

        while (inicioFila < fimFila) {
            int atual = fila[inicioFila];
            inicioFila++;

            if (resultado.length() > 1) {
                resultado.append(", ");
            }

            resultado.append(getVertice(atual));

            for (int vizinho = 0; vizinho < quantidadeVertices; vizinho++) {
                float peso = matrizAdjacencia[atual][vizinho];

                if (!visitados[vizinho] && !Float.isNaN(peso)) {
                    visitados[vizinho] = true;
                    fila[fimFila] = vizinho;
                    fimFila++;
                }
            }
        }

        resultado.append("]");
        return resultado.toString();
    }

    // Procura a maior similaridade entre os filmes de referência
    // e os filmes que ainda não foram assistidos.
    public Ligacao maiorLigacao(int[] referencias, int quantidadeReferencias, boolean[] excluir) {
        Ligacao melhor = null;

        for (int i = 0; i < quantidadeReferencias; i++) {
            int origem = referencias[i];

            for (int destino = 0; destino < quantidadeVertices; destino++) {
                if (destino == origem || excluir[destino]) {
                    continue;
                }

                float peso = matrizAdjacencia[origem][destino];

                if (!Float.isNaN(peso)) {
                    if (melhor == null || peso > melhor.getPeso()) {
                        melhor = new Ligacao(origem, destino, peso);
                    }
                }
            }
        }

        return melhor;
    }

    public static class Ligacao {
        private final int origem;
        private final int destino;
        private final float peso;

        public Ligacao(int origem, int destino, float peso) {
            this.origem = origem;
            this.destino = destino;
            this.peso = peso;
        }

        public int getOrigem() {
            return origem;
        }

        public int getDestino() {
            return destino;
        }

        public float getPeso() {
            return peso;
        }
    }
}
