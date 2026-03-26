import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Tabuleiro {
    private Peca[][] tabuleiro = new Peca[8][8];
    private boolean turnoBranco = true;

    public Tabuleiro() {
        iniciarTabuleiro();
    }

    private void iniciarTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                if ((linha + coluna) % 2 == 1) {
                    if (linha < 3) {
                        tabuleiro[linha][coluna] = new Peca(false);
                    } else if (linha > 4) {
                        tabuleiro[linha][coluna] = new Peca(true);
                    }
                }
            }
        }
    }

    // Métodos básicos de acesso
    public Peca getPeca(int linha, int coluna) {
        if (!estaDentroTabuleiro(linha, coluna)) return null;
        return tabuleiro[linha][coluna];
    }

    public boolean getTurnoBranco() {
        return turnoBranco;
    }

    public void alternarTurno() {
        turnoBranco = !turnoBranco;
    }

    // Validação de movimentos
    public boolean validarMovimento(int linhaOrigem, int colunaOrigem, 
                                  int linhaDestino, int colunaDestino) {
        Peca peca = getPeca(linhaOrigem, colunaOrigem);
        if (peca == null) return false;
        
        if (!movimentoDiagonalValido(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
            return false;
        }
        
        return peca.isDama() ? 
            validarMovimentoDama(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino) :
            validarMovimentoPecaNormal(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
    }

    private boolean validarMovimentoPecaNormal(int linhaOrigem, int colunaOrigem, 
                                            int linhaDestino, int colunaDestino) {
        Peca peca = getPeca(linhaOrigem, colunaOrigem);
        
        // Verificar se há capturas obrigatórias
        if (existeCapturaDisponivel(peca.isBranca()) && 
            Math.abs(linhaDestino - linhaOrigem) == 1) {
            return false;
        }

        // Verificar direção do movimento
        if ((peca.isBranca() && linhaDestino > linhaOrigem) || 
            (!peca.isBranca() && linhaDestino < linhaOrigem)) {
            return false;
        }

        // Verificar captura
        if (Math.abs(linhaDestino - linhaOrigem) == 2) {
            return validarCapturaPecaNormal(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
        }

        // Movimento simples
        return Math.abs(linhaDestino - linhaOrigem) == 1 && 
               getPeca(linhaDestino, colunaDestino) == null;
    }

    private boolean validarMovimentoDama(int linhaOrigem, int colunaOrigem,
                                   int linhaDestino, int colunaDestino) {
    // Verifica se o destino está livre
    if (getPeca(linhaDestino, colunaDestino) != null) {
        return false;
    }
    
    // Verifica se é movimento diagonal
    if (!movimentoDiagonalValido(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
        return false;
    }
    
    // Se for movimento de captura
    if (Math.abs(linhaDestino - linhaOrigem) > 1) {
        return validarCapturaDama(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
    }
    
    // Movimento simples sem captura
    return true;
}

    // Lógica de captura
    private boolean validarCapturaPecaNormal(int linhaOrigem, int colunaOrigem,
                                           int linhaDestino, int colunaDestino) {
        int linhaMeio = (linhaOrigem + linhaDestino) / 2;
        int colunaMeio = (colunaOrigem + colunaDestino) / 2;
        
        Peca pecaMeio = getPeca(linhaMeio, colunaMeio);
        Peca pecaOrigem = getPeca(linhaOrigem, colunaOrigem);
        
        return pecaMeio != null && 
               pecaMeio.isBranca() != pecaOrigem.isBranca() && 
               getPeca(linhaDestino, colunaDestino) == null;
    }

    private boolean validarCapturaDama(int linhaOrigem, int colunaOrigem,
                                 int linhaDestino, int colunaDestino) {
    int dirLinha = Integer.signum(linhaDestino - linhaOrigem);
    int dirColuna = Integer.signum(colunaDestino - colunaOrigem);
    
    int pecasInimigas = 0;
    int linhaAtual = linhaOrigem + dirLinha;
    int colunaAtual = colunaOrigem + dirColuna;
    
    while (linhaAtual != linhaDestino || colunaAtual != colunaDestino) {
        Peca peca = getPeca(linhaAtual, colunaAtual);
        if (peca != null) {
            if (peca.isBranca() == getPeca(linhaOrigem, colunaOrigem).isBranca()) {
                return false; // Peça aliada bloqueando
            }
            pecasInimigas++;
        }
        linhaAtual += dirLinha;
        colunaAtual += dirColuna;
    }
    
    return pecasInimigas == 1; // Deve haver exatamente uma peça inimiga para capturar
}

private int[] ultimaPecaMovida = null; 

    // Movimentação de peças
    public void moverPeca(int linhaOrigem, int colunaOrigem, 
                     int linhaDestino, int colunaDestino) {
    if (!validarMovimento(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino)) {
        return;
    }

    boolean capturaRealizada = false;
    Peca peca = getPeca(linhaOrigem, colunaOrigem);
    
    if (Math.abs(linhaDestino - linhaOrigem) > 1) {
        if (peca.isDama()) {
            realizarCapturaDama(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
        } else {
            int linhaCapturada = (linhaOrigem + linhaDestino) / 2;
            int colunaCapturada = (colunaOrigem + colunaDestino) / 2;
            tabuleiro[linhaCapturada][colunaCapturada] = null;
        }
        capturaRealizada = true;
    }
    
    // Move a peça
    tabuleiro[linhaDestino][colunaDestino] = peca;
    tabuleiro[linhaOrigem][colunaOrigem] = null;
    
    // Verifica promoção
    verificarPromocao(linhaDestino, colunaDestino);
    
    // Verifica vitória
    if (verificarVitoria()) {
        return;
    }
    
    // Verifica captura múltipla
    if (capturaRealizada && peca.isDama() && podeCapturarNovamente(linhaDestino, colunaDestino)) {
        return; // Mantém o turno para captura adicional
    }
    
      // Atualiza a última peça movida
      ultimaPecaMovida = new int[]{linhaDestino, colunaDestino};
    
      // Verifica se pode capturar novamente
      if (capturaRealizada && podeCapturarNovamente(linhaDestino, colunaDestino)) {
          return; // Mantém o turno
      }
      
      ultimaPecaMovida = null; // Reseta se não houver mais capturas
      alternarTurno();
}

public boolean podeMoverOutraPeca(int linha, int coluna) {
    if (ultimaPecaMovida == null) return true;
    return linha == ultimaPecaMovida[0] && coluna == ultimaPecaMovida[1];
}

public int[] getUltimaPecaMovida() {
    return ultimaPecaMovida;
}

public boolean existeCapturaObrigatoria() {
    return !getPecasComCapturaObrigatoria().isEmpty();
}

    private boolean realizarCaptura(int linhaOrigem, int colunaOrigem,
                                  int linhaDestino, int colunaDestino) {
        if (Math.abs(linhaDestino - linhaOrigem) > 1) {
            if (getPeca(linhaOrigem, colunaOrigem).isDama()) {
                realizarCapturaDama(linhaOrigem, colunaOrigem, linhaDestino, colunaDestino);
            } else {
                int linhaCapturada = (linhaOrigem + linhaDestino) / 2;
                int colunaCapturada = (colunaOrigem + colunaDestino) / 2;
                tabuleiro[linhaCapturada][colunaCapturada] = null;
            }
            return true;
        }
        return false;
    }

    private void realizarCapturaDama(int linhaOrigem, int colunaOrigem,
                               int linhaDestino, int colunaDestino) {
    int dirLinha = Integer.signum(linhaDestino - linhaOrigem);
    int dirColuna = Integer.signum(colunaDestino - colunaOrigem);
    
    int linhaAtual = linhaOrigem + dirLinha;
    int colunaAtual = colunaOrigem + dirColuna;
    
    while (linhaAtual != linhaDestino || colunaAtual != colunaDestino) {
        Peca peca = getPeca(linhaAtual, colunaAtual);
        if (peca != null) {
            tabuleiro[linhaAtual][colunaAtual] = null; // Remove a peça capturada
            break;
        }
        linhaAtual += dirLinha;
        colunaAtual += dirColuna;
    }
}

    // Verificações de estado do jogo
    public boolean verificarVitoria() {
        int pecasBrancas = 0, pecasPretas = 0;
        
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                Peca peca = getPeca(i, j);
                if (peca != null) {
                    if (peca.isBranca()) pecasBrancas++;
                    else pecasPretas++;
                }
            }
        }
        
        if (pecasBrancas == 0 || pecasPretas == 0) {
            JOptionPane.showMessageDialog(null, 
                (pecasBrancas == 0 ? "Pretas" : "Brancas") + " venceram!");
            return true;
        }
        return false;
    }

    public boolean existeCapturaDisponivel(boolean paraPecasBrancas) {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = getPeca(linha, coluna);
                if (peca != null && peca.isBranca() == paraPecasBrancas && 
                    podeCapturar(linha, coluna)) {
                    return true;
                }
            }
        }
        return false;
    }

    // Métodos auxiliares
    private boolean estaDentroTabuleiro(int linha, int coluna) {
        return linha >= 0 && linha < 8 && coluna >= 0 && coluna < 8;
    }

    private boolean movimentoDiagonalValido(int linhaOrigem, int colunaOrigem,
                                          int linhaDestino, int colunaDestino) {
        return Math.abs(linhaDestino - linhaOrigem) == Math.abs(colunaDestino - colunaOrigem);
    }

    private boolean caminhoLivre(int linhaOrigem, int colunaOrigem,
                               int linhaDestino, int colunaDestino) {
        int dirLinha = Integer.signum(linhaDestino - linhaOrigem);
        int dirColuna = Integer.signum(colunaDestino - colunaOrigem);
        
        int linhaAtual = linhaOrigem + dirLinha;
        int colunaAtual = colunaOrigem + dirColuna;
        
        while (linhaAtual != linhaDestino || colunaAtual != colunaDestino) {
            if (getPeca(linhaAtual, colunaAtual) != null) {
                return false;
            }
            linhaAtual += dirLinha;
            colunaAtual += dirColuna;
        }
        return true;
    }

    private void verificarPromocao(int linha, int coluna) {
        Peca peca = getPeca(linha, coluna);
        if (peca != null && !peca.isDama()) {
            if ((peca.isBranca() && linha == 0) || (!peca.isBranca() && linha == 7)) {
                peca.tornarDama();
            }
        }
    }

    public boolean podeCapturarNovamente(int linha, int coluna) {
        Peca peca = getPeca(linha, coluna);
        if (peca == null || !peca.isDama()) return false;
        
        // Verifica nas 4 direções diagonais
        int[][] direcoes = {{-1,-1}, {-1,1}, {1,-1}, {1,1}};
        
        for (int[] dir : direcoes) {
            int distancia = 1;
            boolean encontrouInimigo = false;
            
            while (true) {
                int linhaAtual = linha + dir[0] * distancia;
                int colunaAtual = coluna + dir[1] * distancia;
                
                if (!estaDentroTabuleiro(linhaAtual, colunaAtual)) break;
                
                Peca pecaAtual = getPeca(linhaAtual, colunaAtual);
                
                if (pecaAtual != null) {
                    if (pecaAtual.isBranca() != peca.isBranca() && !encontrouInimigo) {
                        encontrouInimigo = true;
                    } else {
                        break; // Outra peça bloqueando
                    }
                } else if (encontrouInimigo) {
                    // Casa vazia após peça inimiga - captura possível
                    return true;
                }
                
                distancia++;
            }
        }
        
        return false;
    }

    public List<int[]> getPecasComCapturaObrigatoria() {
        List<int[]> pecas = new ArrayList<>();
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                Peca peca = getPeca(linha, coluna);
                if (peca != null && peca.isBranca() == turnoBranco) {
                    List<int[]> capturas = getCapturasObrigatorias(linha, coluna);
                    if (!capturas.isEmpty()) {
                        pecas.add(new int[]{linha, coluna});
                    }
                }
            }
        }
        return pecas;
    }

    public List<int[]> getCapturasObrigatorias(int linha, int coluna) {
        List<int[]> capturas = new ArrayList<>();
        Peca peca = tabuleiro[linha][coluna];

        if (peca == null) {
            return capturas; // Retorna lista vazia se não houver peça
        }

        // Lógica para verificar capturas obrigatórias
        // Exemplo: Verifica as direções diagonais
        int[][] direcoes = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}}; // Direções diagonais

        for (int[] dir : direcoes) {
            int linhaCaptura = linha + dir[0] * 2;
            int colunaCaptura = coluna + dir[1] * 2;
            int linhaMeio = linha + dir[0];
            int colunaMeio = coluna + dir[1];

            // Verifica se a captura é válida
            if (estaDentroTabuleiro(linhaCaptura, colunaCaptura) && 
                tabuleiro[linhaMeio][colunaMeio] != null && 
                tabuleiro[linhaMeio][colunaMeio].isBranca() != peca.isBranca() && 
                tabuleiro[linhaCaptura][colunaCaptura] == null) {
                capturas.add(new int[]{linhaCaptura, colunaCaptura}); // Adiciona a captura à lista
            }
        }

        return capturas; // Retorna a lista de capturas obrigatórias
    }

    public void imprimirTabuleiro() {
        for (int linha = 0; linha < 8; linha++) {
            for (int coluna = 0; coluna < 8; coluna++) {
                System.out.print(tabuleiro[linha][coluna] != null ? 
                    (tabuleiro[linha][coluna].isDama() ? "D" : "P") + 
                    (tabuleiro[linha][coluna].isBranca() ? "B" : "P") + " " : ".  ");
            }
            System.out.println();
        }
    }

    private boolean podeCapturar(int linha, int coluna) {
        Peca peca = getPeca(linha, coluna);
        if (peca == null) return false;
    
        int[][] direcoes = peca.isDama() ? 
            new int[][]{{-1,-1}, {-1,1}, {1,-1}, {1,1}} :
            peca.isBranca() ? new int[][]{{-1,-1}, {-1,1}} : new int[][]{{1,-1}, {1,1}};
    
        for (int[] dir : direcoes) {
            int distancia = peca.isDama() ? 2 : 1;
            int linhaAlvo = linha + dir[0] * distancia;
            int colunaAlvo = coluna + dir[1] * distancia;
            int linhaDestino = linha + dir[0] * (distancia + 1);
            int colunaDestino = coluna + dir[1] * (distancia + 1);
    
            if (estaDentroTabuleiro(linhaAlvo, colunaAlvo) && 
                estaDentroTabuleiro(linhaDestino, colunaDestino)) {
                Peca pecaAlvo = getPeca(linhaAlvo, colunaAlvo);
                Peca pecaDestino = getPeca(linhaDestino, colunaDestino);
    
                if (pecaAlvo != null && 
                    pecaAlvo.isBranca() != peca.isBranca() && 
                    pecaDestino == null) {
                    return true;
                }
            }
        }
        return false;
    }
}