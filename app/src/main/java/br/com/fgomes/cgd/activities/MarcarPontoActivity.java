package br.com.fgomes.cgd.activities;

import java.util.*;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.utils.DbHelper;

public class MarcarPontoActivity extends Activity
{
   private ArrayList<Integer> m_listJogador = new ArrayList<Integer>();
   private int m_retornoIdGrupo, m_retornoIdJogador;
   private int m_idGrupo;
   private DbHelper m_db = new DbHelper( this );
   private LinearLayout m_ll_won;
   private LinearLayout m_ll_lose;
   private CheckBox m_checkBoxWon;
   private CheckBox m_checkBoxLose;
   private RadioButton m_rb0;
   private RadioButton m_rb1;
   private ArrayList<Jogador> m_listJogWon;
   private List<Jogador> m_listJogLose;
   private ArrayList<Integer> m_listAddJogWon;
   private ArrayList<Integer> m_listAddJogLose;

   View.OnClickListener getOnClickDoSomethingWon( final Button button )
   {
      return new View.OnClickListener()
      {
         public void onClick( View v )
         {
            if(((CheckBox) v).isChecked())
            {
               switch (m_listAddJogWon.size())
               {
                  case 0:
                     m_listAddJogWon.add(button.getId());
                     //Identifica e remove o nome da lista de perdedores
                     Jogador jog1 = getJog(button.getText().toString());
                     m_listJogLose.remove(jog1);
                     mountListLose();
                     break;
                  case 1:
                     m_listAddJogWon.add(button.getId());
                     //Identifica e remove o nome da lista de perdedores
                     Jogador jog2 = getJog(button.getText().toString());
                     m_listJogLose.remove(jog2);
                     mountListLose();
                     break;
                  case 2:
                     Toast.makeText(getApplicationContext(), "É possível marcar apenas dois jogadores!", Toast.LENGTH_SHORT).show();
                     //TODO uncheck o item.
                     ((CheckBox) v).setChecked(false);
                     break;
               }
            }
            else
            {
               if(m_listAddJogWon.size() >= 1)
               {
                  //Adicionar novamente na lista de lose, buscando do banco.
                  Jogador jog = m_db.selectUmJogador(button.getId());
                  m_listJogLose.add(jog);
                  //Remover da lista de addJog.
                  for (int i = 0 ; i < m_listAddJogWon.size(); i++)
                  {
                     Integer value = m_listAddJogWon.get(i);
                     if( value == jog.getIdJogador())
                     {
                        m_listAddJogWon.remove(i);
                     }
                  }
                  mountListLose();
               }
            }
         }
      };
   }

   View.OnClickListener getOnClickDoSomethingLose( final Button button )
   {
      return new View.OnClickListener()
      {
         public void onClick( View v ) {
            if (((CheckBox) v).isChecked())
            {
               if (m_listAddJogWon.size() < 2)
               {
                  Toast.makeText(getApplicationContext(), "Primeiro marcar os ganhadores!", Toast.LENGTH_SHORT).show();
                  //TODO uncheck o item.
                  ((CheckBox) v).setChecked(false);
               } else
                  {
                  switch (m_listAddJogLose.size())
                  {
                     case 0:
                        //Identifica e insere na lista de perdedores
                        Jogador jog = m_db.selectUmJogador(button.getId());
                        m_listAddJogLose.add(jog.getIdJogador());
                        break;
                     case 1:
                        //Identifica e insere na lista de perdedores
                        Jogador jog1 = m_db.selectUmJogador(button.getId());
                        m_listAddJogLose.add(jog1.getIdJogador());
                        break;
                     case 2:
                        Toast.makeText(getApplicationContext(), "É possível marcar apenas dois jogadores!", Toast.LENGTH_SHORT).show();
                        //TODO uncheck o item.
                        ((CheckBox) v).setChecked(false);
                        break;
                  }
               }
            }
            else
               {
                  if (m_listAddJogLose.size() >= 1) {
                     //Adicionar novamente na lista de lose, buscando do banco.
                     Jogador jog = m_db.selectUmJogador(button.getId());
                     //Remover da lista de addJog.
                     for (int i = 0; i < m_listAddJogLose.size(); i++) {
                        Integer value = m_listAddJogLose.get(i);
                        if (value == jog.getIdJogador()) {
                           m_listAddJogLose.remove(i);
                        }
                     }
                  }
               }
            }

      };
   }


