
import javax.swing.JOptionPane;

/**
 * Armazena o tabuleiro e responsavel por posicionar as pecas.
 *
 * @author Alan Moraes &lt;alan@ci.ufpb.br&gt;
 * @author Leonardo Villeth &lt;lvilleth@cc.ci.ufpb.br&gt;
 */
public class Jogo {

    /*
     * Criei algumas variavéis de diferentes tipos para fazer o codigo rodar com as
     * REGRAS
     */
    private Tabuleiro tabuleiro;
    private int turno; // O turno decide qual cor vai jogar, e determina se o jogador deve continuar;
    private int pecaantiga; /*
                             * Armazena a o CPF(cpf é uma variavel única que eu criei que toda peça tem que
                             * funciona parecido com os numeros do cpf da vida real) da peça antiga, util
                             * para manter a mesma peça combando;
                             */
    private int cpfdamav; // Armazena o CPF da peca vermelha, para quando ela virar dama manter o msm CPF
    private int cpfdamab; // Armazena o CPF da peca branca, para quando ela virar dama manter o msm CPF
    private boolean combo; // Avisa que a pedra comum pode fazer combo
    private boolean combodama; // Avisa que a dama pode fazer combo
    private boolean passPelaBordaSup; /*
                                       * Armazena: caso a peça branca esteja combando e ela tiver passado pela casa de
                                       * promoção
                                       */
    private boolean passPelaBordaInf; /*
                                       * Armazena: caso a peça vermelha esteja combando e ela tiver passado pela casa
                                       * de promoção
                                       */

    // Inicia o JOGO E EXIBE UMA MENSAGEM DE BOAS VINDAS AO JOGADOR
    public Jogo() {
        JOptionPane.showMessageDialog(null, "Seja bem-vindo ao jogo de damas!\n Clique em OK para começar a jogar!");
        tabuleiro = new Tabuleiro();
        criarPecas();
    }

    /**
     * Posiciona pe�as no tabuleiro. Utilizado na inicializa�ao do jogo.
     */
    private void criarPecas() {
        int k; // Valor inteiro criado para gerar o cpf de cada peça
        k = 0;
        int l; // Valor inteiro criado para gerar o cpf de cada peça
        l = 13;

        for (int i = 0; i < 8; i = i + 2) {
            for (int j = 0; j < 3; j++) {
                if (j % 2 == 0) {
                    Casa casa = tabuleiro.getCasa(i, j);
                    Peca peca = new Peca(casa, Peca.PEDRA_BRANCA, k);
                    k++;
                } else {
                    Casa casa = tabuleiro.getCasa(i + 1, j);
                    Peca peca = new Peca(casa, Peca.PEDRA_BRANCA, k);
                    k++;
                }
            }
        }

        for (int i = 0; i < 8; i = i + 2) {
            for (int j = 7; j > 4; j--) {
                if (j % 2 == 0) {
                    Casa casa = tabuleiro.getCasa(i, j);
                    Peca peca = new Peca(casa, Peca.PEDRA_VERMELHA, l);
                    l++;
                } else {
                    Casa casa = tabuleiro.getCasa(i + 1, j);
                    Peca peca = new Peca(casa, Peca.PEDRA_VERMELHA, l);
                    l++;
                }
            }
        }
    }

    /**
     * MÉTODO QUE DIZ SE UM TIME FOI VENCEDOR
     * BASEADO SE AINDA HÁ PEÇAS DE UMA COR NO TABULEIRO
     */

