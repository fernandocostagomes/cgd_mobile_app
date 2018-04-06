package br.com.fgomes.cgd.objects;

import java.util.Date;

public class Pontos
{
   private int m_idPoint;
   private Date m_dtmPoint;
   private int m_idJogador1;
   private int m_idJogador2;
   private int m_qtdPoint;
   private int m_idGato1;
   private int m_idGato2;
   private int m_idGrupo;

   public int getIdPoint()
   {
      return m_idPoint;
   }

   public void setIdPoint( int p_idPoint )
   {
      this.m_idPoint = p_idPoint;
   }

   public Date getDtmPoint()
   {
      return m_dtmPoint;
   }

   public void setDtmPoint( Date p_dtmPoint )
   {
      this.m_dtmPoint = p_dtmPoint;
   }

   public int getIdJogador1()
   {
      return m_idJogador1;
   }

   public void setIdJogador1( int p_idJogador1 )
   {
      this.m_idJogador1 = p_idJogador1;
   }

   public int getIdJogador2()
   {
      return m_idJogador2;
   }

   public void setIdJogador2( int p_idJogador2 )
   {
      this.m_idJogador2 = p_idJogador2;
   }

   public int getQtdPoint()
   {
      return m_qtdPoint;
   }

   public void setQtdPoint( int p_qtdPoint )
   {
      this.m_qtdPoint = p_qtdPoint;
   }

   public int getIdGato1()
   {
      return m_idGato1;
   }

   public void setIdGato1( int m_idGato1 )
   {
      this.m_idGato1 = m_idGato1;
   }

   public int getIdGato2()
   {
      return m_idGato2;
   }

   public void setIdGato2( int m_idGato2 )
   {
      this.m_idGato2 = m_idGato2;
   }

   public int getIdGrupo()
   {
      return m_idGrupo;
   }

   public void setIdGrupo( int m_idGrupo )
   {
      this.m_idGrupo = m_idGrupo;
   }
}
