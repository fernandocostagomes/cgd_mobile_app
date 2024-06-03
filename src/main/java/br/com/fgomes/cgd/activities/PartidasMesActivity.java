package br.com.fgomes.cgd.activities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Parametro;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListPartidasMes;
import br.com.fgomes.cgd.views.CheckBoxCGD;

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
   private CheckBoxCGD mCheckBoxCGD;

   private View.OnClickListener click(){
      return v ->{
         CheckBox checkboxSel = (CheckBox) v;
         LinearLayout linearLayout = (LinearLayout) checkboxSel.getParent();
         if( linearLayout.getId() == R.id.ll_won ){
            if(mCheckBoxCGD.checkboxQtCheckBoxIsChecked(findViewById(R.id.ll_won)) > 2)
               checkboxSel.setChecked(false);
            else{
               for(CheckBox checkBox : mCheckBoxCGD.checkBoxGenerateList(findViewById(R.id.ll_lose))){
                  if(checkBox.getId() == checkboxSel.getId()){
                     checkBox.setVisibility(checkboxSel.isChecked() ? View.GONE : View.VISIBLE);
                     checkBox.setChecked(false);
                  }
               }
            }
         }
         else if (linearLayout.getId() == R.id.ll_lose ){
            if(mCheckBoxCGD.checkboxQtCheckBoxIsChecked(findViewById(R.id.ll_lose)) > 2)
               checkboxSel.setChecked(false);
            else{
               for(CheckBox checkBox : mCheckBoxCGD.checkBoxGenerateList(findViewById(R.id.ll_won))){
                  if(checkBox.getId() == checkboxSel.getId()){
                     checkBox.setVisibility(checkboxSel.isChecked() ? View.GONE : View.VISIBLE);
                     checkBox.setChecked(false);
                  }
               }
            }
         }
      };
   }

   private void clickMarcarPonto(int pPontos) {
      Button button = findViewById(R.id.marcarPonto);
      button.setOnClickListener(v -> {
         try {
            DbHelper.getInstance(getApplicationContext()).insertPoint(
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_won)).get(0),
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_won)).get(1),
                    pPontos,
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_lose)).get(0),
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_lose)).get(1),
                    m_idGrupo);
            Toast.makeText(getApplicationContext(), "Ponto marcado!", Toast.LENGTH_SHORT).show();
            backToInit();
         }
         catch (Exception p_e){
            p_e.printStackTrace();
         }
      });
   }

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
            viewHolder.buttonDel.setOnClickListener(p_v -> {
               final AlertDialog.Builder dialogo = new AlertDialog.Builder(
                       PartidasMesActivity.this );
               dialogo.setTitle( "Apagar partida" );
               dialogo.setMessage( "Confirmar exclusão de partida" );
               dialogo.setPositiveButton( "Ok",(dialog, which) -> {
                          if ( m_db.deletePoint( Integer.parseInt( itemL.get_id() ) ) ){
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

   private void montarLista( ){
      List<LinearLayout> linearLayoutList = new ArrayList<>();
      linearLayoutList.add( findViewById( R.id.ll_won ) );
      linearLayoutList.add( findViewById( R.id.ll_lose ) );
      for(LinearLayout linearLayout : linearLayoutList) {
         linearLayout.removeAllViews();
         LinkedHashMap<String, String> alphabet = new LinkedHashMap<>();
         linearLayout.removeAllViews();
         Set<?> set = alphabet.entrySet();
         Iterator<?> i = set.iterator();
         while (i.hasNext()) {
            Map.Entry me = (Map.Entry) i.next();
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(Integer.parseInt(me.getKey().toString()));
            checkBox.setText(me.getValue().toString());
            checkBox.setOnClickListener(click());
            linearLayout.addView(checkBox);
         }
      }
   }

   private void editPoint( int pIdPoint )
   {
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
