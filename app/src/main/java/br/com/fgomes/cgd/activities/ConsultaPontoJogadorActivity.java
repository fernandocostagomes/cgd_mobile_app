package br.com.fgomes.cgd.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Pontos;
import br.com.fgomes.cgd.utils.DbHelper;

public class ConsultaPontoJogadorActivity extends Activity
{

   private int m_retornoIdGrupo, m_retornoIdJogador;
   private List<Pontos> m_listPointDetails;
   private TextView m_jogador_principal;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_consulta_ponto_jogador );

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

      DbHelper db = new DbHelper( this );
      m_listPointDetails = db.consultarPointDetalhado( m_retornoIdJogador );

      m_jogador_principal = ( TextView )findViewById( R.id.tvPlayer );

      ArrayList<Jogador> jogador = new ArrayList<Jogador>( db.selectJogadoresGrupo( m_retornoIdGrupo ) );

      for ( Jogador jogador2 : jogador )
      {
         if ( jogador2.getIdJogador() == m_retornoIdJogador )
         {
            m_jogador_principal.setText( jogador2.getNomeJogador().toUpperCase() );
            break;
         }
      }

      init();

   }

   private void init()
   {
      TableLayout stk = ( TableLayout )findViewById( R.id.table_main );
      TableRow tbrow0 = new TableRow( this );
      TextView tv0 = new TextView( this );
      tv0.setText( " Id " );
      tv0.setTextColor( Color.WHITE );
      tbrow0.addView( tv0 );
      TextView tv1 = new TextView( this );
      tv1.setText( " Data " );
      tv1.setTextColor( Color.WHITE );
      tbrow0.addView( tv1 );
      TextView tv2 = new TextView( this );
      tv2.setText( " Jogador1 " );
      tv2.setTextColor( Color.WHITE );
      tbrow0.addView( tv2 );
      TextView tv3 = new TextView( this );
      tv3.setText( " Jogador2 " );
      tv3.setTextColor( Color.WHITE );
      tbrow0.addView( tv3 );
      TextView tv4 = new TextView( this );
      tv4.setText( " Qtd Pontos " );
      tv4.setTextColor( Color.WHITE );
      tbrow0.addView( tv4 );
      stk.addView( tbrow0 );

      for ( Pontos pontoDetalhado : m_listPointDetails )
      {
         TableRow tbrow = new TableRow( this );
         TextView t1v = new TextView( this );
         t1v.setText( String.valueOf( pontoDetalhado.getIdPoint() ) );
         t1v.setTextColor( Color.WHITE );
         t1v.setGravity( Gravity.LEFT );
         t1v.setPadding( 0, 0, 5, 0 );
         tbrow.addView( t1v );
         TextView t2v = new TextView( this );
         t2v.setText( android.text.format.DateFormat.format( "dd/MM", pontoDetalhado.getDtmPoint() ) );
         t2v.setTextColor( Color.WHITE );
         t2v.setGravity( Gravity.CENTER );
         t2v.setPadding( 0, 0, 5, 0 );
         tbrow.addView( t2v );
         TextView t3v = new TextView( this );
         t3v.setText( getNomeJogador( pontoDetalhado.getIdJogador1() ) );
         t3v.setTextColor( Color.WHITE );
         t3v.setGravity( Gravity.CENTER );
         tbrow.addView( t3v );
         TextView t4v = new TextView( this );
         t4v.setText( getNomeJogador( pontoDetalhado.getIdJogador2() ) );
         t4v.setTextColor( Color.WHITE );
         t4v.setGravity( Gravity.CENTER );
         tbrow.addView( t4v );
         TextView t5v = new TextView( this );
         t5v.setText( String.valueOf( pontoDetalhado.getQtdPoint() ) );
         t5v.setTextColor( Color.WHITE );
         t5v.setGravity( Gravity.CENTER );
         tbrow.addView( t5v );
         stk.addView( tbrow );
      }
   }

   private String getNomeJogador( int p_idJogador )
   {
      String name = "";

      DbHelper db = new DbHelper( this );
      List<Jogador> listPlayer = db.selectJogadoresGrupo( m_retornoIdGrupo );

      for ( Jogador jogador : listPlayer )
      {
         if ( jogador.getIdJogador() == p_idJogador )
         {
            name = jogador.getNomeJogador();
            break;
         }
      }

      return name;
   }

   @Override
   public void onBackPressed()
   {
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
      startActivity( it );

      super.onBackPressed();
   }

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate( R.menu.consulta_ponto_jogador, menu );
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
}
