package br.com.fgomes.cgd.objects;

public class Parametro {

   private int m_idParametro;
   private String m_nomeParametro;
   private int m_valorParametro;

   public Parametro()
   {
      this.m_idParametro = 0;
      this.m_nomeParametro = "";
      this.m_valorParametro = 0;
	}

   public int getIdParametro()
   {
      return m_idParametro;
	}

   public void setIdParametro( int p_idParametro )
   {
      this.m_idParametro = p_idParametro;
	}

   public String getNomeParametro()
   {
      return m_nomeParametro;
	}

   public void setNomeParametro( String p_nomeParametro )
   {
      this.m_nomeParametro = p_nomeParametro;
	}

   public int getValorParametro()
   {
      return m_valorParametro;
	}

   public void setValorParametro( int p_valorParametro )
   {
      this.m_valorParametro = p_valorParametro;
	}
}
