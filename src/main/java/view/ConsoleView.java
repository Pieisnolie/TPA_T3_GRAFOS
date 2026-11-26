package view;

import java.util.Scanner;
import model.Catalogo.Recomendacao;
import model.Filme;

public class ConsoleView {

    private final Scanner entrada = new Scanner(System.in);

    public String ler(String mensagem) {
        System.out.print(mensagem);
        return entrada.nextLine().trim();
    }

    public int lerInteiro(String mensagem) {
        try {
            return Integer.parseInt(ler(mensagem));
        } catch (NumberFormatException erro) {
            throw new IllegalArgumentException("Informe um número inteiro.");
        }
    }

    public float lerPeso(String mensagem) {
        try {
            return Float.parseFloat(ler(mensagem).replace(',', '.'));
        } catch (NumberFormatException erro) {
            throw new IllegalArgumentException("Informe uma similaridade numérica.");
        }
    }

    public void exibirMenu() {
        System.out.println();
        System.out.println("===== MENU =====");
        System.out.println("1 - Listar filmes");
        System.out.println("2 - Avaliar filme assistido");
        System.out.println("3 - Recomendar filme");
        System.out.println("4 - Cadastrar filme");
        System.out.println("5 - Alterar similaridade entre dois filmes");
        System.out.println("6 - Caminhamento em largura (BFS)");
        System.out.println("0 - Sair");
    }

    public void mostrarFilme(int indice, Filme filme, int nota) {
        String avaliacao = "";

        if (nota != 0) {
            avaliacao = " (nota " + nota + ")";
        }

        System.out.println((indice + 1) + " - " + filme + avaliacao);
    }

    public void mostrarRecomendacao(Recomendacao recomendacao) {
        if (recomendacao == null) {
            System.out.println("Não foi possível gerar uma recomendação.");
            System.out.println("Avalie pelo menos um filme e deixe algum filme sem avaliação.");
            return;
        }

        System.out.println("Filme recomendado: " + recomendacao.getIndicado());
        System.out.println("Filme de referência: " + recomendacao.getReferencia());
        System.out.printf("Grau de similaridade: %.2f%n", recomendacao.getSimilaridade());
    }

    public void mostrar(String texto) {
        System.out.println(texto);
    }
}
