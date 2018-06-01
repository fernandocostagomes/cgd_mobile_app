package br.com.fgomes.cgd.utils;

import java.util.*;

public class ItensListViewInicio{
   /** String para o textView que indicara o Name*/
   public String m_name;
   /** String para o textView que indicara o Total de pontos menos os gatos*/
   public String m_pontos;
   /** String para o textView que indicara a quantidade total de gatos*/
   public String m_gatos;
   /** int para o textView que indicara o total de pontos*/
   public int m_total_pontos;
   /** int para o textView que indicara o total de derrotas*/
   public int m_loses;
   /** int para o textView que indicara o total de vitorias*/
   public int m_wins;

   public ItensListViewInicio (){}

   public ItensListViewInicio( String p_name, String p_ptTotal, String p_ptCat, int p_total, int p_loses, int p_wins ){
      this.m_name = p_name;
      this.m_pontos = p_ptTotal;
      this.m_gatos = p_ptCat;
      this.m_total_pontos = p_total;
      this.m_loses = p_loses;
      this.m_wins = p_wins;
   }

   public String getM_name()
   {
      return m_name;
   }

   public void setM_name( String m_name )
   {
      this.m_name = m_name;
   }

   public String getM_pontos()
   {
      return m_pontos;
   }

   public void setM_pontos( String m_pontos )
   {
      this.m_pontos = m_pontos;
   }

   public String getM_gatos()
   {
      return m_gatos;
   }

   public void setM_gatos( String m_gatos )
   {
      this.m_gatos = m_gatos;
   }

   public int getM_total_pontos()
   {
      return m_total_pontos;
   }

   public void setM_total_pontos( int m_total_pontos )
   {
      this.m_total_pontos = m_total_pontos;
   }

   public int getM_loses()
   {
      return m_loses;
   }

   public void setM_loses( int m_loses )
   {
      this.m_loses = m_loses;
   }

   public int getM_wins() {
      return m_wins;
   }

   public void setM_wins(int m_wins) {
      this.m_wins = m_wins;
   }
}
