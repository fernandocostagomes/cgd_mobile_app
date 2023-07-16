package br.com.fgomes.cgd.objects;

import java.sql.Date;

public class Grupo {
   private int m_idGrupo;
   private String m_nomeGrupo;
   private String m_senhaGrupo;
   private Date m_dataGrupo;
   private long m_telefoneGrupo;

   public Grupo()
   {
      this.m_idGrupo = 0;
      this.m_nomeGrupo = "";
      this.m_senhaGrupo = "";
      this.m_dataGrupo = new Date( System.currentTimeMillis() );
      this.m_telefoneGrupo = 0L;
	}

   public int getIdGrupo()
   {
      return m_idGrupo;
	}

   public void setIdGrupo( int p_idGrupo )
   {
      this.m_idGrupo = p_idGrupo;
	}

   public String getNomeGrupo()
   {
      return m_nomeGrupo;
	}

   public void setNomeGrupo( String p_nomeGrupo )
   {
      this.m_nomeGrupo = p_nomeGrupo;
	}

   public String getSenhaGrupo()
   {
      return m_senhaGrupo;
	}

   public void setSenhaGrupo( String p_senhaGrupo )
   {
      this.m_senhaGrupo = p_senhaGrupo;
	}

   public Date getDataGrupo()
   {
      return m_dataGrupo;
	}

   public void setDataGrupo( Date p_dataGrupo )
   {
      this.m_dataGrupo = p_dataGrupo;
	}

   public long getTelefoneGrupo()
   {
      return m_telefoneGrupo;
	}

   public void setTelefoneGrupo( long p_telefoneGrupo )
   {
      this.m_telefoneGrupo = p_telefoneGrupo;
	}

	@Override
   public String toString()
   {
      return "Grupo: " + m_nomeGrupo;
	}
}
