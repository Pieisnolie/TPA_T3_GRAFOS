# Sistema de Recomendação de Filmes com Grafos

Projeto em **Java** desenvolvido para o Trabalho 3 de TPA.

O sistema utiliza um **grafo completo e ponderado** para representar a similaridade entre filmes e gerar recomendações a partir das avaliações do usuário.

## Integrantes

- Dominic Cassidy
- Gabriel Lacerda
- Marcos Paulo

## Ideia do Projeto

Cada vértice do grafo representa um filme.

Cada aresta representa a similaridade entre dois filmes e possui um peso entre `0` e `1`.

Quanto mais próximo de `1`, maior é a similaridade entre os filmes.

Exemplo:

```text
Interestelar -> A Origem = 0.90
Interestelar -> Titanic = 0.15
```

O usuário pode avaliar os filmes que já assistiu utilizando três níveis:

```text
1 - Não gostei
2 - Mais ou menos
3 - Gostei muito
```

O sistema seleciona até três filmes com as melhores avaliações e procura, no grafo, o filme ainda não assistido com maior grau de similaridade com eles.

## Representação do Grafo

O grafo é representado por uma **matriz de adjacências**.

Cada posição da matriz guarda o peso da aresta entre dois filmes.

Como o grafo utilizado no sistema é completo, cada filme possui uma ligação com todos os outros filmes.

A matriz é simétrica porque a similaridade é considerada igual nos dois sentidos.

## Biblioteca de Grafos

A classe genérica `Grafo<T>` permite:

- adicionar vértices;
- adicionar arestas com peso;
- consultar vértices e pesos;
- fazer caminhamento em largura (BFS);
- encontrar a maior ligação de similaridade entre os filmes de referência e os candidatos.

## Estrutura do Projeto

```text
src/main/java
├── controller
│   └── FilmesController.java
├── grafo
│   └── Grafo.java
├── model
│   ├── ArquivoGrafo.java
│   ├── Catalogo.java
│   └── Filme.java
├── view
│   └── ConsoleView.java
└── Main.java
```

O projeto segue uma organização em **MVC**:

- **Model:** dados dos filmes, catálogo e leitura do arquivo;
- **View:** entrada e saída pelo console;
- **Controller:** controla as opções do menu e chama as operações necessárias;
- **Grafo:** biblioteca genérica utilizada pelo sistema.

## Arquivo de Entrada

O catálogo inicial fica em:

```text
dados/filmes.txt
```

Cada linha contém um filme e os pesos de similaridade com os filmes que apareceram anteriormente no arquivo.

Exemplo:

```text
Interestelar
A Origem;0.90
Titanic;0.15;0.25
Perdido em Marte;0.88;0.55;0.20
```

Nesse exemplo:

- A Origem possui similaridade `0.90` com Interestelar;
- Titanic possui similaridade `0.15` com Interestelar e `0.25` com A Origem.

## Menu

Ao executar o programa são apresentadas as opções:

```text
===== MENU =====
1 - Listar filmes
2 - Avaliar filme assistido
3 - Recomendar filme
4 - Cadastrar filme
5 - Definir similaridade entre dois filmes
6 - Caminhamento em largura (BFS)
7 - Carregar outro arquivo
0 - Sair
```

## Recomendação

A recomendação funciona da seguinte forma:

1. O usuário avalia os filmes que já assistiu.
2. O sistema seleciona até três filmes começando pelas maiores notas.
3. Os filmes já assistidos são retirados das opções de recomendação.
4. O sistema compara os pesos das arestas dos filmes escolhidos com os filmes restantes.
5. O filme com maior similaridade é recomendado.

## Busca em Largura

A biblioteca também possui o método `caminharEmLargura`.

O método utiliza um vetor como fila e percorre os vértices do grafo em largura.

Como o grafo do projeto é completo, a partir de qualquer filme todos os outros filmes são vizinhos diretos.

## Complexidade

Como a representação utilizada é uma matriz de adjacências:

- consultar um peso por índice: `O(1)`;
- busca de um vértice no vetor: `O(V)`;
- caminhamento em largura: `O(V²)`;
- espaço da matriz: `O(V²)`.

Na recomendação são analisados no máximo três filmes de referência contra os filmes do catálogo. Como a quantidade de referências é limitada a três, o custo cresce de forma linear em relação à quantidade de filmes.

## Dependências

O projeto utiliza somente recursos padrão do Java e as classes desenvolvidas no próprio projeto.

É necessário:

- **Java JDK 11 ou superior**;
- IntelliJ IDEA ou outra IDE Java.

Não são necessárias bibliotecas externas.

## Execução

Abra o projeto no IntelliJ IDEA e execute:

```text
src/main/java/Main.java
```

O programa carrega automaticamente o arquivo:

```text
dados/filmes.txt
```

Também é possível informar outro arquivo pelo menu durante a execução.

## Tecnologias e Recursos

- Java
- Generics
- Grafos
- Matriz de adjacências
- BFS
- MVC
- Scanner
- BufferedReader
- Files
- Path
- IntelliJ IDEA
- Git
- GitHub
