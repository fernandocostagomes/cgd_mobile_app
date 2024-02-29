package br.com.fgomes.cgd.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Pontos;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListPartidasMes;
import br.com.fgomes.cgd.utils.ItensListViewInicio;
import br.com.fgomes.cgd.utils.ProcessExportManager;

public class GrupoInicioActivity extends Activity implements OnItemClickListener{
   private static final String TAG = "GrupoInicioActivity";
   private int m_retornoIdGrupo, m_idGrupo, m_idParametro = 1;
   private ListView m_list, m_listPartidasToday;
   private ArrayList<Jogador> m_listJogadores;
   private ArrayList<Pontos> m_listPoints;
   private ArrayList<ItensListViewInicio> m_listItensInicio;
   private List<ItensListPartidasMes> m_list_partidas;
   private String m_month = "";
   private String m_year = "";
   private String m_dateStart = "";
   private String m_dateEnd = "";
   private String m_today = "";

   private ArrayList<ItensListViewInicio> getListForDate(String pDateStart,
                                                         String pDateEnd){
      ArrayList<ItensListViewInicio> listItensInicio = new ArrayList<>();
      DbHelper dbHelper = DbHelper.getInstance( this );

      //Carregamento da lista de jogadores do grupo.
      m_listJogadores = new ArrayList<>( dbHelper.selectJogadoresGrupo( m_idGrupo ) );

      //Carregamento da lista de pontos de acordo com o mes corrente na data atual.
      m_listPoints = new ArrayList<>( dbHelper.selectPointsDayForData(
                      m_idGrupo, pDateStart, pDateEnd));

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
         //Variavel para somar as vitorias.
         int winsTotal = 0;
         //Dia do mes, para somar os dias jogados.
         int monthDay = 0;
         //Dias jogados.
         int daysPlayed = 0;
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

            //Dia do mes.
            if(jogador == jogador1 || jogador == jogador2 ||
                    jogador == gato1 || jogador == gato2){
               Date date = m_listPoints.get(x).getDtmPoint();
               Calendar c = Calendar.getInstance();
               c.setTime(date);
               Format formatDay = new SimpleDateFormat("dd");
               //Dia jogado.
               int day = Integer.valueOf( formatDay.format(c.getTime()) );
               //Se o dia for igual ao dia do mes, continua.
               if(day != monthDay){
                  monthDay = day;
                  daysPlayed++;
               }
            }

            //Verificando se o jogador foi derrotado.
            if(jogador == gato1 || jogador == gato2){
               losesTotal++;
               //Verificando se a partida foi gato.
               if(m_listPoints.get(x).getQtdPoint() == 3){
                  catTotal++;
               }
            }

            //Verificando se o jogador ganhou ponto nesse ponto percorrido.
            if(jogador == jogador1 || jogador == jogador2){
               int point = m_listPoints.get(x).getQtdPoint();
                ptTotal = ptTotal + point;
               winsTotal++;
            }
         }
         //Objeto de itens mostrados na tela.
          ItensListViewInicio ilv = new ItensListViewInicio();
         //id do jogador
         ilv.set_id( m_listJogadores.get(i).getIdJogador());
          //nome do jogador
          ilv.setM_name( DbHelper.getInstance(this ).selectNameJogador(jogador));
         //pontos total
         ilv.setM_pontos(String.valueOf(ptTotal));
         //Qt gatos
          ilv.setM_gatos(String.valueOf(catTotal));
          //Diferenca de pontos - gatos.
          ilv.setM_total_pontos(ptTotal - catTotal);
          //Qt derrotas
          ilv.setM_loses(losesTotal);
         //Qt vitorias
         ilv.setM_wins(winsTotal);
         //Dias jogados
         ilv.set_played_days( daysPlayed );

