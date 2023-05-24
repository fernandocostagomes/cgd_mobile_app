package br.com.fgomes.cgd.activities;

import android.app.Activity;
import android.content.Intent;
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
import java.util.List;

import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Parametro;
import br.com.fgomes.cgd.objects.Pontos;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListViewInicio;
import br.com.fgomes.cgd.utils.ItensListWinners;

/**
 * Created by fernando.gomes on 29/03/2018.
 */

public class WinnersActivity extends Activity{

    private DbHelper m_db = new DbHelper( this );
    private int m_retornoIdGrupo;
    private int m_idGrupo;
    private int m_idParametro = 1;
    private ListView m_lvWinners;
    ArrayList<Jogador> m_listJogadores;
    ArrayList<Pontos> m_listPoints;
    private ArrayList<ItensListWinners> m_itensListWinners;
    private ArrayList<ItensListViewInicio> m_listItensInicio;
    View m_view;
    private String m_month = "";
    private String m_year = "";
    private String m_dateStart = "";
    private String m_dateEnd = "";
    private ArrayList<DateCGD> m_date;

    private List<ItensListViewInicio> loadListStart(String p_dateStart, String p_dateEnd){
        //Carregamento da lista de jogadores do grupo.
        m_listJogadores = new ArrayList<>( m_db.selectJogadoresGrupo( m_idGrupo ) );
        //Carregamento da lista de pontos de acordo com o mes corrente na data atual.
        m_listPoints = new ArrayList<>(m_db.selectPointsDayForData(m_idGrupo, p_dateStart, p_dateEnd));
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
        return m_listItensInicio;
    }

    private void loadListWinners(){
        //Inicializacao da lista com os itens mostrados na tela.
        m_itensListWinners = new ArrayList<>();
        for(int i =0; i < m_date.size(); i++){
            List<ItensListViewInicio> list = new ArrayList<>(loadListStart(m_date.get(i).getDateStart(), m_date.get(i).getDateEnd()));
            ItensListWinners ilw = new ItensListWinners();
            //Month
            ilw.setM_month(String.valueOf(i + 1));
            Log.i("Mes", String.valueOf(i + 1));
            //Year
            ilw.setM_year("2018");
            Log.i("Ano", "2018");
            //Points
            ilw.setM_points(String.valueOf(list.get(0).getM_total_pontos()));
            Log.i("Pontos", String.valueOf(list.get(0).getM_total_pontos()));
            //Winner
            ilw.setM_winner(list.get(0).getM_name());
            Log.i("Winner", list.get(0).getM_name());
            m_itensListWinners.add(ilw);
        }
        loadListView();
}

    private void loadListView(){
        ArrayAdapter<ItensListWinners> adapter = new ArrayAdapter<ItensListWinners>( this,
                R.layout.activity_itens_winners, m_itensListWinners ){
            @Override
            public View getView( int p_position, View p_convertView, ViewGroup p_parent ){
                // Use view holder patern to better performance with list view.
                ViewHolderItem viewHolder = null;
                if ( p_convertView == null ){
                    p_convertView = getLayoutInflater().inflate( R.layout.activity_itens_winners, p_parent,
                            false );
                    viewHolder = new ViewHolderItem();
                    viewHolder.textViewMonth = p_convertView.findViewById( R.id.tvMonth );
                    viewHolder.textViewYear = p_convertView.findViewById( R.id.tvYear );
                    viewHolder.textViewPoints = p_convertView.findViewById( R.id.tvPoints );
                    viewHolder.textViewWinner = p_convertView.findViewById( R.id.tvWinner );
                    // store holder with view.
                    p_convertView.setTag( viewHolder );
                }
                // get saved holder
                else{
                    viewHolder = ( ViewHolderItem )p_convertView.getTag();
                }
                ItensListWinners itemL = m_itensListWinners.get( p_position );
                // Points
                int points = Integer.valueOf(itemL.m_points);
                if(points == 0) {
                    // Month
                    viewHolder.textViewMonth.setText("");
                    // Year
                    viewHolder.textViewYear.setText("");

                    viewHolder.textViewPoints.setText("");
                    // Winner
                    viewHolder.textViewWinner.setText("");
                }
                else{
                    // Month
                    viewHolder.textViewMonth.setText(itemL.m_month);
                    // Year
                    viewHolder.textViewYear.setText(itemL.m_year);

                    viewHolder.textViewPoints.setText(itemL.m_points);
                    // Winner
                    viewHolder.textViewWinner.setText(itemL.m_winner);
                }
                return p_convertView;
            }
            final class ViewHolderItem{
                TextView textViewMonth;
                TextView textViewYear;
                TextView textViewPoints;
                TextView textViewWinner;
            }
        };
        m_lvWinners.setAdapter( adapter );
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
        switch (m_month)
        {
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);
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
        m_lvWinners = (ListView)findViewById( R.id.lvWinners );
        loadListDate();
        loadListWinners();
    }

