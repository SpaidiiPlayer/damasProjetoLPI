
/**
 * Representa uma Pe�a do jogo. Possui uma casa e um tipo associado.
 *
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Peca {

    public static final int PEDRA_BRANCA = 0;
    public static final int DAMA_BRANCA = 1;
    public static final int PEDRA_VERMELHA = 2;
    public static final int DAMA_VERMELHA = 3;

    private Casa casa;
    private int tipo;
    private int cpf;

    public Peca(Casa casa, int tipo, int cpf) {
        this.casa = casa;
        this.tipo = tipo;
        this.cpf = cpf;
        casa.colocarPeca(this);
    }

    /**
     * Movimenta a peca para uma nova casa.
     *
     * @param destino nova casa que ira conter esta peca.
     */
    public void mover(Casa destino) {
        casa.removerPeca();
        destino.colocarPeca(this);
        casa = destino;
    }

    /**
     * Valor Tipo 0 Branca (Pedra) 1 Branca (Dama) 2 Vermelha (Pedra) 3 Vermelha
     * (Dama)
     *
     * @return o tipo da peca.
     */
    public int getTipo() {
        return tipo;
    }

    /**
    * Retorna o "CPF" da peça
    */
    public int getCpf() {
        return cpf;
    }
}