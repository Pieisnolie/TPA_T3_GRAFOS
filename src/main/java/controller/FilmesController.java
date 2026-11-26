package controller;

import java.io.IOException;
import java.nio.file.Path;
import model.ArquivoGrafo;
import model.Catalogo;
import view.ConsoleView;

public class FilmesController {

    private final ConsoleView view;
    private final ArquivoGrafo arquivo;
    private Catalogo catalogo;

    public FilmesController(ConsoleView view) {
        this.view = view;
        this.arquivo = new ArquivoGrafo();
        this.catalogo = new Catalogo();
    }

    public void executar(Path caminhoInicial) {
        carregar(caminhoInicial);

        int opcao;

        do {
            view.exibirMenu();

            try {
                opcao = view.lerInteiro("Escolha uma opção: ");

                switch (opcao) {
                    case 1:
                        listar();
                        break;
                    case 2:
                        avaliar();
                        break;
                    case 3:
                        view.mostrarRecomendacao(catalogo.recomendar());
                        break;
                    case 4:
                        cadastrar();
                        break;
                    case 5:
                        definirPeso();
                        break;
                    case 6:
                        percorrer();
                        break;
                    case 7:
                        carregar(Path.of(view.ler("Nome do arquivo: ")));
                        break;
                    case 0:
                        view.mostrar("Programa encerrado.");
                        break;
                    default:
                        view.mostrar("Opção inválida.");
                }
            } catch (IllegalArgumentException erro) {
                view.mostrar("Erro: " + erro.getMessage());
                opcao = -1;
            }

        } while (opcao != 0);
    }

    private void carregar(Path caminho) {
        try {
            // O catálogo só é substituído quando o arquivo é carregado corretamente.
            Catalogo novoCatalogo = arquivo.carregar(caminho);
            catalogo = novoCatalogo;
            view.mostrar("Filmes carregados: " + catalogo.tamanho());
        } catch (IOException erro) {
            view.mostrar("Erro ao carregar o arquivo: " + erro.getMessage());
        }
    }

    private void listar() {
        if (catalogo.tamanho() == 0) {
            view.mostrar("Catálogo vazio.");
            return;
        }

        for (int i = 0; i < catalogo.tamanho(); i++) {
            view.mostrarFilme(i, catalogo.getFilme(i), catalogo.getNota(i));
        }
    }

    private int selecionarFilme(String mensagem) {
        if (catalogo.tamanho() == 0) {
            throw new IllegalArgumentException("O catálogo está vazio.");
        }

        int codigo = view.lerInteiro(mensagem);

        if (codigo < 1 || codigo > catalogo.tamanho()) {
            throw new IllegalArgumentException("Filme inexistente.");
        }

        return codigo - 1;
    }

    private void avaliar() {
        listar();

        int filme = selecionarFilme("Filme assistido: ");
        int nota = view.lerInteiro(
                "Nota (1 = não gostei, 2 = mais ou menos, 3 = gostei muito): "
        );

        catalogo.avaliar(filme, nota);
        view.mostrar("Avaliação registrada.");
    }

    private void cadastrar() {
        String titulo = view.ler("Título: ");
        float[] pesos = new float[catalogo.tamanho()];

        for (int i = 0; i < pesos.length; i++) {
            pesos[i] = view.lerPeso(
                    "Similaridade com " + catalogo.getFilme(i) + " (0 a 1): "
            );
        }

        catalogo.cadastrar(titulo, pesos);
        view.mostrar("Filme cadastrado com sucesso.");
    }

    private void definirPeso() {
        listar();

        int origem = selecionarFilme("Primeiro filme: ");
        int destino = selecionarFilme("Segundo filme: ");
        float peso = view.lerPeso("Similaridade (0 a 1): ");

        catalogo.definirSimilaridade(origem, destino, peso);
        view.mostrar("Similaridade atualizada.");
    }

    private void percorrer() {
        listar();

        int origem = selecionarFilme("Filme inicial: ");

        view.mostrar("Ordem da busca em largura:");
        view.mostrar(catalogo.caminharEmLargura(origem));
    }
}
