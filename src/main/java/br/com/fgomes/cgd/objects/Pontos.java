package br.com.fgomes.cgd.objects;

import android.content.Context;

import java.util.Date;
import java.util.List;

import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListPartidasMes;

public class Pontos {
    private Date m_dtmPoint;
    private int m_idPoint, m_idJogador1, m_idJogador2, m_qtdPoint, m_idGato1,
            m_idGato2, m_idGrupo;
    private Context m_context;

    public Pontos() {
    }

    public Pontos(Context pContext) {
        this.m_context = pContext;
    }

    public PontosPeriodo getCampeao(int pIdGrupo, String pDateStart, String pDateEnd) {
        List<Jogador> listJogador = DbHelper.getInstance(
                m_context).selectJogadoresGrupo(pIdGrupo);

        PontosPeriodo pontos = new PontosPeriodo();

        int idWins = 0, idGatos = 0;
        //Varre a lista de jogadores
        for (Jogador jogador : listJogador) {
            idWins = jogador.getIdJogador();
            idGatos = jogador.getIdJogador();
            int qtdWins = 0, qtdGatos = 0;

            //Qtd de pontos do jogador.
            qtdWins = getQtdWins(idWins, pIdGrupo, pDateStart, pDateEnd);

            //Qtd de gatos do jogador.
            qtdGatos = getQtdGatos(idGatos, pIdGrupo, pDateStart, pDateEnd);

            //Pega a quantidade de gatos para diminuir dos pontos.
            qtdWins = qtdWins - qtdGatos;

            //Se a quantidade de wins for maior que a quantidade de wins do jogador
            //então seta o id do jogador e a quantidade de wins.
            if ( qtdWins >= pontos.getQtdWin()) {
                //Tem o mesmo numero de wins, quem tiver mais gatos ganha.
                if ( qtdWins == pontos.getQtdWin()){
                    if (qtdGatos > getQtdGatos(pontos.getIdGatosJogador(),
                            pIdGrupo, pDateStart, pDateEnd))
                        continue;
                }
                pontos.setIdWinJogador(idWins);
                pontos.setQtdWin(qtdWins);
            }

            //Se a quantidade de gatos for maior que a quantidade de gatos do jogador
            //então seta o id do jogador e a quantidade de gatos.
            if (qtdGatos >= pontos.getQtdGatos()) {
                //Criterio de desempate é o numero de wins.
                if ( qtdGatos == pontos.getQtdGatos()){
                    if(qtdWins > getQtdWins(pontos.getIdWinJogador(),
                            pIdGrupo, pDateStart, pDateEnd))
                        continue;
                }
                pontos.setIdGatosJogador(idGatos);
                pontos.setQtdGatos(qtdGatos);
            }
        }
        return pontos;
    }

    public int getQtdWins(int pJogador, int pIdGrupo,
                          String pDateStart, String pDateEnd) {
        List<ItensListPartidasMes> list = DbHelper.getInstance(
                m_context).selectItensPartidas(pIdGrupo, pDateStart, pDateEnd);

        int qtdWins = 0;

        for (ItensListPartidasMes item : list) {
           if (Integer.parseInt(item.get_v1()) == pJogador ||
                        Integer.parseInt(item.get_v2()) == pJogador) {
                    qtdWins = qtdWins + Integer.parseInt( item.get_points());
                }
        }
        return qtdWins;
    }

    public int getQtdGatos(int pIdJogador, int pIdGrupo,
                           String pDateStart, String pDateEnd) {
        List<ItensListPartidasMes> list = DbHelper.getInstance(
                m_context).selectItensPartidas(pIdGrupo, pDateStart, pDateEnd);

        int qtdGatos = 0;

        for (ItensListPartidasMes item : list) {
            if (Integer.parseInt(item.get_p1()) == pIdJogador ||
                    Integer.parseInt(item.get_p2()) == pIdJogador) {
                if (Integer.parseInt(item.get_points()) == 3)
                    qtdGatos++;
            }
        }

        return qtdGatos;
    }

    public int getIdPoint() {
        return m_idPoint;
    }

    public void setIdPoint(int p_idPoint) {
        this.m_idPoint = p_idPoint;
    }

    public Date getDtmPoint() {
        return m_dtmPoint;
    }

    public void setDtmPoint(Date p_dtmPoint) {
        this.m_dtmPoint = p_dtmPoint;
    }

    public int getIdJogador1() {
        return m_idJogador1;
    }

    public void setIdJogador1(int p_idJogador1) {
        this.m_idJogador1 = p_idJogador1;
    }

    public int getIdJogador2() {
        return m_idJogador2;
    }

    public void setIdJogador2(int p_idJogador2) {
        this.m_idJogador2 = p_idJogador2;
    }

    public int getQtdPoint() {
        return m_qtdPoint;
    }

    public void setQtdPoint(int p_qtdPoint) {
        this.m_qtdPoint = p_qtdPoint;
    }

    public int getIdGato1() {
        return m_idGato1;
    }

    public void setIdGato1(int m_idGato1) {
        this.m_idGato1 = m_idGato1;
    }

    public int getIdGato2() {
        return m_idGato2;
    }

    public void setIdGato2(int m_idGato2) {
        this.m_idGato2 = m_idGato2;
    }

    public int getIdGrupo() {
        return m_idGrupo;
    }

    public void setIdGrupo(int m_idGrupo) {
        this.m_idGrupo = m_idGrupo;
    }
}
