package br.com.fgomes.cgd.activities;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.utils.DbHelper;

public class ConsultaJogadorActivity extends Activity
{
   private int m_retornoIdGrupo, m_retornoIdJogador;
   private TextView m_jogador_principal;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_consulta_jogador );

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

   private void init() {
      TableLayout stk = (TableLayout) findViewById(R.id.table_main);
      TableRow tbrow0 = new TableRow(this);
      TextView tv0 = new TextView(this);
      tv0.setText(" Id ");
      tv0.setTextColor(Color.WHITE);
      tbrow0.addView(tv0);
      TextView tv1 = new TextView(this);
      tv1.setText(" Data ");
      tv1.setTextColor(Color.WHITE);
      tbrow0.addView(tv1);
      TextView tv2 = new TextView(this);
      tv2.setText(" Jogador ");
      tv2.setTextColor(Color.WHITE);
      tbrow0.addView(tv2);
      TextView tv3 = new TextView(this);
      tv3.setText(" Adversário1 ");
      tv3.setTextColor(Color.WHITE);
      tbrow0.addView(tv3);
      TextView tv4 = new TextView(this);
      tv4.setText(" Adversário2 ");
      tv4.setTextColor(Color.WHITE);
      tbrow0.addView(tv4);
      stk.addView(tbrow0);
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
}
