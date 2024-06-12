package br.com.fgomes.cgd.activities;

import java.util.List;

import android.app.*;
import android.content.*;
import android.os.Bundle;
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

public class PartidasMesActivity extends Activity{
   private static final String ASC = "Asc";
   private static final String DESC = "Desc";
   private final DbHelper mDbHelper = new DbHelper( this );
   private List<ItensListPartidasMes> m_list_partidas;
   private ListView m_list;
   private int mRetornoIdGrupo, mIdGrupo;
   private String mOrderBy = ASC;

   /**
    * Metodo que carrega a lista com os pontos.
    */
   private void loadListPoints(){
     m_list_partidas = mDbHelper.selectItensPartidasMes(mIdGrupo, mOrderBy);
   }

   /**
    * Metodo que faz o preenchimento do ListView com os pontos.
    */
   private void loadListView(){
      ArrayAdapter<ItensListPartidasMes> adapter = new ArrayAdapter<ItensListPartidasMes>( this,
         R.layout.activity_itens_partidas_mes, m_list_partidas )
      {
         @Override
         public View getView( int p_position, View p_convertView, ViewGroup p_parent ){
            // Use view holder patern to better performance with list view.
            ViewHolderItem viewHolder = null;

            if ( p_convertView == null ){
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
               viewHolder = ( ViewHolderItem )p_convertView.getTag();

            final ItensListPartidasMes itemL = m_list_partidas.get( p_position );

            Jogador jog = new Jogador();
            // Id
            viewHolder.textViewId.setText( itemL.get_id() );
            // Data

            viewHolder.textViewDate
               .setText( android.text.format.DateFormat.format(
                       "dd/MM", Long.parseLong( itemL.get_date() ) ) );
            // TextView V1
            viewHolder.textViewV1.setText( mDbHelper.selectNameJogador(
                    Integer.parseInt( itemL.get_v1() ) ) );
            // TextView V2
            viewHolder.textViewV2.setText( mDbHelper.selectNameJogador(
                    Integer.parseInt( itemL.get_v2() ) ) );
            // TextView P1
            viewHolder.textViewP1.setText( mDbHelper.selectNameJogador(
                    Integer.parseInt( itemL.get_p1() ) ) );
            // TextView P2
            viewHolder.textViewP2.setText( mDbHelper.selectNameJogador(
                    Integer.parseInt( itemL.get_p2() ) ) );
            // Total pontos
            viewHolder.textViewPoints.setText( itemL.get_points() );

            // Evento de toque no button para excluir a partida.
            viewHolder.buttonDel.setOnClickListener(p_v -> {
               final AlertDialog.Builder dialogo = new AlertDialog.Builder(
                       PartidasMesActivity.this );
               dialogo.setTitle( "Apagar partida" );
               dialogo.setMessage( "Confirmar exclusão de partida" );
               dialogo.setPositiveButton( "Ok",(dialog, which) -> {
                          if ( mDbHelper.deletePoint( Integer.parseInt(
                                  itemL.get_id() ) ) ){
                             Toast.makeText( getApplicationContext(),
                                     "Ponto deletado.",
                                     Toast.LENGTH_SHORT ).show();
                             loadListPoints();
                             loadListView();
                          }
                       });
               dialogo.setNegativeButton( "Cancelar", (dialog, which) ->
                       dialog.dismiss());
               dialogo.setNeutralButton( "Editar",    (dialog, which) -> {
                  editPoint( Integer.parseInt( itemL.get_id() ) );
                  dialog.dismiss();
                       });
               dialogo.show();
            });

            return p_convertView;
         }

         static final class ViewHolderItem{
            TextView textViewId, textViewDate, textViewV1, textViewV2,
            textViewP1, textViewP2, textViewPoints;
            Button buttonDel;
         }
      };
      m_list.setAdapter( adapter );
   }

   private void editPoint( int pIdPoint ){
      final Dialog dialog = new Dialog( this );
      dialog.setContentView( R.layout.activity_editar_partida );
      dialog.setTitle( "Editar partida" );

      dialog.show();
   }

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );

      setContentView( R.layout.activity_partidas_mes );

      m_list = findViewById( R.id.lvPartidasMes );

      checkBundle();

      checkGroupId();

      loadListPoints();

      loadListView();
   }

   private void checkGroupId() {
      // ...Buscando o valor do idGrupo cadastrado no Banco / Parametros
      Parametro param = mDbHelper.selectUmParametro(1);
      int valorIdGrupo = param.getValorParametro();

      if ( mRetornoIdGrupo > 0 )
         mIdGrupo = mRetornoIdGrupo;
      else
         mIdGrupo = valorIdGrupo;
   }

   private void checkBundle() {
      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null ){
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null )
            mRetornoIdGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
      }
   }

   /**
    * Ação realizada ao clicar no botão VOLTAR do dispositivo.
    */
   @Override
   public void onBackPressed(){
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", mRetornoIdGrupo);
      startActivity( it );

      super.onBackPressed();
   }

   public void evtOrderByValue(View p_view){
       if (mOrderBy.equals(ASC))
           mOrderBy = DESC;
       else
           mOrderBy = ASC;

      loadListPoints();

      loadListView();
   }
}
