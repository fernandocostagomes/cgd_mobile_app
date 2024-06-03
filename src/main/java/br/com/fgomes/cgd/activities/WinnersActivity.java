package br.com.fgomes.cgd.activities;

import android.app.Activity;
import android.content.Intent;
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
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Pontos;
import br.com.fgomes.cgd.objects.PontosPeriodo;
import br.com.fgomes.cgd.utils.DateCGD;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.ItensListViewInicio;
import br.com.fgomes.cgd.utils.ItensListWinners;

/**
 * Created by fernando.gomes on 29/03/2018.
 */

public class WinnersActivity extends Activity{
    private int m_idGrupo;
    private ArrayList<ItensListWinners> m_itensListWinners;

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
                    viewHolder.tvMonth = p_convertView.findViewById( R.id.tvMonth );
                    viewHolder.tvYear = p_convertView.findViewById( R.id.tvYear );
                    viewHolder.tvWinner = p_convertView.findViewById( R.id.tvWinner );
                    viewHolder.tvWinnerPoints = p_convertView.findViewById( R.id.tvWinnerPoints );
                    viewHolder.tvCat = p_convertView.findViewById( R.id.tvCat );
                    viewHolder.tvCatPoints = p_convertView.findViewById( R.id.tvCatPoints );
                    // store holder with view.
                    p_convertView.setTag( viewHolder );
                }
                // get saved holder
                else{
                    viewHolder = ( ViewHolderItem )p_convertView.getTag();
                }
                ItensListWinners itemL = m_itensListWinners.get( p_position );

                // Month
                viewHolder.tvMonth.setText( itemL.getMonth() );
                // Year
                viewHolder.tvYear.setText( itemL.getYear());

                viewHolder.tvWinner.setText( itemL.getWinner());
                // Winner
                viewHolder.tvWinnerPoints.setText( itemL.getWinnerPoints());

                viewHolder.tvCat.setText( itemL.getCat());
                // Winner
                viewHolder.tvCatPoints.setText( itemL.getCatPoints());

                return p_convertView;
            }
            static final class ViewHolderItem{
                TextView tvMonth, tvYear, tvWinner, tvWinnerPoints, tvCat,
                        tvCatPoints;
            }
        };
        ListView listView = findViewById( R.id.lvWinners );
        listView.setAdapter( adapter );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winners);

        checkBundle();

        loadData();

        loadListView();
    }

    private void loadData() {

        Pontos pontos = new Pontos();
        PontosPeriodo pontosPeriodo = new PontosPeriodo();

        //Pega 3 meses de dados.
        m_itensListWinners = new ArrayList<>();

        List<String> listMonths = DateCGD.getPreviousMonthsDate(18);

        for (String month : listMonths) {
            String[] split = month.split(" - ");
            String dateStart = split[0];
            String dateEnd = split[1];

            ItensListWinners item = new ItensListWinners();

            // pega a data da string que esta com o seguinte formato: 01/01/2018
            String[] splitDate = dateStart.split("-");

            //Mes
            item.setMonth( DateCGD.getInstance().getNameMonth( splitDate[1] ) );

            //Ano
            item.setYear(splitDate[0]);

            pontosPeriodo = pontos.getCampeao(m_idGrupo, dateStart, dateEnd);

            if (pontosPeriodo.getQtdWin() == 0)
                continue;

            //Nome do Winner.
            item.setWinner(DbHelper.getInstance(getApplicationContext()).selectNameJogador(
                    pontosPeriodo.getIdWinJogador()));

            //Pontos do Winner.
            item.setWinnerPoints(String.valueOf(pontosPeriodo.getQtdWin()));

            //Nome do Cat.
            item.setCat(DbHelper.getInstance(getApplicationContext()).selectNameJogador(
                    pontosPeriodo.getIdGatosJogador()));

            //Pontos do Cat.
            item.setCatPoints(String.valueOf(pontosPeriodo.getQtdGatos()));

            m_itensListWinners.add(item);
        }
        Collections.reverse( m_itensListWinners );
    }

    private void checkBundle() {
        if ( getIntent() != null ){
            Bundle parametrosRecebidos = getIntent().getExtras();
            if ( parametrosRecebidos != null ){
                m_idGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
            }else
                finish();
        }
    }

    /**
     * Ação realizada ao clicar no botão VOLTAR do dispositivo.
     */
    @Override
    public void onBackPressed(){
        Intent it = new Intent( this, GrupoInicioActivity.class );
        it.putExtra( "envioIdGrupo", m_idGrupo );
        startActivity( it );
        super.onBackPressed();
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

        if(id == R.id.action_send_print){
            takeScreenshot();
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