    /**
     * Ação realizada ao clicar no botão VOLTAR do dispositivo.
     */
    @Override
    public void onBackPressed(){
        Intent it = new Intent( this, GrupoInicioActivity.class );
        it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
        startActivity( it );
        super.onBackPressed();
    }

    private void loadListDate() {
        m_date = new ArrayList<>();
        for (int i = 1; i < 13; i++){
            if(i == 1 || i == 3|| i == 5 || i == 7 || i == 8){
                m_dateStart = "2018" + "-" +"0"+ String.valueOf(i) + "-" + "01";
                m_dateEnd = "2018" + "-" +"0"+ String.valueOf(i) + "-" + "31";
            }
            if(i == 10 || i == 12){
                m_dateStart = "2018" + "-" + String.valueOf(i) + "-" + "01";
                m_dateEnd = "2018" + "-" + String.valueOf(i) + "-" + "31";
            }
            if(i == 2 ){
                m_dateStart = "2018" + "-" +"0"+ String.valueOf(i) + "-" + "01";
                m_dateEnd = "2018" + "-" +"0"+ String.valueOf(i) + "-" + "29";
            }
            if(i == 4 || i == 6 || i == 9 || i == 11 ){
                m_dateStart = "2018" + "-" +"0"+ String.valueOf(i) + "-" + "01";
                m_dateEnd = "2018" + "-" +"0"+ String.valueOf(i) + "-" + "30";
            }
            if(i == 11 ){
                m_dateStart = "2018" + "-" + String.valueOf(i) + "-" + "01";
                m_dateEnd = "2018" + "-" + String.valueOf(i) + "-" + "30";
            }
            DateCGD listDate = new DateCGD();
            listDate.setDateStart(m_dateStart);
            listDate.setDateEnd(m_dateEnd);
            m_date.add(listDate);
            Log.i("Mes", String.valueOf(i)+" " + m_dateStart + " " + m_dateEnd );
        }
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

    @Override
    public boolean onCreateOptionsMenu( Menu menu ){
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate( R.menu.winner_menu, menu );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected( MenuItem p_item ){
        int id = p_item.getItemId();
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch ( id ){
            case R.id.action_send_print:
                takeScreenshot();
                break;
        }
        return super.onOptionsItemSelected( p_item );
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

    class DateCGD{
        private String dateStart;
        private String dateEnd;

        public String getDateStart() {
            return dateStart;
        }

        public void setDateStart(String dateStart) {
            this.dateStart = dateStart;
        }

        public String getDateEnd() {
            return dateEnd;
        }

        public void setDateEnd(String dateEnd) {
            this.dateEnd = dateEnd;
        }
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
            if (Integer.valueOf(t1.getM_gatos()) > Integer.valueOf(t2.getM_gatos()))
                return -1;
            else if (Integer.valueOf(t1.getM_gatos()) < Integer.valueOf(t2.getM_gatos()))
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


