# Trabalho 3 de TPA — Sistema de Recomendação de Filmes

## 1. Especificação do problema

O tema escolhido foi um sistema de recomendação de filmes baseado em similaridade.

Cada vértice do grafo representa um filme e cada aresta possui um peso entre 0 e 1, representando o grau de similaridade entre dois filmes. Quanto maior o peso, maior é a similaridade.

O usuário informa os filmes que já assistiu e dá uma avaliação em três níveis: 1 para não gostei, 2 para mais ou menos e 3 para gostei muito. O sistema seleciona até três filmes começando pelas maiores avaliações e recomenda um filme ainda não assistido que possua o maior grau de similaridade com eles.

O grafo é completo, não direcionado e ponderado. A representação escolhida foi a matriz de adjacências, pois cada filme possui uma ligação com todos os outros filmes.

A biblioteca também implementa a Busca em Largura (BFS), conforme solicitado no trabalho.

## 2. Biblioteca de grafos

A classe `Grafo<T>` é genérica e utiliza um vetor para armazenar os vértices e uma matriz de valores `float` para armazenar os pesos das arestas.

O método `adicionarVertice` inclui um novo vértice caso ele ainda não exista no grafo. Quando o espaço disponível termina, os vetores são aumentados.

O método `adicionarAresta` recebe dois vértices e um peso. Como o grafo do sistema não é direcionado, o peso é colocado nas duas posições correspondentes da matriz.

```java
matrizAdjacencia[indiceOrigem][indiceDestino] = peso;
matrizAdjacencia[indiceDestino][indiceOrigem] = peso;
```

O método `caminharEmLargura` utiliza um vetor como fila. Um vértice é marcado como visitado quando entra na fila, evitando que seja visitado novamente.

Como foi utilizada uma matriz de adjacências, para cada vértice retirado da fila é necessário percorrer uma linha da matriz. Dessa forma, a complexidade da BFS é `O(V²)`.

A matriz ocupa `O(V²)` de memória.

Para a recomendação, o método `maiorLigacao` compara as arestas dos filmes de referência com os filmes ainda não assistidos e guarda a maior similaridade encontrada. Como são utilizados no máximo três filmes de referência, o número de comparações cresce de forma linear em relação à quantidade de filmes.

## 3. Arquitetura em camadas

O projeto foi organizado seguindo MVC.

| Camada | Classes | Função |
|---|---|---|
| Model | `Filme`, `Catalogo`, `ArquivoGrafo` | Guarda os dados e regras do sistema |
| View | `ConsoleView` | Faz a entrada e saída pelo console |
| Controller | `FilmesController` | Controla o menu e as operações |
| Biblioteca | `Grafo<T>` | Implementa a estrutura genérica de grafo |
| Inicialização | `Main` | Cria os componentes e inicia o programa |

## 4. Manual de uso

Ao iniciar o programa, o arquivo `dados/filmes.txt` é carregado automaticamente.

O menu permite listar os filmes, avaliar filmes assistidos, receber uma recomendação, cadastrar novos filmes, alterar a similaridade entre dois filmes, executar a BFS e carregar outro arquivo.

Para avaliar um filme, o usuário informa o código mostrado na listagem e uma nota de 1 a 3.

Ao escolher a opção de recomendação, o programa considera até três filmes com as melhores avaliações e procura o filme ainda não assistido com maior similaridade.

## 5. Desenvolvimento em equipe

Integrantes:

- Dominic Cassidy
- Gabriel Lacerda
- Marcos Paulo

A divisão final das atividades deve ser preenchida de acordo com o que cada integrante desenvolveu ou revisou antes da entrega.

**GitHub:** adicionar a URL do repositório do Trabalho 3.

**Uso de IA:** ferramentas de Inteligência Artificial foram utilizadas como apoio para interpretar o enunciado, organizar as etapas do desenvolvimento, revisar o código e auxiliar na identificação de erros durante os testes. O grupo revisou e testou a implementação antes da entrega.
