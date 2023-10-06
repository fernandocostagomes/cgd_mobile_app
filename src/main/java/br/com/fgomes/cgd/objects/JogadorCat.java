package br.com.fgomes.cgd.objects;

import java.util.Date;

public class JogadorCat implements Comparable<JogadorCat>
{
   private int m_idJogador;
   private String m_nomeJogador;
   private long m_telefoneJogador;
   private String m_emailJogador;
   private Date m_dataJogador;
   private int m_idGrupo;
   private int m_gatoJogador;
   private int m_pointJogador;
   private int m_pointsDay;

   public JogadorCat()
   {
      this.m_idJogador = 0;
      this.m_nomeJogador = "";
      this.m_emailJogador = "";
      this.m_telefoneJogador = 0;
      this.m_dataJogador = new Date();
      this.m_idGrupo = 0;
      this.m_gatoJogador = 0;
      this.m_pointJogador = 0;
      m_pointsDay = 0;
   }

   public int getIdJogador()
   {
      return m_idJogador;
   }

   public void setIdJogador( int p_idJogador )
   {
      this.m_idJogador = p_idJogador;
   }

   public String getNomeJogador()
   {
      return m_nomeJogador;
   }

   public void setNomeJogador( String p_nomeJogador )
   {
      this.m_nomeJogador = p_nomeJogador;
   }

   public long getTelefoneJogador()
   {
      return m_telefoneJogador;
   }

   public void setTelefoneJogador( long p_telefoneJogador )
   {
      this.m_telefoneJogador = p_telefoneJogador;
   }

   public String getEmailJogador()
   {
      return m_emailJogador;
   }

   public void setEmailJogador( String p_emailJogador )
   {
      this.m_emailJogador = p_emailJogador;
   }

   public Date getDataJogador()
   {
      return m_dataJogador;
   }

   public void setDataJogador( Date p_dataJogador )
   {
      this.m_dataJogador = p_dataJogador;
   }

   public int getIdGrupo()
   {
      return m_idGrupo;
   }

   public void setIdGrupo( int p_idGrupo )
   {
      this.m_idGrupo = p_idGrupo;
   }

   public int getGatoJogador()
   {
      return m_gatoJogador;
   }

   public void setGatoJogador( int p_gatoJogador )
   {
      this.m_gatoJogador = p_gatoJogador;
   }

   public int getPointsDay()
   {
      return m_pointsDay;
   }

   public void setPointsDay( int m_pointsDay )
   {
      this.m_pointsDay = m_pointsDay;
   }

   @Override
   public String toString()
   {
      return m_nomeJogador + "         " + m_gatoJogador;
   }

   public int getPointJogador()
   {
      return m_pointJogador;
   }

   public void setPointJogador( int m_pointJogador )
   {
      this.m_pointJogador = m_pointJogador;
   }

   public int compareTo( JogadorCat compareJogador )
   {

      int compareQuantity = ( ( JogadorCat )compareJogador ).getGatoJogador();

      // ascending order
      // return this.m_pointJogador - compareQuantity;

      // descending order
      return compareQuantity - this.m_gatoJogador;

   }
}
