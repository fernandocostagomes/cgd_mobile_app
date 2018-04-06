package br.com.fgomes.cgd.utils;

public class ItensListViewPointsDay implements Comparable<ItensListViewPointsDay>
{

   /** String para o textView que indicara o Name*/
   public String m_name;;
   /** int para o textView que indicara o Total de pontos menos os gatos*/
   public int m_pontos;
   /** int para o textView que indicara a quantidade total de gatos*/
   public int m_gatos;
   /**int para o textView que indicara a quantidade total de derrotas*/
   public int m_loses;

   @Override
   public int compareTo(ItensListViewPointsDay outroAluno) {
      if (this.getM_pontos() > outroAluno.getM_pontos()) {
         return -1;
      }
      if (this.getM_pontos() < outroAluno.getM_pontos()) {
         return 1;
      }
      return 0;
   }

   public ItensListViewPointsDay()
   {
   }

   public ItensListViewPointsDay(String p_name, int p_pontos, int p_gatos, int p_loses )
   {
      this.m_name = p_name;
      this.m_pontos = p_pontos;
      this.m_gatos = p_gatos;
      this.m_loses = p_loses;
   }

   public String getM_name() {
      return m_name;
   }

   public void setM_name(String m_name) {
      this.m_name = m_name;
   }

   public int getM_pontos() {
      return m_pontos;
   }

   public void setM_pontos(int m_pontos) {
      this.m_pontos = m_pontos;
   }

   public int getM_gatos() {
      return m_gatos;
   }

   public void setM_gatos(int m_gatos) {
      this.m_gatos = m_gatos;
   }

   public int getM_loses() {
      return m_loses;
   }

   public void setM_loses(int m_loses) {
      this.m_loses = m_loses;
   }
}
