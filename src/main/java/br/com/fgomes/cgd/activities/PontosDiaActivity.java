package br.com.fgomes.cgd.activities;
import android.app.*;
import android.content.Intent;
import android.os.*;
import android.view.*;
import android.widget.*;

import java.text.*;
import java.util.*;

import br.com.fgomes.cgd.*;
import br.com.fgomes.cgd.objects.*;
import br.com.fgomes.cgd.utils.*;

/**
 * Created by fernando.gomes on 27/12/2017.
 */

public class PontosDiaActivity extends Activity
{
   private DbHelper m_db = new DbHelper( this );
   private int m_retornoIdGrupo, m_idGrupo, m_idParametro = 1;
   ArrayList<Jogador> m_listJogadores;
   ArrayList<Pontos> m_listPoints;
   private ListView m_list;
   private ArrayList<ItensListViewPointsDay> m_listItensPointsDay;
   private TextView m_tvPointsDayFrom, m_tvPointsDayTo;
   long m_millis;

   private void loadListStart(String p_type)
   {
      //Carregamento da lista de jogadores do grupo.
      m_listJogadores = new ArrayList<>( m_db.selectJogadoresGrupo( m_idGrupo ) );
      //Carregamento da lista de pontos.
      if(p_type.equals("periodo"))
      {
         m_listPoints = new ArrayList<>(m_db.selectPointsDayForData(m_idGrupo, m_tvPointsDayFrom.getText().toString(), m_tvPointsDayTo.getText().toString()));
      }
      else {
         m_listPoints = new ArrayList<>(m_db.selectPointsDay(m_idGrupo));
      }
      //Inicializacao da lista com os itens mostrados na tela.
      m_listItensPointsDay = new ArrayList<>();
      //Percorrendo a lista de jogadores.
      for (int i = 0; i < m_listJogadores.size(); i++ ) {
         //Value jogador
         int jogador = m_listJogadores.get(i).getIdJogador();
         //Variavel para somar os pontos.
         int ptTotal = 0;
         //Variavel para somar os gatos.
         int catTotal = 0;
         //Variavel para somar as derrotas.
         int losesTotal = 0;
         //Percorrendo a lista de pontos.
         for (int x = 0; x < m_listPoints.size(); x++) {
            //Value do jogador1.
            int jogador1 = m_listPoints.get(x).getIdJogador1();
            //Value do jogador 2.
            int jogador2 = m_listPoints.get(x).getIdJogador2();
            //Value gato1.
            int gato1 = m_listPoints.get(x).getIdGato1();
            //Value gato1.
            int gato2 = m_listPoints.get(x).getIdGato2();
            //Verificando se o jogador foi derrotado.
            if(jogador == gato1 || jogador == gato2)
            {
               losesTotal++;
               //Verificando se a partida foi gato.
               if(m_listPoints.get(x).getQtdPoint() == 3)
               {
                  catTotal++;
               }
            }
            //Verificando se o jogador ganhou ponto nesse ponto percorrido.
            if(jogador == jogador1 || jogador == jogador2)
            {
               int point = m_listPoints.get(x).getQtdPoint();
               ptTotal = ptTotal + point;
            }
         }
         ptTotal = ptTotal - catTotal;
         //Objeto de itens mostrados na tela.
         ItensListViewPointsDay ilv = new ItensListViewPointsDay();
         //nome do jogador
         ilv.setM_name(m_db.selectNameJogador(jogador));
         //pontos total
         ilv.setM_pontos(ptTotal);
         //Qt gatos
         ilv.setM_gatos(catTotal);
         //Qt derrotas
         ilv.setM_loses(losesTotal);
         m_listItensPointsDay.add(ilv);
      }
      Collections.sort(m_listItensPointsDay);
      loadListView();
   }

