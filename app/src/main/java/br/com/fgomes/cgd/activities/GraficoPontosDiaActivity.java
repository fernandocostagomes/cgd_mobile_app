package br.com.fgomes.cgd.activities;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import br.com.fgomes.cgd.graph.SalesStackedBarChart;

public class GraficoPontosDiaActivity extends ListActivity
{
   private int m_retornoIdGrupo, m_retornoIdJogador;

   @Override
   public void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );

      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null )
      {
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null )
         {
            // vindo da activity main
            m_retornoIdJogador = parametrosRecebidos.getInt( "envioIdJogador" );
            m_retornoIdGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
         }
      }

      startActivity( ( new SalesStackedBarChart() ).execute( this, m_retornoIdJogador ) );
   }

   @Override
   public void onBackPressed()
   {
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
      startActivity( it );

      super.onBackPressed();
   }
}
