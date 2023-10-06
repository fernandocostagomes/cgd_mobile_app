package br.com.fgomes.cgd.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.utils.DbHelper;

public class RemoverPontoActivity extends Activity
{
   // atributo da classe.
   private AlertDialog m_alert;
   // Caixa de texto
   private EditText m_etRemovePointId;
   private EditText m_etRemovePoint;
   // Bnaco de dados
   DbHelper m_db = new DbHelper( this );
   private int m_retornoIdJogador;
   private int m_retornoIdGrupo;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_remover_ponto );

      Intent iDadosRecebidos = getIntent();
      if ( iDadosRecebidos != null )
      {
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();

         if ( parametrosRecebidos != null )
         {
            // vindo do GrupoInicio
            m_retornoIdJogador = parametrosRecebidos.getInt( "envioIdJogador" );
            m_retornoIdGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
         }
      }

      m_etRemovePointId = ( EditText )findViewById( R.id.etRemoverPontoId );

      m_etRemovePoint = ( EditText )findViewById( R.id.etRemoverPoint );
   }

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate( R.menu.remover_gato, menu );
      return true;
   }

   @Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      int id = item.getItemId();
      if ( id == R.id.action_settings )
      {
         return true;
      }
      return super.onOptionsItemSelected( item );
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