   private void mountListWon()
   {
      //Lista de ganhadores
      m_listJogWon = new ArrayList<Jogador>( m_db.selectJogadoresGrupo( m_idGrupo ) );
      LinkedHashMap<String, String> alphabet = new LinkedHashMap<String, String>();
      for ( Jogador jogador2 : m_listJogWon )
      {
         if ( jogador2.getIdJogador() != m_retornoIdJogador )
         {
            alphabet.put( String.valueOf( jogador2.getIdJogador() ), jogador2.getNomeJogador() );
         }
      }

      Set<?> set = alphabet.entrySet();
      // Get an iterator
      Iterator<?> i = set.iterator();
      // Display elements
      while ( i.hasNext() )
      {
         @SuppressWarnings( "rawtypes" )
         Map.Entry me = ( Map.Entry )i.next();
         System.out.print( me.getKey() + ": " );
         System.out.println( me.getValue() );

         m_checkBoxWon = new CheckBox( this );
         m_checkBoxWon.setId( Integer.parseInt( me.getKey().toString() ) );
         m_checkBoxWon.setText( me.getValue().toString() );
         m_checkBoxWon.setOnClickListener( getOnClickDoSomethingWon( m_checkBoxWon ) );
         m_ll_won.addView( m_checkBoxWon );
      }
   }

   private void mountListLose()
   {
      m_ll_lose.removeAllViews();
      LinkedHashMap<String, String> alpha = new LinkedHashMap<String, String>();
      for ( Jogador jogador2 : m_listJogLose )
      {
         if ( jogador2.getIdJogador() != m_retornoIdJogador )
         {
            alpha.put( String.valueOf( jogador2.getIdJogador() ), jogador2.getNomeJogador() );
         }
      }

      Set<?> set1 = alpha.entrySet();
      // Get an iterator
      Iterator<?> i2 = set1.iterator();
      // Display elements
      while ( i2.hasNext() )
      {
         @SuppressWarnings( "rawtypes" )
         Map.Entry me = ( Map.Entry )i2.next();
         System.out.print( me.getKey() + ": " );
         System.out.println( me.getValue() );

         m_checkBoxLose = new CheckBox( this );
         m_checkBoxLose.setId( Integer.parseInt( me.getKey().toString() ) );
         m_checkBoxLose.setText( me.getValue().toString() );
         m_checkBoxLose.setOnClickListener(getOnClickDoSomethingLose(m_checkBoxLose));
         m_ll_lose.addView( m_checkBoxLose );
      }
   }

   private Jogador getJog(String p_name)
   {
      Jogador jogador = new Jogador();

      for (Jogador jog : m_listJogLose)
      {
         if(jog.getNomeJogador().equals(p_name))
         {
            jogador = jog;
         }
      }
      return  jogador;
   }

   private int getValueGame()
   {
      int result = 0;
      if ( m_rb0.isChecked() )
         result = 3;
      else
      if ( m_rb1.isChecked() )
         result = 2;
      else
         result = 1;
      return result;
   }

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_marcar_ponto);

      m_rb0 = ( RadioButton )findViewById( R.id.radio0 );
      m_rb1 = ( RadioButton )findViewById( R.id.radio1 );

      Intent iDadosRecebidos = getIntent();
      if ( iDadosRecebidos != null )
      {
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null )
         {
            m_retornoIdGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
            m_retornoIdJogador = parametrosRecebidos.getInt( "envioIdJogador" );
         }
      }
      // ...Buscando o valor do idGrupo cadastrado no Banco / Parametros
      if ( m_retornoIdGrupo > 0 )
         m_idGrupo = m_retornoIdGrupo;

      m_ll_won = ( LinearLayout )findViewById( R.id.ll_won );
      m_ll_lose = ( LinearLayout )findViewById( R.id.ll_lose );

      mountListWon();
      //Lista de perdedores
      m_listJogLose = new ArrayList<Jogador>( m_listJogWon );
      mountListLose();

      m_listAddJogWon = new ArrayList<>();
      m_listAddJogLose = new ArrayList<>();
   }

   @Override
   public void onBackPressed()
   {
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
      startActivity( it );

      super.onBackPressed();
   }

   public void doMarkPoint( View p_view )
   {
      if(m_listAddJogWon.size() <2)
      {
         Toast.makeText(getApplicationContext(), "O número de vencedores é menor que o permitido.", Toast.LENGTH_SHORT).show();
      }
      else
      {
         if(m_listAddJogLose.size() <2)
         {
            Toast.makeText(getApplicationContext(), "O número de perdedores é menor que o permitido.", Toast.LENGTH_SHORT).show();
         }
         else
         {
            try {
               m_db.insertPoint(m_listAddJogWon.get(0), m_listAddJogWon.get(1), getValueGame(), m_listAddJogLose.get(0), m_listAddJogLose.get(1),
                       m_retornoIdGrupo);
               Toast.makeText(getApplicationContext(), "Ponto marcado!", Toast.LENGTH_SHORT).show();
               Intent it = new Intent( this, GrupoInicioActivity.class );
               it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
               startActivity( it );
               finish();
            }
            catch (Exception p_e)
            {
               p_e.printStackTrace();
            }
         }
      }
   }
}
