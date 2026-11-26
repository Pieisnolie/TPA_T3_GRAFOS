package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class ArquivoGrafo {

    public Catalogo carregar(Path caminho) throws IOException {
        Catalogo catalogo = new Catalogo();

        try (BufferedReader leitor = Files.newBufferedReader(caminho)) {
            String linha;
            int numeroLinha = 0;

            while ((linha = leitor.readLine()) != null) {
                numeroLinha++;

                if (linha.trim().isEmpty() || linha.trim().startsWith("#")) {
                    continue;
                }

                try {
                    adicionarLinha(catalogo, linha);
                } catch (IllegalArgumentException erro) {
                    throw new IOException(
                            "Linha " + numeroLinha + ": " + erro.getMessage(),
                            erro
                    );
                }
            }
        }

        if (catalogo.tamanho() == 0) {
            throw new IOException("O arquivo não contém filmes.");
        }

        return catalogo;
    }

    private void adicionarLinha(Catalogo catalogo, String linha) {
        String[] campos = linha.split(";", -1);
        int quantidadePesos = catalogo.tamanho();

        if (campos.length != quantidadePesos + 1) {
            throw new IllegalArgumentException(
                    "Quantidade de pesos inválida para o filme " + campos[0] + "."
            );
        }

        float[] pesos = new float[quantidadePesos];

        for (int i = 0; i < quantidadePesos; i++) {
            try {
                pesos[i] = Float.parseFloat(campos[i + 1].trim().replace(',', '.'));
            } catch (NumberFormatException erro) {
                throw new IllegalArgumentException("Peso inválido.");
            }
        }

        catalogo.cadastrar(campos[0], pesos);
    }
}
