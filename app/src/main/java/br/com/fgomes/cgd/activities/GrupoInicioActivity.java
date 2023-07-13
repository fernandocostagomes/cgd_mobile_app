package br.com.fgomes.cgd.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.text.*;
import java.util.*;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Parametro;
import br.com.fgomes.cgd.objects.Pontos;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListViewInicio;
import br.com.fgomes.cgd.utils.ProcessExportManager;

public class GrupoInicioActivity extends Activity implements OnItemClickListener{
   private DbHelper m_db = new DbHelper( this );
   private int m_retornoIdGrupo;
   private int m_idGrupo;
   private int m_idParametro = 1;
   private ListView m_list;
   ArrayList<Jogador> m_listJogadores;
   ArrayList<Pontos> m_listPoints;
   private ArrayList<ItensListViewInicio> m_listItensInicio;
   View m_view;
   private String m_month = "";
   private String m_year = "";
   private String m_dateStart = "";
   private String m_dateEnd = "";

   private void loadListStart(){
      //Carregamento da lista de jogadores do grupo.
      m_listJogadores = new ArrayList<>( m_db.selectJogadoresGrupo( m_idGrupo ) );
      //Carregamento da lista de pontos de acordo com o mes corrente na data atual.
      m_listPoints = new ArrayList<>(m_db.selectPointsDayForData(m_idGrupo, m_dateStart, m_dateEnd));
      //Inicializacao da lista com os itens mostrados na tela.
      m_listItensInicio = new ArrayList<>();
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
          //nome do jogador
          ilv.setM_name(m_db.selectNameJogador(jogador));
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
          m_listItensInicio.add(ilv);
      }
      Collections.sort(m_listItensInicio, new comparatorDefault());
      loadListView();
   }
   private void loadListView(){
      ArrayAdapter<ItensListViewInicio> adapter = new ArrayAdapter<ItensListViewInicio>( this,
         R.layout.activity_inicio_itens, m_listItensInicio ){
         @Override
         public View getView( int p_position, View p_convertView, ViewGroup p_parent ){
            // Use view holder patern to better performance with list view.
            ViewHolderItem viewHolder = null;

            if ( p_convertView == null ){
               p_convertView = getLayoutInflater().inflate( R.layout.activity_inicio_itens, p_parent,
                  false );

               viewHolder = new ViewHolderItem();

               viewHolder.textViewName = ( TextView )p_convertView.findViewById( R.id.tvName );
               viewHolder.textViewTotal = ( TextView )p_convertView.findViewById( R.id.tvPtTotal );
               viewHolder.textViewPtCat = ( TextView )p_convertView.findViewById( R.id.tvPtCat );
               viewHolder.textViewPtTotal = ( TextView )p_convertView.findViewById( R.id.tvTotal );
               viewHolder.textViewLoses = ( TextView )p_convertView.findViewById( R.id.tvLoses);
               viewHolder.textViewWins = ( TextView )p_convertView.findViewById( R.id.tvWins);

               // store holder with view.
               p_convertView.setTag( viewHolder );
            }
            // get saved holder
            else{
               viewHolder = ( ViewHolderItem )p_convertView.getTag();
            }

            ItensListViewInicio itemL = m_listItensInicio.get( p_position );
            final int itenListJogador = p_position;

            // Name
            viewHolder.textViewName.setText( itemL.m_name );
            // Pontos
            viewHolder.textViewTotal.setText( itemL.m_pontos );
            // Gatos
            viewHolder.textViewPtCat.setText( itemL.m_gatos );
            // Total pontos
            viewHolder.textViewPtTotal.setText( String.valueOf(itemL.m_total_pontos) );
            // Total derrotas
            viewHolder.textViewLoses.setText( String.valueOf(itemL.m_loses) );
            // Total Vitorias
            viewHolder.textViewWins.setText( String.valueOf(itemL.m_wins) );

            /**
             *  Evento de toque em um item da lista.
             */
            viewHolder.textViewName.setOnClickListener( new View.OnClickListener(){
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
            TextView textViewName;
            TextView textViewTotal;
            TextView textViewPtCat;
            TextView textViewPtTotal;
            TextView textViewLoses;
            TextView textViewWins;
         }
      };
      m_list.setAdapter( adapter );
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
      // formata e exibe a data e hora
      Format formatMonth = new SimpleDateFormat("MM");
      m_month = formatMonth.format(c.getTime());
      Format formatYear = new SimpleDateFormat("yyyy");
      m_year = formatYear.format(c.getTime());
    switch (m_month){
       case "01":
          setTitle("Pontos Dominó - Janeiro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "02":
          setTitle("Pontos Dominó - Fevereiro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "29";
          break;
       case "03":
          setTitle("Pontos Dominó - Março " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "04":
          setTitle("Pontos Dominó - Abril " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "05":
          setTitle("Pontos Dominó - Maio " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "06":
          setTitle("Pontos Dominó - Abril " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "07":
          setTitle("Pontos Dominó - Julho " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "08":
          setTitle("Pontos Dominó - Agosto " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "09":
          setTitle("Pontos Dominó - Setembro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "10":
          setTitle("Pontos Dominó - Outubro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
       case "11":
          setTitle("Pontos Dominó - Novembro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "30";
          break;
       case "12":
          setTitle("Pontos Dominó - Dezembro " + m_year);
          m_dateStart = m_year +"-"+ m_month +"-"+ "01";
          m_dateEnd = m_year +"-"+ m_month +"-"+ "31";
          break;
    }
   }

   public GrupoInicioActivity(){}

   public void teste(){

   }

   @Override
   protected void onCreate( Bundle savedInstanceState ){
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_grupo_inicio );
      m_view = getWindow().getDecorView().findViewById( android.R.id.content );
      CheckMonthYear();
      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null ){
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null ){
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
      m_list = findViewById( R.id.lvJogadores );
      loadListStart();
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
      // Handle action bar item clicks here. The action bar will
      // automatically handle clicks on the Home/Up button, so long
      // as you specify a parent activity in AndroidManifest.xml.
      switch ( id ){

         case R.id.action_login:
            startActivity(new Intent(this, LoginActivity.class));
            break;
         case R.id.action_settings:
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
         break;
         case R.id.action_points:
            Intent it2 = new Intent( getBaseContext(), PartidasMesActivity.class );
            it2.putExtra( "envioIdGrupo", m_idGrupo );
            startActivity( it2 );
            finish();
         break;
         case R.id.action_winners:
            Intent it3 = new Intent( getBaseContext(), WinnersActivity.class );
            it3.putExtra( "envioIdGrupo", m_idGrupo );
            startActivity( it3 );
            finish();
            break;
         case R.id.action_out:
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
            Intent it4 = new Intent( getBaseContext(), MainActivity.class );
            startActivity( it4 );
            finish();
         break;

         case R.id.action_send_print:
            takeScreenshot();
         break;
      }
      return super.onOptionsItemSelected( p_item );
   }

   public void evtBtPointsDay( View p_view ){
      Intent intent = new Intent( this, PontosDiaActivity.class );
      intent.putExtra( "idGrupo", m_retornoIdGrupo );
      startActivity( intent );
   }

   public void evtBtCadastrarJogador( View p_view ){
      Intent intent = new Intent( this, CadastroJogadorActivity.class );
      intent.putExtra( "idGrupo", m_retornoIdGrupo );
      startActivity( intent );
   }

   public void evtBtCheckPoint( View p_view ){
      Intent it = new Intent( getBaseContext(), MarcarPontoActivity.class );
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