   private void loadListView()
   {
      ArrayAdapter<ItensListViewPointsDay> adapter = new ArrayAdapter<ItensListViewPointsDay>( this,
              R.layout.activity_itens_points_day, m_listItensPointsDay )
      {
         @Override
         public View getView( int p_position, View p_convertView, ViewGroup p_parent )
         {
            // Use view holder patern to better performance with list view.
            ViewHolderItem viewHolder = null;

            if ( p_convertView == null )
            {
               p_convertView = getLayoutInflater().inflate( R.layout.activity_itens_points_day, p_parent,
                       false );

               viewHolder = new ViewHolderItem();

               viewHolder.textViewName = ( TextView )p_convertView.findViewById( R.id.tvName );
               viewHolder.textViewLoses = ( TextView )p_convertView.findViewById( R.id.tvLoses );
               viewHolder.textViewPoints = ( TextView )p_convertView.findViewById( R.id.tvPoints );
               viewHolder.textViewCats = ( TextView )p_convertView.findViewById( R.id.tvCats );

               // store holder with view.
               p_convertView.setTag( viewHolder );
            }
            // get saved holder
            else
            {
               viewHolder = ( ViewHolderItem )p_convertView.getTag();
            }

            ItensListViewPointsDay itemL = m_listItensPointsDay.get( p_position );

            // Name
            viewHolder.textViewName.setText( itemL.m_name );
            // Pontos
            viewHolder.textViewPoints.setText( String.valueOf(itemL.m_pontos));
            // Gatos
            viewHolder.textViewCats.setText( String.valueOf(itemL.m_gatos) );
            // Derrotas
            viewHolder.textViewLoses.setText( String.valueOf(itemL.m_loses) );

            return p_convertView;
         }

         final class ViewHolderItem
         {
            TextView textViewName;
            TextView textViewLoses;
            TextView textViewPoints;
            TextView textViewCats;
         }
      };
      m_list.setAdapter( adapter );
   }

   private void checkDates()
   {
      long date = System.currentTimeMillis();
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      String dateString = sdf.format(date);
      m_tvPointsDayFrom.setText(dateString);
      m_tvPointsDayTo.setText(dateString);
   }

   private String modifyDate(String p_date, String p_type)
   {
      String result = "";
      try {
         SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
         Date data = format.parse(p_date);
         if(p_type.equals("dateTo"))
         {
            result = String.valueOf(data.getTime() + 86400000);
         }
         else
         {
            result = String.valueOf(data.getTime());
         }
      }
      catch (Exception p_e)
      {
         p_e.printStackTrace();
      }
      return result;
   }

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_pontos_dia );
      m_tvPointsDayFrom = (TextView) findViewById(R.id.tvPointsDayFrom);
      m_tvPointsDayTo  = (TextView) findViewById(R.id.tvPointsDayTo);

      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null )
      {
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null )
         {
            // vindo da activity main
            m_retornoIdGrupo = parametrosRecebidos.getInt( "idGrupo" );
            m_idGrupo = m_retornoIdGrupo;
         }
      }
      m_list = ( ListView )findViewById( R.id.lvJogadores );
      checkDates();
      loadListStart("");
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

   public void evtBtPointsDayStart(View p_view)
   {
      showPicker(m_tvPointsDayFrom);
   }

   public void evtBtPointsDayEnd(View p_view)
   {
      showPicker(m_tvPointsDayTo);
   }

   public void evtBtPointsDaygo(View p_view)
   {
      try
      {
         loadListStart("periodo");
      }
      catch (Exception p_e)
      {
         p_e.printStackTrace();
      }
   }

   public void showPicker(final TextView p_button) {

      // Get Current Date
      final Calendar c = Calendar.getInstance();
      int mYear = c.get(Calendar.YEAR);
      int mMonth = c.get(Calendar.MONTH);
      int mDay = c.get(Calendar.DAY_OF_MONTH);

      DatePickerDialog datePickerDialog = new DatePickerDialog(this,
              new DatePickerDialog.OnDateSetListener() {
                 @Override
                 public void onDateSet(DatePicker view, int year,
                                       int monthOfYear, int dayOfMonth)
                 {
                    String mes = String.valueOf(monthOfYear + 1);
                    String dia = String.valueOf(dayOfMonth);
                    if(monthOfYear <= 9)
                    {
                        mes = "0" + mes;
                    }
                    if(dayOfMonth <= 9 )
                    {
                       dia = "0" + dia;
                    }
                    p_button.setText(year + "-" + mes + "-" + dia);


                 }
              }, mYear, mMonth, mDay);
      datePickerDialog.show();
   }

}


