# Projeto de Jogos Clássicos em Java

## 1. Visão Geral

Este projeto consiste em uma coleção de jogos clássicos desenvolvidos em Java, utilizando a biblioteca **Java Swing** para a criação de interfaces gráficas interativas. A aplicação é iniciada a partir de um menu central que permite ao usuário selecionar qual jogo deseja jogar.

O foco do projeto é demonstrar sólidos conhecimentos em **Programação Orientada a Objetos (POO)**, manipulação de eventos, lógica de jogos e design de interfaces de usuário em Java.

### Jogos Incluídos

1.  **Jogo de Damas**: Implementação completa do clássico jogo de tabuleiro, com todas as regras fundamentais, incluindo captura obrigatória e promoção de peças. Disponível em versão gráfica (GUI) e console.
2.  **Jogo das Bolas**: Um jogo dinâmico onde o objetivo é clicar em bolas coloridas que se movem pela tela.
3.  **Jogo de Perguntas e Respostas**: Um quiz sobre conhecimentos de programação em Java, com feedback visual instantâneo.

### Tecnologias Utilizadas

*   **Linguagem**: Java
*   **Interface Gráfica**: Java Swing e AWT

---

## 2. Como Executar o Projeto

Para compilar e executar o projeto, é necessário ter o **JDK (Java Development Kit)** instalado e configurado no seu sistema.

### Passos para Execução

1.  **Abra o Terminal** ou Prompt de Comando.

2.  **Navegue até o diretório `src`** do projeto, onde os arquivos `.java` estão localizados:
    ```sh
    cd caminho/para/o/projeto/Jogos/src
    ```

3.  **Compile todos os arquivos Java**. Execute o comando abaixo para compilar todas as classes necessárias:
    ```sh
    javac MenuInicial.java DamasGUI.java Main.java JogoPerguntasRespostasGUI.java JogoDamas.java Tabuleiro.java Peca.java
    ```

4.  **Execute o Menu Principal** para iniciar a aplicação gráfica:
    ```sh
    java MenuInicial
    ```

A partir do menu, você poderá acessar todos os jogos.

#### Execução Alternativa (Damas no Console)

Para jogar a versão do Jogo de Damas diretamente no terminal (sem interface gráfica), execute:
```sh
java JogoDamas
```

---

## 3. Análise Detalhada dos Jogos

### 3.1. Jogo de Damas

#### Descrição

Implementação fiel do jogo de Damas, onde o objetivo é capturar todas as peças do oponente. O jogo segue as regras oficiais, incluindo a obrigatoriedade de captura.

#### Funcionalidades Principais

*   **Movimentação Completa**: Distinção entre o movimento de peças comuns e o movimento multidirecional das "Damas".
*   **Captura Obrigatória**: O sistema detecta quando uma captura é possível e obriga o jogador a realizar o movimento, destacando as peças que podem capturar.
*   **Capturas Múltiplas**: Suporte para sequências de capturas com a mesma peça em um único turno.
*   **Promoção para Dama**: Uma peça comum é promovida a "Dama" automaticamente ao atingir a extremidade oposta do tabuleiro.
*   **Feedback Visual**: A interface gráfica destaca a peça selecionada, os movimentos válidos (em amarelo) e as peças com captura obrigatória (em azul).

#### Arquitetura e Classes Relevantes

*   `DamasGUI.java`: Responsável por toda a interface gráfica (GUI). Cria o tabuleiro de botões, exibe as peças e gerencia os eventos de clique do usuário para selecionar e mover peças.
*   `JogoDamas.java`: Controla a versão do jogo para console, lendo as coordenadas de entrada do usuário e exibindo o estado do tabuleiro no terminal.
*   `Tabuleiro.java`: O cérebro do jogo. Contém a matriz 8x8 que representa o tabuleiro, toda a lógica de validação de movimentos, execução de capturas, verificação de vitória e gerenciamento de turnos.
*   `Peca.java`: Modela uma peça individual, armazenando sua cor (branca ou preta) e seu estado (comum ou Dama).

### 3.2. Jogo das Bolas

#### Descrição

Um jogo dinâmico e interativo onde 36 bolas coloridas se movem pela tela, quicando nas bordas. O jogador deve clicar nas bolas para removê-las. O jogo reinicia quando todas as 6 bolas de uma mesma cor são eliminadas.

#### Funcionalidades Principais

*   **Animação Fluida**: As bolas se movem de forma contínua pela tela, com suas posições sendo atualizadas por um `javax.swing.Timer`.
*   **Detecção de Colisão**: As bolas detectam as bordas da janela e invertem sua direção, criando um efeito de "quicar".
*   **Interatividade**: O jogo responde aos cliques do mouse, removendo a bola clicada e contabilizando-a.

#### Arquitetura e Classes Relevantes

*   `Main.java`: Configura a janela principal (`JFrame`) do jogo e contém a classe `GamePanel`.
*   `GamePanel.java` (classe interna): O painel onde o jogo acontece. É responsável por desenhar as bolas, gerenciar o `Timer` para a animação e tratar os eventos de clique do mouse.
*   `Ball.java` (classe interna): Modela uma bola, contendo sua posição (x, y), velocidade (dx, dy) e cor.

### 3.3. Jogo de Perguntas e Respostas

#### Descrição

Um quiz de múltipla escolha com perguntas focadas em conceitos de Programação Orientada a Objetos em Java. O jogo fornece feedback instantâneo e calcula a pontuação final.

#### Funcionalidades Principais

*   **Feedback Visual Moderno**: Ao invés de pop-ups, o jogo utiliza cores para dar feedback: o botão da resposta correta fica verde e, em caso de erro, a opção escolhida fica vermelha.
*   **Transição Automática**: Após responder, há uma pequena pausa para o jogador ver o feedback, e então o jogo avança automaticamente para a próxima pergunta.
*   **Estrutura de Dados Limpa**: As perguntas, opções e respostas corretas são armazenadas em uma lista de objetos `Pergunta`, facilitando a adição de novo conteúdo.

#### Arquitetura e Classes Relevantes

*   `JogoPerguntasRespostasGUI.java`: Controla toda a lógica e a interface do quiz. Gerencia a exibição das perguntas, a verificação das respostas, a atualização da pontuação e o fluxo do jogo.
*   `Pergunta` (classe interna): Modela uma única pergunta do quiz, encapsulando o texto da pergunta, um array de opções e o índice da resposta correta.

---

## 4. Menu Principal

*   `MenuInicial.java`: É a porta de entrada da aplicação. Apresenta uma janela simples com botões para iniciar cada um dos três jogos. Ao selecionar um jogo, a janela do menu é fechada e a janela do jogo escolhido é aberta, garantindo uma experiência de usuário limpa.

---

**Data da Última Atualização**: 26/03/2026