    public void Vencedor() {
        Casa testecasa;
        Peca testePeca;
        int pecaver = 0;
        int pecabra = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                testecasa = tabuleiro.getCasa(i, j);

                if (testecasa.possuiPeca() == true) {
                    testePeca = testecasa.getPeca();

                    if (testePeca.getTipo() == 0 || testePeca.getTipo() == 1) {
                        pecabra++;
                    }

                    if (testePeca.getTipo() == 2 || testePeca.getTipo() == 3) {
                        pecaver++;
                    }
                }
            }
        }
        if (pecabra == 0) {
            JOptionPane.showMessageDialog(null, "FIM DE JOGO!\n AS VERMELHAS GANHARAM!");
        }
        if (pecaver == 0) {
            JOptionPane.showMessageDialog(null, "FIM DE JOGO!\n AS BRANCAS GRANHARAM!");
        }
    }

    /**
     * Caso a peça passe pela casa de promoção enquanto estiver combando esse método
     * é executado. Ele consiste em questionar ao usuário
     * se ele quer transformar a pedra em Dama no final do
     * combo.
     */

    public void DamaAposCombo(int origemX, int origemY, int destinoX, int destinoY) {

        int dialogButton = JOptionPane.showConfirmDialog(null,
                "Deseja transformar a peça em dama?\n Yes = Sim\n No = Não", "A PEÇA POSSUI COMBOS",
                JOptionPane.YES_NO_OPTION);

        if (dialogButton == JOptionPane.YES_OPTION) {
            Casa destino = tabuleiro.getCasa(destinoX, destinoY);
            Peca peca = destino.getPeca();

            cpfdamab = peca.getCpf();
            cpfdamav = peca.getCpf();

            if (peca.getTipo() == 0) {
                destino.removerPeca();
                peca = new Peca(destino, Peca.DAMA_BRANCA, cpfdamab);
            }

            if (peca.getTipo() == 2) {
                destino.removerPeca();
                peca = new Peca(destino, Peca.DAMA_VERMELHA, cpfdamav);
            }
        } else {
            // não precisa acontecer nada se o jogador não quiser transformar a peça.
        }
    }

    /**
     * Caso o jogador chegue a pedra chegue na borda do lado adversário do tabuleiro
     * esse método vai transformar a pedra em DAMA.
     */

    public void virarDama(int destinoX, int destinoY) {
        Casa casa = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = casa.getPeca();

        cpfdamab = peca.getCpf();
        cpfdamav = peca.getCpf();

        if (peca.getTipo() == 0 && destinoY == 7) {
            casa.removerPeca();
            peca = new Peca(casa, Peca.DAMA_BRANCA, cpfdamab);
        }

        if (peca.getTipo() == 2 && destinoY == 0) {
            casa.removerPeca();
            peca = new Peca(casa, Peca.DAMA_VERMELHA, cpfdamav);
        }
    }

    /*
     * Um método que testa o COMBO das DAMAS vermelhas. Caso positivo ele retorna o
     * combo como true, e ajusta o turno para o combo das damas
     */

    public void testaComboDamaV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa possdestino;
        Casa possMeio;
        Peca possPeca;

        for (int i = destinoX, j = destinoY; i < 8 || j < 8; i++, j++) {

            try {
                possdestino = tabuleiro.getCasa(i + 1, j + 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 0 || possPeca.getTipo() == 1)) {
                        combodama = true;
                        turno = 6;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }

        }

        for (int i = destinoX, j = destinoY; i < 8 || j > -1; i++, j--) {

            try {
                possdestino = tabuleiro.getCasa(i + 1, j - 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 0 || possPeca.getTipo() == 1)) {
                        combodama = true;
                        turno = 6;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        for (int i = destinoX, j = destinoY; i > -1 || j < 8; i--, j++) {

            try {
                possdestino = tabuleiro.getCasa(i - 1, j + 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 0 || possPeca.getTipo() == 1)) {
                        combodama = true;
                        turno = 6;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        for (int i = destinoX, j = destinoY; i > -1 || j > -1; i--, j--) {

            try {
                possdestino = tabuleiro.getCasa(i - 1, j - 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 0 || possPeca.getTipo() == 1)) {
                        combodama = true;
                        turno = 6;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }

        }
    }

    /*
     * Um método que testa o COMBO das DAMAS brancas. Caso positivo ele retorna o
     * combo como true, e ajusta o turno para o combo das damas
     */

    public void testaComboDamaB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa possdestino;
        Casa possMeio;
        Peca possPeca;

        for (int i = destinoX, j = destinoY; i < 8 || j < 8; i++, j++) {

            try {
                possdestino = tabuleiro.getCasa(i + 1, j + 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 2 || possPeca.getTipo() == 3)) {
                        combodama = true;
                        turno = 5;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }

        }

        for (int i = destinoX, j = destinoY; i < 8 || j > -1; i++, j--) {

            try {
                possdestino = tabuleiro.getCasa(i + 1, j - 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 2 || possPeca.getTipo() == 3)) {
                        combodama = true;
                        turno = 5;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        for (int i = destinoX, j = destinoY; i > -1 || j < 8; i--, j++) {

            try {
                possdestino = tabuleiro.getCasa(i - 1, j + 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 2 || possPeca.getTipo() == 3)) {
                        combodama = true;
                        turno = 5;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }
        }

        for (int i = destinoX, j = destinoY; i > -1 || j > -1; i--, j--) {

            try {
                possdestino = tabuleiro.getCasa(i - 1, j - 1);
                possMeio = tabuleiro.getCasa(i, j);
                possPeca = possMeio.getPeca();
                if (possMeio.possuiPeca() == true) {
                    if ((possdestino.possuiPeca() == false) && (possPeca.getTipo() == 2 || possPeca.getTipo() == 3)) {
                        combodama = true;
                        turno = 5;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                break;
            }

        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão no centro
     * do Tabuleiro. Caso positivo ele retorna o combo como true, e ajusta o turno
     * para o combo das Pedras.
     */

    public void testaComboB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pFinal2;
        Casa pFinal3;
        Casa pFinal4;
        Casa pMeio1;
        Casa pMeio2;
        Casa pMeio3;
        Casa pMeio4;
        Peca pecaMeio1;
        Peca pecaMeio2;
        Peca pecaMeio3;
        Peca pecaMeio4;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if (((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo()) == 2 || (pecaMeio1.getTipo() == 3))
                && (pFinal1.possuiPeca() == false))
                || ((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo() == 2) || (pecaMeio2.getTipo() == 3))
                        && (pFinal2.possuiPeca() == false))
                || ((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo() == 2) || (pecaMeio3.getTipo() == 3))
                        && (pFinal3.possuiPeca() == false))
                || ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo() == 2) || (pecaMeio4.getTipo() == 3))
                        && (pFinal4.possuiPeca() == false))) {
            turno = 3;
            combo = true;
        } else {
            turno = 1;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS Vemelhas COMUNS quando estão no centro
     * do Tabuleiro. Caso positivo ele retorna o combo como true, e ajusta o turno
     * para o combo das Pedras.
     */

    public void testaComboV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pFinal2;
        Casa pFinal3;
        Casa pFinal4;
        Casa pMeio1;
        Casa pMeio2;
        Casa pMeio3;
        Casa pMeio4;
        Peca pecaMeio1;
        Peca pecaMeio2;
        Peca pecaMeio3;
        Peca pecaMeio4;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if (((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo() == 0) || (pecaMeio1.getTipo() == 1))
                && (pFinal1.possuiPeca() == false))
                || ((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo() == 0) || (pecaMeio2.getTipo() == 1))
                        && (pFinal2.possuiPeca() == false))
                || ((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo() == 0) || (pecaMeio3.getTipo() == 1))
                        && (pFinal3.possuiPeca() == false))
                || ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo() == 0) || (pecaMeio4.getTipo() == 1))
                        && (pFinal4.possuiPeca() == false))) {
            turno = 4;
            combo = true;
        } else {
            turno = 0;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão no canto
     * superior direito do Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboSupDirB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pMeio1;
        Peca pecaMeio1;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        if ((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo()) == 2 || (pecaMeio1.getTipo() == 3))
                && (pFinal1.possuiPeca() == false)) {
            turno = 3;
            combo = true;
            if (destinoY == 7) {
                passPelaBordaSup = true;
            }
        } else {
            turno = 1;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão no canto
     * superior direito do Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboSupDirV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pMeio1;
        Peca pecaMeio1;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        if ((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo() == 0) || (pecaMeio1.getTipo() == 1))
                && (pFinal1.possuiPeca() == false)) {
            turno = 4;
            combo = true;
        } else {
            turno = 0;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão no canto
     * superior esquerdo do Tabuleiro. Caso positivo ele retorna o combo como true,
     * e ajusta o turno para o combo das Pedras.
     */

    public void testaComboSupEsqB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal3;
        Casa pMeio3;
        Peca pecaMeio3;

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        if ((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo()) == 2 || (pecaMeio3.getTipo() == 3))
                && (pFinal3.possuiPeca() == false)) {
            turno = 3;
            combo = true;
            if (destinoY == 7) {
                passPelaBordaSup = true;
            }
        } else {
            turno = 1;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão no canto
     * superior esquerdo do Tabuleiro. Caso positivo ele retorna o combo como true,
     * e ajusta o turno para o combo das Pedras.
     */

    public void testaComboSupEsqV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal3;
        Casa pMeio3;
        Peca pecaMeio3;

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        if ((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo() == 0) || (pecaMeio3.getTipo() == 1))
                && (pFinal3.possuiPeca() == false)) {
            turno = 4;
            combo = true;
        } else {
            turno = 0;
            combo = false;
        }

    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão no canto
     * inferior direito do Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboInfDirB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal2;
        Casa pMeio2;
        Peca pecaMeio2;

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        if ((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo()) == 2 || (pecaMeio2.getTipo() == 3))
                && (pFinal2.possuiPeca() == false)) {
            turno = 3;
            combo = true;
        } else {
            turno = 1;
            combo = false;
        }

    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão no canto
     * inferior direito do Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboInfDirV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal2;
        Casa pMeio2;
        Peca pecaMeio2;

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        if ((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo() == 0) || (pecaMeio2.getTipo() == 1))
                && (pFinal2.possuiPeca() == false)) {
            turno = 4;
            combo = true;
            if (destinoY == 0) {
                passPelaBordaInf = true;
            }
        } else {
            turno = 0;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão no canto
     * inferior esquerdo do Tabuleiro. Caso positivo ele retorna o combo como true,
     * e ajusta o turno para o combo das Pedras.
     */

    public void testaComboInfEsqB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal4;
        Casa pMeio4;
        Peca pecaMeio4;

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo()) == 2 || (pecaMeio4.getTipo() == 3))
                && (pFinal4.possuiPeca() == false)) {
            turno = 3;
            combo = true;
        } else {
            turno = 1;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão no canto
     * inferior esquerdo do Tabuleiro. Caso positivo ele retorna o combo como true,
     * e ajusta o turno para o combo das Pedras.
     */

    public void testaComboInfEsqV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal4;
        Casa pMeio4;
        Peca pecaMeio4;

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo() == 0) || (pecaMeio4.getTipo() == 1))
                && (pFinal4.possuiPeca() == false)) {
            turno = 4;
            combo = true;
            if (destinoY == 0) {
                passPelaBordaInf = true;
            }
        } else {
            turno = 0;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão no
     * inferior central Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboInferiorB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal2;
        Casa pFinal4;
        Casa pMeio2;
        Casa pMeio4;
        Peca pecaMeio2;
        Peca pecaMeio4;

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if (((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo()) == 2 || (pecaMeio2.getTipo() == 3))
                && (pFinal2.possuiPeca() == false))
                || ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo()) == 2 || (pecaMeio4.getTipo() == 3))
                        && (pFinal4.possuiPeca() == false))) {
            turno = 3;
            combo = true;
        } else {
            turno = 1;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão no
     * inferior central Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboInferiorV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal2;
        Casa pFinal4;
        Casa pMeio2;
        Casa pMeio4;
        Peca pecaMeio2;
        Peca pecaMeio4;

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if (((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo() == 0) || (pecaMeio2.getTipo() == 1))
                && (pFinal2.possuiPeca() == false))
                || ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo() == 0) || (pecaMeio4.getTipo() == 1))
                        && (pFinal4.possuiPeca() == false))) {
            turno = 4;
            combo = true;
            if (destinoY == 0) {
                passPelaBordaInf = true;
            }
        } else {
            turno = 0;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão no
     * superior central Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboSuperiorB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pFinal3;
        Casa pMeio1;
        Casa pMeio3;
        Peca pecaMeio1;
        Peca pecaMeio3;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        if (((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo()) == 2 || (pecaMeio1.getTipo() == 3))
                && (pFinal1.possuiPeca() == false))
                || ((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo()) == 2 || (pecaMeio3.getTipo() == 3))
                        && (pFinal3.possuiPeca() == false))) {
            turno = 3;
            combo = true;
            if (destinoY == 7) {
                passPelaBordaSup = true;
            }
        } else {
            turno = 1;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão no
     * superior central Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboSuperiorV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pFinal3;
        Casa pMeio1;
        Casa pMeio3;
        Peca pecaMeio1;
        Peca pecaMeio3;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        if (((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo() == 0) || (pecaMeio1.getTipo() == 1))
                && (pFinal1.possuiPeca() == false))
                || ((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo() == 0) || (pecaMeio3.getTipo() == 1))
                        && (pFinal3.possuiPeca() == false))) {
            turno = 4;
            combo = true;

        } else {
            turno = 0;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão na
     * esquerda central Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboEsquerdaB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal3;
        Casa pFinal4;
        Casa pMeio3;
        Casa pMeio4;
        Peca pecaMeio3;
        Peca pecaMeio4;

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if (((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo()) == 2 || (pecaMeio3.getTipo() == 3))
                && (pFinal3.possuiPeca() == false))
                || ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo()) == 2 || (pecaMeio4.getTipo() == 3))
                        && (pFinal4.possuiPeca() == false))) {
            turno = 3;
            combo = true;
        } else {
            turno = 1;
            combo = false;
        }

    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão na
     * esquerda central Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboEsquerdaV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal3;
        Casa pFinal4;
        Casa pMeio3;
        Casa pMeio4;
        Peca pecaMeio3;
        Peca pecaMeio4;

        pMeio3 = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
        pecaMeio3 = pMeio3.getPeca();
        pFinal3 = tabuleiro.getCasa(destinoX + 2, destinoY - 2);

        pMeio4 = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
        pecaMeio4 = pMeio4.getPeca();
        pFinal4 = tabuleiro.getCasa(destinoX + 2, destinoY + 2);

        if (((pMeio3.possuiPeca() == true) && ((pecaMeio3.getTipo() == 0) || (pecaMeio3.getTipo() == 1))
                && (pFinal3.possuiPeca() == false))
                || ((pMeio4.possuiPeca() == true) && ((pecaMeio4.getTipo() == 0) || (pecaMeio4.getTipo() == 1))
                        && (pFinal4.possuiPeca() == false))) {
            turno = 4;
            combo = true;
        } else {
            turno = 0;
            combo = false;
        }
    }

    /*
     * Um método que testa o COMBO das PEDRAS brancas COMUNS quando estão na direita
     * central Tabuleiro. Caso positivo ele retorna o combo como true, e ajusta o
     * turno para o combo das Pedras.
     */

    public void testaComboDireitaB(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pFinal2;
        Casa pMeio1;
        Casa pMeio2;
        Peca pecaMeio1;
        Peca pecaMeio2;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        if (((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo()) == 2 || (pecaMeio1.getTipo() == 3))
                && (pFinal1.possuiPeca() == false))
                || ((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo()) == 2 || (pecaMeio2.getTipo() == 3))
                        && (pFinal2.possuiPeca() == false))) {
            turno = 3;
            combo = true;
        } else {
            turno = 1;
            combo = false;
        }

    }

    /*
     * Um método que testa o COMBO das PEDRAS vermelhas COMUNS quando estão na
     * direita central Tabuleiro. Caso positivo ele retorna o combo como true, e
     * ajusta o turno para o combo das Pedras.
     */

    public void testaComboDireitaV(int origemX, int origemY, int destinoX, int destinoY) {

        Casa pFinal1;
        Casa pFinal2;
        Casa pMeio1;
        Casa pMeio2;
        Peca pecaMeio1;
        Peca pecaMeio2;

        pMeio1 = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
        pecaMeio1 = pMeio1.getPeca();
        pFinal1 = tabuleiro.getCasa(destinoX - 2, destinoY - 2);

        pMeio2 = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
        pecaMeio2 = pMeio2.getPeca();
        pFinal2 = tabuleiro.getCasa(destinoX - 2, destinoY + 2);

        if (((pMeio1.possuiPeca() == true) && ((pecaMeio1.getTipo() == 0) || (pecaMeio1.getTipo() == 1))
                && (pFinal1.possuiPeca() == false))
                || ((pMeio2.possuiPeca() == true) && ((pecaMeio2.getTipo() == 0) || (pecaMeio2.getTipo() == 1))
                        && (pFinal2.possuiPeca() == false))) {
            turno = 4;
            combo = true;
        } else {
            turno = 0;
            combo = false;
        }
    }

    /**
     * NESSE MÉTODO A MÁGICA ACONTECE! Nele é testado as possiveis as
     * mudanças no tabuleiro. Está contido DIRETAMENTE o movimento das peças
     * comuns, o movimento e captura das DAMAS!
     */

    public void moverPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = origem.getPeca();
        int diferencaY = java.lang.Math.abs(destinoY - origemY);
        int diferencaX = java.lang.Math.abs(destinoX - origemX);

        if (destino.possuiPeca() == true) { // Se o destino da peça tiver outra peça ele
            return;
        }
        if (peca == null) { // Se o usuário clicar em uma casa vazia
            return;
        }

        /*
         * TESTANDO OS MOVIMENTOS DAS PEÇAS BRANCAS
         */

        if ((peca.getTipo() == 0)) {
            if (((origemX - destinoX == 1) || (origemX - destinoX == -1)) && (origemY - destinoY == -1)
                    && (turno == 0)) {
                peca.mover(destino);
                virarDama(destinoX, destinoY);
                turno = 1;
            } else {
                if ((turno == 0) && (((destinoX - origemX == 2) && (destinoY - origemY == 2))
                        || ((destinoX - origemX == 2) && (destinoY - origemY == -2))
                        || ((destinoX - origemX == -2) && (destinoY - origemY == 2))
                        || ((destinoX - origemX == -2) && (destinoY - origemY == -2)))) {
                    capturarPeca(origemX, origemY, destinoX, destinoY);
                    if (combo == false) {
                        virarDama(destinoX, destinoY);
                    }
                }
                if ((turno == 3) && (peca.getCpf() == pecaantiga)) {
                    capturarPeca(origemX, origemY, destinoX, destinoY);
                    if (combo == false) {
                        virarDama(destinoX, destinoY);
                    }

                }
            }
        }
        
        //Movimento e captura das DAMAS brancas:
        if ((peca.getTipo() == 1) && (turno == 0 || turno == 5)) {
            int quantidadeCasas = 0;
            int quantidadePecasAdv = 0;
            if ((diferencaY == diferencaX) && (turno == 0 || turno == 5)) {
                if (destinoX < origemX && destinoY > origemY) {
                    for (int i = origemX - 1, j = origemY + 1; i >= destinoX && j <= destinoY; i--, j++) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 2 || possPeca.getTipo() == 3) {
                                quantidadePecasAdv++;
                            }
                        }
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 5 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX - 1, j = origemY + 1; i >= destinoX && j <= destinoY; i--, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 0) {
                        for (int i = origemX - 1, j = origemY + 1; i >= destinoX && j <= destinoY; i--, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 0) {
                        peca.mover(destino);
                        turno = 1;
                    }

                }

                if (destinoX > origemX && destinoY > origemY) {
                    for (int i = origemX + 1, j = origemY + 1; i <= destinoX && j <= destinoY; i++, j++) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 2 || possPeca.getTipo() == 3) {
                                quantidadePecasAdv++;
                            }
                        }
                    }
                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 5 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX + 1, j = origemY + 1; i <= destinoX && j <= destinoY; i++, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 0) {
                        for (int i = origemX + 1, j = origemY + 1; i <= destinoX && j <= destinoY; i++, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 0) {
                        peca.mover(destino);
                        turno = 1;
                    }
                }

                if (destinoX < origemX && destinoY < origemY) {
                    for (int i = origemX - 1, j = origemY - 1; i >= destinoX && j >= destinoY; i--, j--) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 2 || possPeca.getTipo() == 3) {
                                quantidadePecasAdv++;
                            }
                        }
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 5 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX - 1, j = origemY - 1; i >= destinoX && j >= destinoY; i--, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 0) {
                        for (int i = origemX - 1, j = origemY - 1; i >= destinoX && j >= destinoY; i--, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 0) {
                        peca.mover(destino);
                        turno = 1;
                    }
                }

                if (destinoX > origemX && destinoY < origemY) {
                    for (int i = origemX + 1, j = origemY - 1; i <= destinoX && j >= destinoY; i++, j--) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 2 || possPeca.getTipo() == 3) {
                                quantidadePecasAdv++;
                            }
                        }
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 5 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX + 1, j = origemY - 1; i <= destinoX && j >= destinoY; i++, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 0) {
                        for (int i = origemX + 1, j = origemY - 1; i <= destinoX && j >= destinoY; i++, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaB(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 1;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 0) {
                        peca.mover(destino);
                        turno = 1;
                    }
                }
            }
        }

        /*
         *
         * TESTANDO MOVIMENTO DAS PEÇAS VERMELHAS AGORA
         *
         */

        if (peca.getTipo() == 2 && (turno == 1 || turno == 4)) {

            if (((origemX - destinoX == 1) || (origemX - destinoX == -1)) && (origemY - destinoY == 1)
                    && (turno == 1)) {
                peca.mover(destino);
                virarDama(destinoX, destinoY);
                turno = 0;
            }
            if ((turno == 1) && (((destinoX - origemX == 2) && (destinoY - origemY == 2))
                    || ((destinoX - origemX == 2) && (destinoY - origemY == -2))
                    || ((destinoX - origemX == -2) && (destinoY - origemY == 2))
                    || ((destinoX - origemX == -2) && (destinoY - origemY == -2)))) {
                capturarPeca(origemX, origemY, destinoX, destinoY);
                if (combo == false) {
                    virarDama(destinoX, destinoY);
                }
            }
            if ((turno == 4) && (peca.getCpf() == pecaantiga)) {
                capturarPeca(origemX, origemY, destinoX, destinoY);
                if (combo == false) {
                    virarDama(destinoX, destinoY);
                }

            }

        }
        
        //Movimento e captura das DAMAS vermelhas:
        if ((peca.getTipo() == 3)) {
            int quantidadeCasas = 0;
            int quantidadePecasAdv = 0;
            if ((diferencaY == diferencaX) && (turno == 1 || turno == 6)) {
                if (destinoX < origemX && destinoY > origemY) {
                    for (int i = origemX - 1, j = origemY + 1; i >= destinoX && j <= destinoY; i--, j++) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 0 || possPeca.getTipo() == 1) {
                                quantidadePecasAdv++;
                            }
                        }
                    }
                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 6 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX - 1, j = origemY + 1; i >= destinoX && j <= destinoY; i--, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 1) {
                        for (int i = origemX - 1, j = origemY + 1; i >= destinoX && j <= destinoY; i--, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 1) {
                        peca.mover(destino);
                        turno = 0;
                    }

                }

                if (destinoX > origemX && destinoY > origemY) {
                    for (int i = origemX + 1, j = origemY + 1; i <= destinoX && j <= destinoY; i++, j++) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 0 || possPeca.getTipo() == 1) {
                                quantidadePecasAdv++;
                            }
                        }
                    }
                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 6 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX + 1, j = origemY + 1; i <= destinoX && j <= destinoY; i++, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 1) {
                        for (int i = origemX + 1, j = origemY + 1; i <= destinoX && j <= destinoY; i++, j++) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 1) {
                        peca.mover(destino);
                        turno = 0;
                    }
                }

                if (destinoX < origemX && destinoY < origemY) {
                    for (int i = origemX - 1, j = origemY - 1; i >= destinoX && j >= destinoY; i--, j--) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 0 || possPeca.getTipo() == 1) {
                                quantidadePecasAdv++;
                            }
                        }
                    }
                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 6 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX - 1, j = origemY - 1; i >= destinoX && j >= destinoY; i--, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 1) {
                        for (int i = origemX - 1, j = origemY - 1; i >= destinoX && j >= destinoY; i--, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 1) {
                        peca.mover(destino);
                        turno = 0;
                    }
                }

                if (destinoX > origemX && destinoY < origemY) {
                    for (int i = origemX + 1, j = origemY - 1; i <= destinoX && j >= destinoY; i++, j--) {
                        Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                        Peca possPeca = casaPossivelPeca.getPeca();
                        if (casaPossivelPeca.possuiPeca()) {
                            quantidadeCasas += 1;
                            if (possPeca.getTipo() == 0 || possPeca.getTipo() == 1) {
                                quantidadePecasAdv++;
                            }
                        }
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 6 && peca.getCpf() == pecaantiga) {
                        for (int i = origemX + 1, j = origemY - 1; i <= destinoX && j >= destinoY; i++, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        peca.mover(destino);
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            combodama = false;
                        }
                    }

                    if (quantidadeCasas == 1 && quantidadePecasAdv == 1 && turno == 1) {
                        for (int i = origemX + 1, j = origemY - 1; i <= destinoX && j >= destinoY; i++, j--) {
                            Casa casaPossivelPeca = tabuleiro.getCasa(i, j);
                            casaPossivelPeca.removerPeca();
                        }
                        testaComboDamaV(origemX, origemY, destinoX, destinoY);
                        if (combodama == false) {
                            turno = 0;
                        }
                        if (combodama == true) {
                            pecaantiga = peca.getCpf();
                            combodama = false;
                        }
                        peca.mover(destino);
                    }

                    if (quantidadeCasas == 0 && turno == 1) {
                        peca.mover(destino);
                        turno = 0;
                    }
                }
            }
        }

        Vencedor();
    }

    /**
     * MÉTODO QUE TESTA A POSSÍVEL CAPTURA DAS PEDRAS COMUNS
     */

    public void capturarPeca(int origemX, int origemY, int destinoX, int destinoY) {
        Casa origem = tabuleiro.getCasa(origemX, origemY);
        Casa destino = tabuleiro.getCasa(destinoX, destinoY);
        Peca peca = origem.getPeca();
        Casa meiotermo;
        Peca metade;

        if (peca == null) {
            return;
        }

        if (peca.getTipo() == 0) {
            if ((destinoX - origemX == 2) && (destinoY - origemY == 2)) {
                meiotermo = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 2) || (metade.getTipo() == 3))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaSup == true && destinoY != 7) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaSup = false;
                    }
                }
            }
            if ((destinoX - origemX == 2) && (destinoY - origemY == -2)) {
                meiotermo = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 2) || (metade.getTipo() == 3))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaSup == true && destinoY != 7) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaSup = false;
                    }
                }
            }
            if ((destinoX - origemX == -2) && (destinoY - origemY == 2)) {
                meiotermo = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 2) || (metade.getTipo() == 3))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaSup == true && destinoY != 7) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaSup = false;
                    }
                }
            }
            if ((destinoX - origemX == -2) && (destinoY - origemY == -2)) {
                meiotermo = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 2) || (metade.getTipo() == 3))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }

                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqB(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaSup == true && destinoY != 7) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaSup = false;
                    }
                }
            }

        }
        if (peca.getTipo() == 2) {
            if ((destinoX - origemX == 2) && (destinoY - origemY == 2)) {
                meiotermo = tabuleiro.getCasa(destinoX - 1, destinoY - 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 0) || (metade.getTipo() == 1))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaInf == true && destinoY != 0) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaInf = false;
                    }

                }
            }
            if ((destinoX - origemX == 2) && (destinoY - origemY == -2)) {
                meiotermo = tabuleiro.getCasa(destinoX - 1, destinoY + 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 0) || (metade.getTipo() == 1))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }

                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaInf == true && destinoY != 0) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaInf = false;
                    }
                }
            }
            if ((destinoX - origemX == -2) && (destinoY - origemY == 2)) {
                meiotermo = tabuleiro.getCasa(destinoX + 1, destinoY - 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 0) || (metade.getTipo() == 1))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaInf == true && destinoY != 0) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaInf = false;
                    }
                }
            }
            if ((destinoX - origemX == -2) && (destinoY - origemY == -2)) {
                meiotermo = tabuleiro.getCasa(destinoX + 1, destinoY + 1);
                metade = meiotermo.getPeca();
                if ((meiotermo.possuiPeca() == true) && ((metade.getTipo() == 0) || (metade.getTipo() == 1))) {
                    meiotermo.removerPeca();
                    peca.mover(destino);
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }

                    }
                    if ((destinoX > 5) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboDireitaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 1) && (destinoY < 6)) {
                        testaComboEsquerdaV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY < 2)) {
                        testaComboInferiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 1) && (destinoX < 6) && (destinoY > 5)) {
                        testaComboSuperiorV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY > 5)) {
                        testaComboSupDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX > 5) && (destinoY < 2)) {
                        testaComboInfDirV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY > 5)) {
                        testaComboSupEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if ((destinoX < 2) && (destinoY < 2)) {
                        testaComboInfEsqV(origemX, origemY, destinoX, destinoY);
                        if (combo == true) {
                            pecaantiga = peca.getCpf();
                        }
                    }
                    if (combo == false && passPelaBordaInf == true && destinoY != 0) {
                        DamaAposCombo(origemX, origemY, destinoX, destinoY);
                        passPelaBordaInf = false;
                    }
                }
            }

        }
    }

    /**
     * @return o Tabuleiro em jogo.
     */
    public Tabuleiro getTabuleiro() {
        return tabuleiro;
    }
}

