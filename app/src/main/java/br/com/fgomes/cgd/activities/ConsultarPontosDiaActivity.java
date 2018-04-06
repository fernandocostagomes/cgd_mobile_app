package br.com.fgomes.cgd.activities;

import java.util.ArrayList;
import java.util.Collections;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Pontos;
import br.com.fgomes.cgd.utils.DbHelper;

public class ConsultarPontosDiaActivity extends Activity
{
   private DbHelper m_db = new DbHelper( this );
   private int m_retornoIdGrupo;
   private int m_idGrupo;
   private ArrayAdapter<Jogador> m_grupoAdapter = null;
   ArrayList<Jogador> m_listJogador;
   private ListView m_list;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_consultar_pontos_dia );

      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null )
      {
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null )
         {
            // vindo da activity main
            m_retornoIdGrupo = parametrosRecebidos.getInt( "idGrupo" );
         }
      }

      ArrayList<Pontos> pd = new ArrayList<>( m_db.selectPointsDay( m_retornoIdGrupo ) );

      ArrayList<Jogador> ljog = new ArrayList<>( m_db.selectJogadoresGrupo( m_retornoIdGrupo ) );

      m_listJogador = new ArrayList<>();

      for ( Jogador jogador2 : ljog )
      {
         int point = 0;

         for ( Pontos pontos : pd )
         {
            if ( ( pontos.getIdJogador1() == jogador2.getIdJogador() )
                     || pontos.getIdJogador2() == jogador2.getIdJogador() )
            {
               point = point + pontos.getQtdPoint();
            }
         }

         jogador2.setPointJogador( point );
         m_listJogador.add( jogador2 );
      }

      Collections.sort(m_listJogador);

      m_grupoAdapter = new ArrayAdapter<Jogador>( this,
               android.R.layout.simple_list_item_1, m_listJogador);

      m_list = ( ListView )findViewById( R.id.lvJogadores );
      m_list.setAdapter( m_grupoAdapter );
      m_list.setOnItemClickListener( new OnItemClickListener()
      {

         public void onItemClick( AdapterView<?> arg0, View arg1, int arg2,
                  long arg3 )
         {
         }

      } );
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
