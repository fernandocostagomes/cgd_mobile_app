package br.com.fgomes.cgd.activities;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Parametro;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListPartidasMes;

public class PartidasMesActivity extends Activity
{
   private static final String TAG = "PartidasMesActivity";
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

               viewHolder.textViewId = p_convertView.findViewById( R.id.tvId );
               viewHolder.textViewDate = p_convertView.findViewById( R.id.tvTitleDate );
               viewHolder.textViewV1 = p_convertView.findViewById( R.id.tvTitleV1 );
               viewHolder.textViewV2 = p_convertView.findViewById( R.id.tvTitleV2 );
               viewHolder.textViewP1 = p_convertView.findViewById( R.id.tvTitleP1 );
               viewHolder.textViewP2 = p_convertView.findViewById( R.id.tvTitleP2 );
               viewHolder.textViewPoints = p_convertView.findViewById( R.id.tvTitleQtPt );
               viewHolder.buttonDel = p_convertView.findViewById( R.id.btDelPointPart );

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

            //Abri a partida em um dialog para editar.
            viewHolder.textViewId.setOnClickListener( p_v -> {
               editPoint( itemL );

            });

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

   private void editarPartida( int pIdPartida,
                               int pWon1,
                               int pWon2,
                               int pLose1,
                               int pLose2,
                               int pValuePartida ){
      int id = pIdPartida;
      int idWon1 = pWon1;
      int idWon2 = pWon2;
      int idLose1 = pLose1;
      int idLose2 = pLose2;
      int valuePartida = pValuePartida;

//      if ( m_db.updatePoint( id, idWon1, idWon2, idLose1, idLose2, pValuePartida ) ){
//         Toast.makeText( getApplicationContext(), "Partida editada.", Toast.LENGTH_SHORT ).show();
//         dialog.dismiss();
//         backToInit();;
//      }
   }

   private void backToInit(){
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", m_idGrupo );
      startActivity( it );
      finish();
   }

   private void editPoint( ItensListPartidasMes itemL )
   {
      final Dialog dialog = new Dialog( this );
      dialog.setContentView( R.layout.activity_editar_partida );
      dialog.setTitle( "Editar partida" );

      TextView tvId = dialog.findViewById( R.id.tv_id_partida );
      TextView tvData = dialog.findViewById( R.id.tv_data_partida );
      //Coluna de vencedores
      CheckBox cbWon1 = dialog.findViewById( R.id.cb_won1_edit );
      CheckBox cbWon2 = dialog.findViewById( R.id.cb_won2_edit );
      CheckBox cbWon3 = dialog.findViewById( R.id.cb_won3_edit );
      CheckBox cbWon4 = dialog.findViewById( R.id.cb_won4_edit );

      //Coluna de perdedores
      CheckBox cbLose1 = dialog.findViewById( R.id.cb_lose1_edit );
      CheckBox cbLose2 = dialog.findViewById( R.id.cb_lose2_edit );
      CheckBox cbLose3 = dialog.findViewById( R.id.cb_lose3_edit );
      CheckBox cbLose4 = dialog.findViewById( R.id.cb_lose4_edit );

      RadioButton rbPartGato =
              dialog.findViewById( R.id.rb_edit_partida_gato );
      RadioButton rbPartPts2 =
              dialog.findViewById( R.id.rb_edit_partida_pts_2 );
      RadioButton rbPartPts1 =
              dialog.findViewById( R.id.rb_edit_partida_pts_1 );

      Button btEdit = dialog.findViewById( R.id.bt_edit_partida );

      String v1 = m_db.selectNameJogador( Integer.parseInt( itemL.get_v1() ) );
      String v2 = m_db.selectNameJogador( Integer.parseInt( itemL.get_v2() ) );
      String p1 = m_db.selectNameJogador( Integer.parseInt( itemL.get_p1() ) );
      String p2 = m_db.selectNameJogador( Integer.parseInt( itemL.get_p2() ) );

      //Textview com o id da partida.
      tvId.setText( itemL.get_id() );

      //Textview com a data da partida.
      tvData.setText( android.text.format.DateFormat.format( "dd/MM",
              Long.parseLong( itemL.get_date() ) ) );

      //CheckBox dos jogadores, pega o valor do nome do hash criado acima.
      cbWon1.setId(Integer.parseInt(itemL.get_v1()));
      cbWon1.setText( v1 );
      cbWon1.setChecked( true );

      cbWon2.setId(Integer.parseInt(itemL.get_v2()));
      cbWon2.setText( v2 );
      cbWon2.setChecked( true );

      cbWon3.setId(Integer.parseInt(itemL.get_p1()));
      cbWon3.setText( p1 );
      cbWon3.setChecked( false );

      cbWon4.setId(Integer.parseInt(itemL.get_p2()));
      cbWon4.setText( p2 );
      cbWon4.setChecked( false );

      cbLose1.setId(Integer.parseInt(itemL.get_p1()));
      cbLose1.setText( p1 );
      cbLose1.setChecked(true);

      cbLose2.setId(Integer.parseInt(itemL.get_p2()));
      cbLose2.setText( p2 );
      cbLose2.setChecked(true);

      cbLose3.setId(Integer.parseInt(itemL.get_v1()));
      cbLose3.setText( v1 );
      cbLose3.setChecked(false);

      cbLose4.setId(Integer.parseInt(itemL.get_v2()));
      cbLose4.setText( v2 );
      cbLose4.setChecked(false);

      switch (itemL.get_points()) {
         case "3" -> {
            rbPartGato.setChecked(true);
            rbPartPts1.setChecked(false);
            rbPartPts2.setChecked(false);
         }
         case "2" -> {
            rbPartPts2.setChecked(true);
            rbPartGato.setChecked(false);
            rbPartPts1.setChecked(false);
         }
         case "1" -> {
            rbPartPts1.setChecked(true);
            rbPartPts2.setChecked(false);
            rbPartGato.setChecked(false);
         }
      }

      btEdit.setOnClickListener( p_v -> {
//         editarPartida(
//                 itemL.get_id(),
//                 m_db.selectJogadorByName( , m_idGrupo ),
//                 cbWon2,
//                 cbLose1,
//                 cbLose2,
//                 rbPartGato,
//                 rbPartPts2,
//                 rbPartPts1,
//                 dialog );

      });

      dialog.show();
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
