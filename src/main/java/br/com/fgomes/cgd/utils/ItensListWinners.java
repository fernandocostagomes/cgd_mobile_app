package br.com.fgomes.cgd.utils;

/**
 * Created by fernando.gomes on 29/03/2018.
 */

public class ItensListWinners {
    /** String para o textView que indicara o Mes*/
    public String m_month;;
    /** String para o textView que indicara o Ano*/
    public String m_year;
    /** String para o textView que indicara os Pontos*/
    public String m_points;
    /** String para o textView que indicara o Nome*/
    public String m_winner;

    public ItensListWinners()
    {
    }

    public ItensListWinners(String p_month, String p_year, String p_points, String p_winner)
    {
        this.m_month = p_month;
        this.m_year = p_year;
        this.m_points = p_points;
        this.m_winner = p_winner;
    }

    public String getM_month() {
        return m_month;
    }

    public void setM_month(String m_month) {
        this.m_month = m_month;
    }

    public String getM_year() {
        return m_year;
    }

    public void setM_year(String m_year) {
        this.m_year = m_year;
    }

    public String getM_points() {
        return m_points;
    }

    public void setM_points(String m_points) {
        this.m_points = m_points;
    }

    public String getM_winner() {
        return m_winner;
    }

    public void setM_winner(String m_winner) {
        this.m_winner = m_winner;
    }
}
