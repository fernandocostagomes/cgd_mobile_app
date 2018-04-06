package br.com.fgomes.cgd.activities;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.DebitoCredito;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.utils.DbHelper;

public class ConsultarCreditoDebitoActivity extends Activity
{
   private int m_retornoIdGrupo, m_retornoIdJogador;
   private ArrayList<DebitoCredito> m_listDebitoCredito;
   private TextView m_jogador_principal;
   private TextView m_adversario1, m_adversario2, m_adversario3, m_adversario4, m_adversario5, m_adversario6,
            m_adversario7, m_adversario8;
   private TextView m_credito1, m_credito2, m_credito3, m_credito4, m_credito5, m_credito6, m_credito7, m_credito8;
   private TextView m_debito1, m_debito2, m_debito3, m_debito4, m_debito5, m_debito6, m_debito7, m_debito8;
   private TextView m_saldo1, m_saldo2, m_saldo3, m_saldo4, m_saldo5, m_saldo6, m_saldo7, m_saldo8;

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_consultar_credito_debito );

      int credito = 0, debito = 0, saldo = 0;

      m_adversario1 = ( TextView )findViewById( R.id.tvPlayer0 );
      m_adversario2 = ( TextView )findViewById( R.id.tvPlayer1 );
      m_adversario3 = ( TextView )findViewById( R.id.tvPlayer2 );
      m_adversario4 = ( TextView )findViewById( R.id.tvPlayer3 );
      m_adversario5 = ( TextView )findViewById( R.id.tvPlayer4 );
      m_adversario6 = ( TextView )findViewById( R.id.tvPlayer5 );
      m_adversario7 = ( TextView )findViewById( R.id.tvPlayer6 );
      m_adversario8 = ( TextView )findViewById( R.id.tvPlayer7 );

      m_credito1 = ( TextView )findViewById( R.id.tvCredit0 );
      m_credito2 = ( TextView )findViewById( R.id.tvCredit1 );
      m_credito3 = ( TextView )findViewById( R.id.tvCredit2 );
      m_credito4 = ( TextView )findViewById( R.id.tvCredit3 );
      m_credito5 = ( TextView )findViewById( R.id.tvCredit4 );
      m_credito6 = ( TextView )findViewById( R.id.tvCredit5 );
      m_credito7 = ( TextView )findViewById( R.id.tvCredit6 );
      m_credito8 = ( TextView )findViewById( R.id.tvCredit7 );

      m_debito1 = ( TextView )findViewById( R.id.tvDebit0 );
      m_debito2 = ( TextView )findViewById( R.id.tvDebit1 );
      m_debito3 = ( TextView )findViewById( R.id.tvDebit2 );
      m_debito4 = ( TextView )findViewById( R.id.tvDebit3 );
      m_debito5 = ( TextView )findViewById( R.id.tvDebit4 );
      m_debito6 = ( TextView )findViewById( R.id.tvDebit5 );
      m_debito7 = ( TextView )findViewById( R.id.tvDebit6 );
      m_debito8 = ( TextView )findViewById( R.id.tvDebit7 );

      m_saldo1 = ( TextView )findViewById( R.id.tvBalance0 );
      m_saldo2 = ( TextView )findViewById( R.id.tvBalance1 );
      m_saldo3 = ( TextView )findViewById( R.id.tvBalance2 );
      m_saldo4 = ( TextView )findViewById( R.id.tvBalance3 );
      m_saldo5 = ( TextView )findViewById( R.id.tvBalance4 );
      m_saldo6 = ( TextView )findViewById( R.id.tvBalance5 );
      m_saldo7 = ( TextView )findViewById( R.id.tvBalance6 );
      m_saldo8 = ( TextView )findViewById( R.id.tvBalance7 );

      m_jogador_principal = ( TextView )findViewById( R.id.tvPlayer );

      m_listDebitoCredito = new ArrayList<DebitoCredito>();

      DbHelper db = new DbHelper( this );

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

      ArrayList<Jogador> jogador = new ArrayList<Jogador>( db.selectJogadoresGrupo( m_retornoIdGrupo ) );

      for ( Jogador jogador2 : jogador )
      {
         if ( jogador2.getIdJogador() != m_retornoIdJogador )
         {
            DebitoCredito dc = new DebitoCredito();

            dc.setIdJogador( jogador2.getIdJogador() );
            dc.setNomeJogador( jogador2.getNomeJogador() );
            dc.setDebito( debito );
            m_listDebitoCredito.add( dc );
            saldo = dc.getCredito() - dc.getDebito();
            dc.setSaldo( saldo );
         }
         else
         {
            m_jogador_principal.setText( jogador2.getNomeJogador().toUpperCase() );
         }
      }

      int i = 0;

      for ( DebitoCredito debitoCredito1 : m_listDebitoCredito )
      {
         switch ( i )
         {
            case 0:
               m_adversario1.setText( debitoCredito1.getNomeJogador() );
               m_credito1.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito1.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo1.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;
            case 1:
               m_adversario2.setText( debitoCredito1.getNomeJogador() );
               m_credito2.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito2.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo2.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;
            case 2:
               m_adversario3.setText( debitoCredito1.getNomeJogador() );
               m_credito3.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito3.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo3.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;
            case 3:
               m_adversario4.setText( debitoCredito1.getNomeJogador() );
               m_credito4.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito4.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo4.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;
            case 4:
               m_adversario5.setText( debitoCredito1.getNomeJogador() );
               m_credito5.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito5.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo5.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;

            case 5:
               m_adversario6.setText( debitoCredito1.getNomeJogador() );
               m_credito6.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito6.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo6.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;

            case 6:
               m_adversario7.setText( debitoCredito1.getNomeJogador() );
               m_credito7.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito7.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo7.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;

            case 7:
               m_adversario8.setText( debitoCredito1.getNomeJogador() );
               m_credito8.setText( String.valueOf( debitoCredito1.getCredito() ) );
               m_debito8.setText( String.valueOf( debitoCredito1.getDebito() ) );
               m_saldo8.setText( String.valueOf( debitoCredito1.getSaldo() ) );
            break;
            default:
            break;
         }

         i++;
      }
   }

   @Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate( R.menu.consultar_credito_debito, menu );
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
