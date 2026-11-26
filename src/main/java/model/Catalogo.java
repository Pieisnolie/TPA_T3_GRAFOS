package model;

import grafo.Grafo;

public class Catalogo {

    private final Grafo<Filme> grafo;
    private int[] notas;

    public Catalogo() {
        grafo = new Grafo<Filme>();
        notas = new int[10];
    }

    public int tamanho() {
        return grafo.quantidadeVertices();
    }

    public Filme getFilme(int indice) {
        return grafo.getVertice(indice);
    }

    public int getNota(int indice) {
        return notas[indice];
    }

    public void cadastrar(String titulo, float[] pesos) {
        Filme novoFilme = new Filme(titulo);
        int quantidadeAnterior = tamanho();

        if (pesos.length != quantidadeAnterior) {
            throw new IllegalArgumentException("Informe um peso para cada filme do catálogo.");
        }

        for (int i = 0; i < quantidadeAnterior; i++) {
            if (getFilme(i).getTitulo().equalsIgnoreCase(novoFilme.getTitulo())) {
                throw new IllegalArgumentException("Esse filme já está cadastrado.");
            }

            validarPeso(pesos[i]);
        }

        grafo.adicionarVertice(novoFilme);
        garantirCapacidadeNotas();

        for (int i = 0; i < quantidadeAnterior; i++) {
            grafo.adicionarAresta(getFilme(i), novoFilme, pesos[i]);
        }
    }

    private void garantirCapacidadeNotas() {
        if (tamanho() <= notas.length) {
            return;
        }

        int[] novasNotas = new int[notas.length * 2];

        for (int i = 0; i < notas.length; i++) {
            novasNotas[i] = notas[i];
        }

        notas = novasNotas;
    }

    public static void validarPeso(float peso) {
        if (!Float.isFinite(peso) || peso < 0 || peso > 1) {
            throw new IllegalArgumentException("A similaridade deve estar entre 0 e 1.");
        }
    }

    public void definirSimilaridade(int origem, int destino, float peso) {
        validarPeso(peso);

        if (origem == destino) {
            throw new IllegalArgumentException("Selecione dois filmes diferentes.");
        }

        grafo.adicionarAresta(getFilme(origem), getFilme(destino), peso);
    }

    public void avaliar(int indice, int nota) {
        if (nota < 1 || nota > 3) {
            throw new IllegalArgumentException("A nota deve ser 1, 2 ou 3.");
        }

        notas[indice] = nota;
    }

    public String caminharEmLargura(int origem) {
        return grafo.caminharEmLargura(getFilme(origem));
    }

    public Recomendacao recomendar() {
        int[] referencias = new int[3];
        int quantidadeReferencias = 0;

        boolean[] assistidos = new boolean[tamanho()];

        for (int i = 0; i < tamanho(); i++) {
            assistidos[i] = notas[i] != 0;
        }

        // Seleciona no máximo três filmes, começando pelas maiores notas.
        for (int nota = 3; nota >= 1 && quantidadeReferencias < 3; nota--) {
            for (int i = 0; i < tamanho() && quantidadeReferencias < 3; i++) {
                if (notas[i] == nota) {
                    referencias[quantidadeReferencias] = i;
                    quantidadeReferencias++;
                }
            }
        }

        if (quantidadeReferencias == 0) {
            return null;
        }

        Grafo.Ligacao melhor = grafo.maiorLigacao(
                referencias,
                quantidadeReferencias,
                assistidos
        );

        if (melhor == null) {
            return null;
        }

        return new Recomendacao(
                getFilme(melhor.getDestino()),
                getFilme(melhor.getOrigem()),
                melhor.getPeso()
        );
    }

    public static class Recomendacao {
        private final Filme indicado;
        private final Filme referencia;
        private final float similaridade;

        public Recomendacao(Filme indicado, Filme referencia, float similaridade) {
            this.indicado = indicado;
            this.referencia = referencia;
            this.similaridade = similaridade;
        }

        public Filme getIndicado() {
            return indicado;
        }

        public Filme getReferencia() {
            return referencia;
        }

        public float getSimilaridade() {
            return similaridade;
        }
    }
}
