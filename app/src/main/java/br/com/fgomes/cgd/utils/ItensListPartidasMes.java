package br.com.fgomes.cgd.utils;

public class ItensListPartidasMes
{
   /** String para o textView que indicara o Id da partida*/
   public String m_id;;
   /** String para o textView que indicara a data*/
   public String m_date;
   /** String para o textView que indicara o nome do vencedor 1.*/
   public String m_v1;
   /** String para o textView que indicara o nome do vencedor 2.*/
   public String m_v2;
   /** String para o textView que indicara o nome do perdedor 1.*/
   public String m_p1;
   /** String para o textView que indicara o nome do perdedor 2.*/
   public String m_p2;
   /** String para o textView que indicara os pontos de cada partida*/
   public String m_points;

   public ItensListPartidasMes()
   {

   }

   public ItensListPartidasMes( String p_id, String p_date, String p_v1, String p_v2, String p_p1, String p_p2, String p_points )
   {
      this.m_id = p_id;
      this.m_date = p_date;
      this.m_p1 = p_v1;
      this.m_p2 = p_v2;
      this.m_p1 = p_p1;
      this.m_p2 = p_p2;
      this.m_points = p_points;
   }

   public String getM_id()
   {
      return m_id;
   }

   public void setM_id( String m_id )
   {
      this.m_id = m_id;
   }

   public String getM_date()
   {
      return m_date;
   }

   public void setM_date( String m_date )
   {
      this.m_date = m_date;
   }

   public String getM_p1()
   {
      return m_p1;
   }

   public void setM_p1( String m_p1 )
   {
      this.m_p1 = m_p1;
   }

   public String getM_p2()
   {
      return m_p2;
   }

   public void setM_p2( String m_p2 )
   {
      this.m_p2 = m_p2;
   }

   public String getM_points()
   {
      return m_points;
   }

   public void setM_points( String m_points )
   {
      this.m_points = m_points;
   }

   public String getM_v1() {
      return m_v1;
   }

   public void setM_v1(String m_v1) {
      this.m_v1 = m_v1;
   }

   public String getM_v2() {
      return m_v2;
   }

   public void setM_v2(String m_v2) {
      this.m_v2 = m_v2;
   }
}
