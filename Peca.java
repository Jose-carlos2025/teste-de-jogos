public class Peca {
    private boolean isBranca; // Verdadeiro para branco, falso para preto
    private boolean isDama;   // Indica se a peça é uma dama (Rei)

    public Peca(boolean isBranca) {
        this.isBranca = isBranca;
        this.isDama = false; // Peças começam como não damas
    }
    public int getDirecao() {
        return isBranca() ? 1 : -1;
    }

    public boolean isBranca() {
        return isBranca;
    }

    public boolean isDama() {
        return isDama;
    }
    public boolean podeMoverMultiplasCasas() {
        return isDama; // Somente damas podem mover múltiplas casas
    }
    
    public boolean podeCapturarParaTras() {
        return isDama; // Somente damas podem capturar para trás
    }


    public void tornarDama() {
        this.isDama = true; // Converte a peça em dama
    }

    @Override
    public String toString() {
        return isBranca ? (isDama ? "B£" : "B") : (isDama ? "P£" : "P");
    }
}
