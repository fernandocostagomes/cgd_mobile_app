package br.com.fgomes.cgd.activities;

import java.util.List;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Parametro;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListPartidasMes;

public class PartidasMesActivity extends Activity
{
   private DbHelper m_db = new DbHelper( this );
   /** Lista de objetos para criar o listView.*/
   private List<ItensListPartidasMes> m_list_partidas;
   private ListView m_list;
   private int m_retornoIdGrupo;
   private int m_idGrupo;
   private int m_idParametro = 1;

   /**
    * Metodo que carrega a lista com os pontos.
    */
   private void loadListPoints(){
     m_list_partidas = m_db.selectItensPartidasMes();
   }

   /**
    * Metodo que faz o preenchimento do ListView com os pontos.
    */
   private void loadListView()
   {
      ArrayAdapter<ItensListPartidasMes> adapter = new ArrayAdapter<ItensListPartidasMes>( this,
         R.layout.activity_itens_partidas_mes, m_list_partidas )
      {
         @Override
         public View getView( int p_position, View p_convertView, ViewGroup p_parent )
         {
            // Use view holder patern to better performance with list view.
            ViewHolderItem viewHolder = null;

            if ( p_convertView == null )
            {
               p_convertView = getLayoutInflater().inflate( R.layout.activity_itens_partidas_mes, p_parent,
                  false );

               viewHolder = new ViewHolderItem();

               viewHolder.textViewId = ( TextView )p_convertView.findViewById( R.id.tvId );
               viewHolder.textViewDate = ( TextView )p_convertView.findViewById( R.id.tvTitleDate );
               viewHolder.textViewV1 = ( TextView )p_convertView.findViewById( R.id.tvTitleV1 );
               viewHolder.textViewV2 = ( TextView )p_convertView.findViewById( R.id.tvTitleV2 );
               viewHolder.textViewP1 = ( TextView )p_convertView.findViewById( R.id.tvTitleP1 );
               viewHolder.textViewP2 = ( TextView )p_convertView.findViewById( R.id.tvTitleP2 );
               viewHolder.textViewPoints = ( TextView )p_convertView.findViewById( R.id.tvTitleQtPt );
               viewHolder.buttonDel = ( Button )p_convertView.findViewById( R.id.btDelPointPart );

               // store holder with view.
               p_convertView.setTag( viewHolder );
            }
            // get saved holder
            else
            {
               viewHolder = ( ViewHolderItem )p_convertView.getTag();
            }

            final ItensListPartidasMes itemL = m_list_partidas.get( p_position );

            Jogador jog = new Jogador();
            // Id
            viewHolder.textViewId.setText( itemL.get_id() );
            // Data

            viewHolder.textViewDate
               .setText( android.text.format.DateFormat.format( "dd/MM",
                       Long.parseLong( itemL.get_date() ) ) );
            // TextView V1
            viewHolder.textViewV1.setText( m_db.selectNameJogador( Integer.parseInt( itemL.get_v1() ) ) );
            // TextView V2
            viewHolder.textViewV2.setText( m_db.selectNameJogador( Integer.parseInt( itemL.get_v2() ) ) );
            // TextView P1
            viewHolder.textViewP1.setText( m_db.selectNameJogador( Integer.parseInt( itemL.get_p1() ) ) );
            // TextView P2
            viewHolder.textViewP2.setText( m_db.selectNameJogador( Integer.parseInt( itemL.get_p2() ) ) );
            // Total pontos
            viewHolder.textViewPoints.setText( itemL.get_points() );
            // Evento de toque no button para excluir a partida.
            viewHolder.buttonDel.setOnClickListener( new View.OnClickListener()
            {
               @Override
               public void onClick( View p_v )
               {
                  final AlertDialog.Builder dialogo = new AlertDialog.Builder(
                          PartidasMesActivity.this );
                  dialogo.setTitle( "Apagar partida" );
                  dialogo.setMessage( "Confirmar exclusão de partida" );
                  dialogo.setPositiveButton( "Ok",
                          new DialogInterface.OnClickListener()
                          {
                             @Override
                             public void onClick( DialogInterface dialog,
                                                  int which )
                             {
                                if ( m_db.deletePoint( Integer.parseInt( itemL.get_id() ) ) )
                                {
                                   Toast.makeText( getApplicationContext(), "Ponto deletado.", Toast.LENGTH_SHORT ).show();
                                   loadListPoints();
                                   loadListView();
                                }
                             }
                          } );
                  dialogo.setNegativeButton( "Cancelar", new DialogInterface.OnClickListener()
                  {
                     @Override
                     public void onClick( DialogInterface dialog, int which )
                     {
                        dialog.dismiss();
                     }
                  } );
                  dialogo.show();
               }
            } );

            return p_convertView;
         }

         final class ViewHolderItem
         {
            TextView textViewId;
            TextView textViewDate;
            TextView textViewV1;
            TextView textViewV2;
            TextView textViewP1;
            TextView textViewP2;
            TextView textViewPoints;
            Button buttonDel;
         }
      };
      m_list.setAdapter( adapter );

   }

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_partidas_mes );
      m_list = ( ListView )findViewById( R.id.lvPartidasMes );

      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null )
      {
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null )
         {
            // vindo da activity main
            m_retornoIdGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
         }
      }

      // ...Buscando o valor do idGrupo cadastrado no Banco / Parametros
      Parametro param = m_db.selectUmParametro( m_idParametro );
      int valorIdGrupo = param.getValorParametro();

      if ( m_retornoIdGrupo > 0 )
         m_idGrupo = m_retornoIdGrupo;
      else
         m_idGrupo = valorIdGrupo;

      loadListPoints();
      loadListView();
   }

   /**
    * Ação realizada ao clicar no botão VOLTAR do dispositivo.
    */
   @Override
   public void onBackPressed()
   {
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
      startActivity( it );

      super.onBackPressed();
   }
}
