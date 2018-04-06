package br.com.fgomes.cgd.objects;

public class DebitoCredito
{
   private int m_idJogador;
   private String m_nomeJogador;
   private int m_credito;
   private int m_debito;
   private int m_saldo;

   public DebitoCredito()
   {
      m_idJogador = 0;
      m_nomeJogador = "";
      m_credito = 0;
      m_debito = 0;
      m_saldo = 0;
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

   public int getCredito()
   {
      return m_credito;
   }

   public void setCredito( int p_credito )
   {
      this.m_credito = p_credito;
   }

   public int getDebito()
   {
      return m_debito;
   }

   public void setDebito( int p_debito )
   {
      this.m_debito = p_debito;
   }

   public int getSaldo()
   {
      return m_saldo;
   }

   public void setSaldo( int p_saldo )
   {
      this.m_saldo = p_saldo;
   }

}