         listItensInicio.add(ilv);
      }

      ArrayList<ItensListViewInicio> newListItensInicio = new ArrayList<>();

      // Retirar jogadores que ainda não jogaram no mês.
      for(ItensListViewInicio jogadorNull : listItensInicio){
         if( jogadorNull.getM_loses() > 0 || jogadorNull.getM_wins() > 0 )
            newListItensInicio.add( jogadorNull );
      }

      listItensInicio = newListItensInicio;

      Collections.sort(listItensInicio, new comparatorDefault());

      return listItensInicio;
   }

   private void loadListResume(){
      m_listItensInicio = getListForDate( m_dateStart, m_dateEnd );
   }

   private void loadViewResume(){
      m_list = findViewById( R.id.lvJogadores );

      ArrayAdapter<ItensListViewInicio> adapter = new ArrayAdapter<ItensListViewInicio>( this,
         R.layout.activity_inicio_itens, m_listItensInicio ){
         @Override
         public View getView( int p_position, View p_convertView, ViewGroup p_parent ){
            // Use view holder patern to better performance with list view.
            ViewHolderItem vh = null;

            if ( p_convertView == null ){
               p_convertView = getLayoutInflater().inflate( R.layout.activity_inicio_itens, p_parent,
                  false );

               vh = new ViewHolderItem();

               vh.tvDaysPlayed = p_convertView.findViewById( R.id.tvDaysPlayed );

               vh.tvName = p_convertView.findViewById( R.id.tvName );

               vh.tvWins = p_convertView.findViewById( R.id.tvWins);
               vh.tvWinsToday = p_convertView.findViewById( R.id.tvWinsToday);

               vh.tvLoses = p_convertView.findViewById( R.id.tvLoses);
               vh.tvLosesToday = p_convertView.findViewById( R.id.tvLosesToday);

               vh.tvPtTotal = p_convertView.findViewById( R.id.tvPtTotal );
               vh.tvPtTotalToday = p_convertView.findViewById( R.id.tvPtTotalToday );

               vh.tvPtCat = p_convertView.findViewById( R.id.tvPtCat );
               vh.tvPtCatToday = p_convertView.findViewById( R.id.tvPtCatToday );

               vh.tvSaldo = p_convertView.findViewById( R.id.tvSaldo);

               // store holder with view.
               p_convertView.setTag( vh );
            }
            // get saved holder
            else{
               vh = ( ViewHolderItem )p_convertView.getTag();
            }

            ItensListViewInicio itemL = m_listItensInicio.get( p_position );
            final int itenListJogador = p_position;

            vh.tvDaysPlayed.setText(String.valueOf( (itemL.get_played_days())));

            // Name
            vh.tvName.setText( itemL.getM_name() );

            HashMap<String, String> map = getDataPlayer( itemL.get_id() );

            // Total Vitorias
            vh.tvWins.setText( String.valueOf(itemL.getM_wins()) );
            vh.tvWinsToday.setText( map.get("wins") );

            // Total derrotas
            vh.tvLoses.setText( String.valueOf(itemL.getM_loses()) );
            vh.tvLosesToday.setText( map.get("loses") );

            // Total pontos
            vh.tvPtTotal.setText( String.valueOf(itemL.getM_pontos()) );
            vh.tvPtTotalToday.setText( map.get("points") );

            // Gatos
            vh.tvPtCat.setText( itemL.getM_gatos() );
            vh.tvPtCatToday.setText( map.get("cats"));

            // Pontos
            vh.tvSaldo.setText( String.valueOf( itemL.getM_total_pontos() ) );

            /**
             *  Evento de toque em um item da lista.
             */
            vh.tvName.setOnClickListener( new View.OnClickListener(){
               @Override
               public void onClick( View p_view ){
                  final int idJogador = m_listJogadores.get( itenListJogador ).getIdJogador();
                  final int idGrupo2 = m_listJogadores.get( itenListJogador ).getIdGrupo();

                  AlertDialog.Builder dialogo = new AlertDialog.Builder(
                     GrupoInicioActivity.this );
                  dialogo.setTitle( "Opcoes" );
                  dialogo.setMessage( "Adicione um gato ou Edite um usuario!" );
                  dialogo.setPositiveButton( "Detalhes Ponto",
                     new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick( DialogInterface dialog, int which ){
                            Intent it = new Intent( getBaseContext(), ConsultaPontoJogadorActivity.class );
                            it.putExtra( "envioIdJogador", idJogador );
                            it.putExtra( "envioIdGrupo", idGrupo2 );
                            startActivity( it );
                            finish();
                        }
                     } );
                  dialogo.setNegativeButton( "Grafico", new DialogInterface.OnClickListener(){
                     @Override
                     public void onClick( DialogInterface dialog, int which ){
                        Intent it = new Intent( getBaseContext(), GraficoPontosDiaActivity.class );
                        it.putExtra( "envioIdJogador", idJogador );
                        it.putExtra( "envioIdGrupo", idGrupo2 );
                        startActivity( it );
                        finish();
                     }
                  } );
                  dialogo.show();
               }
            } );

            return p_convertView;
         }
         final class ViewHolderItem{
            TextView
                    tvDaysPlayed,
                    tvName,
                    tvWins, tvWinsToday,
                    tvLoses, tvLosesToday,
                    tvPtTotal, tvPtTotalToday,
                    tvPtCat, tvPtCatToday,
                    tvSaldo;
         }
      };
      m_list.setAdapter( adapter );
   }

   private void loadListGamesToday() {
      try {
         m_listPartidasToday = findViewById( R.id.lvPartidasMesGrupoInicio );
         m_list_partidas = DbHelper.getInstance( this
         ).selectItensPartidas(m_idGrupo, m_today, m_today);
      } catch (Exception e) {
         throw new RuntimeException(e);
      }
   }

   private void loadViewGamesToday()
   {
      ArrayAdapter<ItensListPartidasMes> adapter = new ArrayAdapter<>(
              this, R.layout.activity_itens_partidas_dia, m_list_partidas )
      {
         @Override
         public View getView( int p_position, View p_convertView, ViewGroup p_parent )
         {
            // Use view holder patern to better performance with list view.
            ViewHolderItem vh = null;

            if ( p_convertView == null )
            {
               p_convertView = getLayoutInflater().inflate(
                       R.layout.activity_itens_partidas_dia, p_parent, false );

               vh = new ViewHolderItem();
               vh.tvV1 = p_convertView.findViewById( R.id.tvTitleV1 );
               vh.tvV2 = p_convertView.findViewById( R.id.tvTitleV2 );
               vh.tvP1 = p_convertView.findViewById( R.id.tvTitleP1 );
               vh.tvP2 = p_convertView.findViewById( R.id.tvTitleP2 );
               vh.tvPoints = p_convertView.findViewById( R.id.tvTitleQtPt );

               // store holder with view.
               p_convertView.setTag( vh );
            }
            // get saved holder
            else
               vh = ( ViewHolderItem )p_convertView.getTag();

            final ItensListPartidasMes itemL = m_list_partidas.get( p_position );

            DbHelper dbHelper = DbHelper.getInstance( GrupoInicioActivity.this );

            // TextView V1
            vh.tvV1.setText( dbHelper.selectNameJogador( Integer.parseInt( itemL.get_v1() ) ) );
            // TextView V2
            vh.tvV2.setText( dbHelper.selectNameJogador( Integer.parseInt( itemL.get_v2() ) ) );
            // TextView P1
            vh.tvP1.setText( dbHelper.selectNameJogador( Integer.parseInt( itemL.get_p1() ) ) );
            // TextView P2
            vh.tvP2.setText( dbHelper.selectNameJogador( Integer.parseInt( itemL.get_p2() ) ) );
            // Total pontos
            vh.tvPoints.setText( itemL.get_points() );

            return p_convertView;
         }

         final class ViewHolderItem{
            TextView tvV1, tvV2, tvP1, tvP2, tvPoints;
         }
      };
      m_listPartidasToday.setAdapter( adapter );

   }

   /**
    * Volta se o mes tem 30 ou 31 dias.
    */
   private int getDaysForMonth(int pMonth) {
      switch (pMonth) {
         case 2:
            return 29;
         case 4:
         case 6:
         case 9:
         case 11:
            return 30;
         default:
            return 31;
      }
   }

   private String getNameMonth(String pMonth) {
      switch (pMonth) {
         case "01":
            return "Janeiro";
         case "02":
            return "Fevereiro";
         case "03":
            return "Março";
         case "04":
            return "Abril";
         case "05":
            return "Maio";
         case "06":
            return "Junho";
         case "07":
            return "Julho";
         case "08":
            return "Agosto";
         case "09":
            return "Setembro";
         case "10":
            return "Outubro";
         case "11":
            return "Novembro";
         case "12":
            return "Dezembro";
         default:
            return "";
      }
   }

   private void loadListWinnerLastFourMonths(){

      for(int i = 4; i > 0; i--){
         String mes = "";

         int mesAtualInt = Integer.parseInt( m_month );

         int mesAnterior = 0;
         int yearAnterior = 0;

         if( mesAtualInt < 4 ){
            mesAnterior = 12 + mesAtualInt - i;

            if(mesAnterior > 12){
               mesAnterior = i;
               yearAnterior = Integer.parseInt( m_year );
            }
            else
               yearAnterior = Integer.parseInt( m_year ) - 1;
         }

         //Acrescenta um zero antes do mes se ele for menor que 10.
         if (mesAnterior < 10) {
            mes = "0" + mesAnterior;
         } else {
            mes = String.valueOf(mesAnterior);
         }

         String dateStartMonth1 = yearAnterior +"-"+ mes + "-"+ "01";
         String dateEndMonth1 =
                 yearAnterior +"-"+ mes + "-"+ getDaysForMonth(  mesAnterior );

         ArrayList<ItensListViewInicio> listItensInicio = getListForDate(
                 dateStartMonth1, dateEndMonth1);

         //Se a lista for nula, continua.
         if (listItensInicio.size() == 0)
            continue;

         ItensListViewInicio item = listItensInicio.get(0);

         // for na lista e pega os que levaram mais gatos.
         int count = 0;
         String namePlayer = "";
         for( ItensListViewInicio item2 : listItensInicio ){
            Integer value = Integer.valueOf( item2.getM_gatos() );
            if( value > count ){
               namePlayer = item2.getM_name();
               count = value;
            }
         }

         String name = item.getM_name();
         String points = String.valueOf( item.getM_total_pontos() );

         switch (i) {
            case 4 -> {
               writeTextView(findViewById(R.id.tvTitleMes1), getNameMonth(mes));
               writeTextView(findViewById(R.id.tvTitleMes1ValuePontos),
                       name + " (" + points + ")");
               writeTextView(findViewById(R.id.tvTitleMes1ValueGatos),
                       namePlayer + " (" + count + ")");
            }
            case 3 -> {
               writeTextView(findViewById(R.id.tvTitleMes2), getNameMonth(mes));
               writeTextView(findViewById(R.id.tvTitleMes2ValuePontos),
                       name + " (" + points + ")");
               writeTextView(findViewById(R.id.tvTitleMes2ValueGatos),
                       namePlayer + " (" + count + ")");
            }
            case 2 -> {
               writeTextView(findViewById(R.id.tvTitleMes3),
                       getNameMonth(mes));
               writeTextView(findViewById(R.id.tvTitleMes3ValuePontos),
                       name + " (" + points + ")");
               writeTextView(findViewById(R.id.tvTitleMes3ValueGatos),
                       namePlayer + " (" + count + ")");
            }
            case 1 -> {
               writeTextView(findViewById(R.id.tvTitleMes4), getNameMonth(mes));
               writeTextView(findViewById(R.id.tvTitleMes4ValuePontos),
                       name + " (" + points + ")");
               writeTextView(findViewById(R.id.tvTitleMes4ValueGatos),
                       namePlayer + " (" + count + ")");
            }
         }
      }
   }

   private void writeTextView(TextView p_tv, String p_text){
      p_tv.setText( p_text );
   }

   private void takeScreenshot(){
      Date now = new Date();
      android.text.format.DateFormat.format( "yyyy-MM-dd_hh:mm:ss", now );
      try{
         // image naming and path to include sd card appending name you choose for file
         String mPath = Environment.getExternalStorageDirectory().toString() + "/" + now + ".jpg";

         // create bitmap screen capture
         View v1 = getWindow().getDecorView().getRootView();
         v1.setDrawingCacheEnabled( true );
         Bitmap bitmap = Bitmap.createBitmap( v1.getDrawingCache() );
         v1.setDrawingCacheEnabled( false );

         File imageFile = new File( mPath );

         FileOutputStream outputStream = new FileOutputStream( imageFile );
         int quality = 100;
         bitmap.compress( Bitmap.CompressFormat.JPEG, quality, outputStream );
         outputStream.flush();
         outputStream.close();

         openScreenshot( imageFile );
      }
      catch ( Throwable e ){
         // Several error may come out with file handling or DOM
         e.printStackTrace();
      }
   }

   private void openScreenshot( File imageFile ){
      PackageManager packageManager = getPackageManager();
      Intent intent = new Intent( Intent.ACTION_SEND );
      intent.setType( "text/plain" );
      List<ResolveInfo> resolvedInfoList = packageManager.queryIntentActivities( intent, 0 );
      for ( ResolveInfo resolveInfo : resolvedInfoList ){
         if ( resolveInfo.activityInfo.packageName.startsWith( "com.whatsapp" ) ){
            intent.setClassName( resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name );
            intent.addCategory( Intent.CATEGORY_LAUNCHER );
            break;
         }
      }
      intent.setPackage( "com.whatsapp" );// com.whatsapp
      intent.setType( "image/*" );
      Uri uri = Uri.fromFile( imageFile );
      intent.putExtra( Intent.EXTRA_STREAM, uri );
      startActivity( Intent.createChooser( intent, "Compartilhar imagem" ) );
   }

   private void CheckMonthYear(){
      Date data = new Date();
      Calendar c = Calendar.getInstance();
      c.setTime(data);
      // Pega o dia atual
      Format formatDay = new SimpleDateFormat("dd");
      String today = formatDay.format(c.getTime());
      // formata e exibe a data e hora
      Format formatMonth = new SimpleDateFormat("MM");
      m_month = formatMonth.format(c.getTime());
      Format formatYear = new SimpleDateFormat("yyyy");
      m_year = formatYear.format(c.getTime());
      m_today = m_year +"-"+ m_month +"-"+ today;

    switch (m_month){
       case "01":
          setTitle("Dominó - " + today + " Janeiro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "02":
          setTitle("Dominó - " + today + " Fevereiro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "29";
          break;
       case "03":
          setTitle("Dominó - " + today + " Março " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "04":
          setTitle("Dominó - " + today + " Abril " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "05":
          setTitle("Dominó - " + today + " Maio " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "06":
          setTitle("Dominó - " + today + " Abril " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "07":
          setTitle("Dominó - " + today + " Julho " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "08":
          setTitle("Dominó - " + today + " Agosto " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "09":
          setTitle("Dominó - " + today + " Setembro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "10":
          setTitle("Dominó - " + today + " Outubro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "11":
          setTitle("Dominó - " + today + " Novembro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "12":
          setTitle("Dominó - " + today + " Dezembro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
    }
   }

   private void getIdGrupo() {
      // ...Buscando o valor do idGrupo cadastrado no Banco / Parametros

      int valorIdGrupo = DbHelper.getInstance( this
              ).selectUmParametro( m_idParametro ).getValorParametro();
      if ( m_retornoIdGrupo > 0 )
         m_idGrupo = m_retornoIdGrupo;
      else
         m_idGrupo = valorIdGrupo;
   }

   private void getBundle() {
      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null ){
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null ){
            // vindo da activity main
            m_retornoIdGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
         }
      }
   }

   private HashMap<String, String> getDataPlayer(int idPlayer){
      HashMap<String, String> map = new HashMap<>();
      int wins = 0;
      int loses = 0;
      int points = 0;
      int cats = 0;

      for(ItensListPartidasMes item : m_list_partidas){
         if( idPlayer == Integer.valueOf( item.get_v1() ) ||
                 idPlayer == Integer.valueOf( item.get_v2() ) ){
            wins = wins + 1;
            points = points + Integer.parseInt(item.get_points() );
         }
         else
            if( idPlayer == Integer.valueOf( item.get_p1() ) ||
                    idPlayer == Integer.valueOf( item.get_p2() ) ){

               loses = loses + 1;

               if( item.get_points().equals("3") ){
                  cats = cats + 1;
                  points = points - 1;
               }
            }
      }
      map.put("wins", String.valueOf(wins));
      map.put("loses", String.valueOf(loses));
      map.put("points", String.valueOf(points));
      map.put("cats", String.valueOf(cats));
      return map;
   }

   @Override
   protected void onCreate( Bundle savedInstanceState ){
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_grupo_inicio );

      getBundle();

      getIdGrupo();

      CheckMonthYear();

      loadListResume();

      loadListGamesToday();

      loadViewResume();

      loadViewGamesToday();

      loadListWinnerLastFourMonths();
   }

   @Override
   public boolean onCreateOptionsMenu( Menu menu ){
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate( R.menu.grupo_inicio, menu );
      return true;
   }

   @Override
   public boolean onOptionsItemSelected( MenuItem p_item ){
      int id = p_item.getItemId();

      if( id == R.id.action_login ){
         startActivity(new Intent(this, LoginUserActivity.class));
      }else
         if( id == R.id.action_settings ){
            actionsSettingsMenu();
         }
         else
            if( id == R.id.action_points ){
               Intent it2 = new Intent( getBaseContext(), PartidasMesActivity.class );
               it2.putExtra( "envioIdGrupo", m_idGrupo );
               startActivity( it2 );
               finish();
      } else
         if( id == R.id.action_winners ){
            Intent it3 = new Intent( getBaseContext(), WinnersActivity.class );
            it3.putExtra( "envioIdGrupo", m_idGrupo );
            startActivity( it3 );
            finish();
      }
      if( id == R.id.action_out ){
         // Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context
         SharedPreferences pref;
         pref = getSharedPreferences( "info", MODE_PRIVATE );
         // Using putXXX - with XXX is type data you want to write like: putString, putInt... from Editor object
         Editor editor = pref.edit();
         editor.putString( "login", "vazio" );
         editor.putString( "pass", "vazio" );
         // finally, when you are done saving the values, call the commit() method.
         editor.commit();
         // Envia para o inicio.
         Intent it4 = new Intent( getBaseContext(), LoginGroupActivity.class );
         startActivity( it4 );
         finish();
      }else
         if( id == R.id.action_send_print ) {
            takeScreenshot();
         } else if (id == R.id.action_add_player) {
            try {
                Intent intent = new Intent( this, CadastroJogadorActivity.class );
                intent.putExtra( "idGrupo", m_retornoIdGrupo );
                startActivity( intent );
            } catch (Exception e) {
                throw new RuntimeException(e);
         }
      }


      return super.onOptionsItemSelected( p_item );
   }

   private void actionsSettingsMenu() {
      ProcessExportManager exportManager = new ProcessExportManager();
      if ( exportManager.makeDataExports( this ) ){
         /*
          * Mensagem de arquivos exportados com sucesso
          */
         AlertDialog alertDialog = new AlertDialog.Builder( GrupoInicioActivity.this )
                 .setIcon( android.R.drawable.ic_dialog_alert )
                 .setTitle( R.string.app_name ).setMessage( R.string.info_successExport )
                 .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick( DialogInterface dialog, int which )
                    {
                       dialog.dismiss();
                    }
                 } ).show();
      }
   }

   public void evtBtPointsDay( View p_view ){
      Intent intent = new Intent( this, PontosDiaActivity.class );
      intent.putExtra( "idGrupo", m_retornoIdGrupo );
      startActivity( intent );
   }

   public void evtBtCheckPoint( View p_view ){
      Intent it = new Intent( getBaseContext(), MarcarPonto2Activity.class );
      it.putExtra( "envioIdJogador", 0 );
      it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
      startActivity( it );
      finish();
   }

   /**
    * Ação realizada ao clicar no botão VOLTAR do dispositivo.
    */
   @Override
   public void onBackPressed(){
      super.onBackPressed();
   }

   @Override
   public void onItemClick( AdapterView<?> parent, View view, int position, long id ){
      // TODO Auto-generated method stub
   }

   public class comparatorDefault implements Comparator<ItensListViewInicio>{
      @Override
      public int compare(ItensListViewInicio t1, ItensListViewInicio t2) {
         if (t1.getM_total_pontos() > t2.getM_total_pontos())
            return -1;
         else if (t1.getM_total_pontos() < t2.getM_total_pontos())
            return +1;
         else return compareCats(t1,t2);
      }

      public int compareCats(ItensListViewInicio t1, ItensListViewInicio t2) {
         if (Integer.valueOf(t1.getM_gatos()) < Integer.valueOf(t2.getM_gatos()))
            return -1;
         else if (Integer.valueOf(t1.getM_gatos()) > Integer.valueOf(t2.getM_gatos()))
            return +1;
         else return compareWins(t1,t2);
      }

      public int compareWins(ItensListViewInicio t1, ItensListViewInicio t2) {
         if (t1.getM_wins() > t2.getM_wins())
            return -1;
         else if (t1.getM_wins() < t2.getM_wins())
            return +1;
         else return compareLoses(t1,t2);
      }

      public int compareLoses(ItensListViewInicio t1, ItensListViewInicio t2) {
         if (t1.getM_loses() > t2.getM_loses())
            return -1;
         else if (t1.getM_loses() < t2.getM_loses())
            return +1;
         else return 0;
      }
   }
}
