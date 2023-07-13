package br.com.fgomes.cgd.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.utils.DbHelper;

public class MarcarPonto2Activity extends Activity
{
   private int mRetornoIdGrupo, mRetornoIdJogador, mIdGrupo;
   private DbHelper mDbHelper;
   private List<Jogador> mListaJogadoresVencedores, mListaJogadoresPerdedores, getmListaJogadoresVencedoresSelecionados;

   private View.OnClickListener clickJogador(final Button button, boolean pVencedor )
   {
      return v -> {
         Jogador jogador = getJogador(button.getText().toString());
         if(((CheckBox) v).isChecked()){
            //Vencedor
            if( pVencedor ){
               if( mListaJogadoresVencedores.size() < 2){
                  mListaJogadoresVencedores.add(mDbHelper.selectUmJogador(button.getId()));
                  mListaJogadoresPerdedores.remove(jogador);
                  montarLista(false);
                  if(mListaJogadoresVencedores.size() == 2){

                  }
               }
               else{
                    Toast.makeText(getApplicationContext(), "É possível marcar apenas " +
                            "dois jogadores!", Toast.LENGTH_SHORT).show();
                    ((CheckBox) v).setChecked(false);
               }
            }
            //Perdedor
            else{
               if( mListaJogadoresVencedoresIds.size() < 2 ) {
                  Toast.makeText(getApplicationContext(), "Primeiro marcar os ganhadores!",
                          Toast.LENGTH_SHORT).show();
                  ((CheckBox) v).setChecked(false);
               }
               else {
                  if ( mListaJogadoresPerdedoresIds.size() < 2 ){
                     Jogador jog = mDbHelper.selectUmJogador(button.getId());
                     mListaJogadoresPerdedoresIds.add(jog.getIdJogador());
                  }
                  else{
                     Toast.makeText(getApplicationContext(), "É possível marcar apenas " +
                             "dois jogadores!", Toast.LENGTH_SHORT).show();
                     ((CheckBox) v).setChecked(false);
                  }
               }
            }
         }
         else
         {
            List<Integer> listJogador = new ArrayList<>();
            Jogador jogadorId = mDbHelper.selectUmJogador(button.getId());
            if(pVencedor){
               listJogador = mListaJogadoresVencedoresIds;
               mListaJogadoresPerdedores.add(jogadorId);
            }
            else
               listJogador = mListaJogadoresPerdedoresIds;

            for (int i = 0; i < listJogador.size(); i++) {
               Integer value = listJogador.get(i);
               if (value == jogadorId.getIdJogador()) {
                  listJogador.remove(i);
               }
            }

            if(pVencedor)
               mListaJogadoresVencedoresIds = listJogador;
            else
               mListaJogadoresPerdedoresIds = listJogador;

            montarListaPerdedores();
         }
      };
   }

   private void montarLista( boolean pVencedor ){
      CheckBox checkBox;
      LinearLayout  linearLayout = findViewById( pVencedor == true ? R.id.ll_won : R.id.ll_lose );
      linearLayout.removeAllViews();

      List<Jogador> jogadorList = new ArrayList<>();

      if(pVencedor)
            jogadorList = mListaJogadoresVencedores;
        else
            jogadorList = mListaJogadoresPerdedores;

      LinkedHashMap<String, String> alphabet = new LinkedHashMap<>();
      for ( Jogador jogador : jogadorList){
         if ( jogador.getIdJogador() != mRetornoIdJogador){
            alphabet.put( String.valueOf( jogador.getIdJogador() ), jogador.getNomeJogador() );
         }
      }

      Set<?> set = alphabet.entrySet();
      // Get an iterator
      Iterator<?> i = set.iterator();
      // Display elements
      while ( i.hasNext() ){
         Map.Entry me = ( Map.Entry )i.next();
         checkBox = new CheckBox( this );
         checkBox.setId( Integer.parseInt( me.getKey().toString() ) );
         checkBox.setText( me.getValue().toString() );
         checkBox.setOnClickListener( clickJogador( checkBox, pVencedor ) );
         linearLayout.addView( checkBox );
      }
   }

   private Jogador getJogador(String p_name){
      Jogador jogador = new Jogador();

      for (Jogador jog : mListaJogadoresPerdedores){
         if(jog.getNomeJogador().equals(p_name))
            jogador = jog;
      }
      return  jogador;
   }

   private int getValorPontosPartida(){
      RadioButton rb0 = findViewById( R.id.radio0 );
      RadioButton rb1 = findViewById( R.id.radio1 );

      int result = 0;
      if ( rb0.isChecked() )
         result = 3;
      else
      if ( rb1.isChecked() )
         result = 2;
      else
         result = 1;
      return result;
   }

   private void getBundle() {
      Intent iDadosRecebidos = getIntent();
      if ( iDadosRecebidos != null ){
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null ){
            mRetornoIdGrupo = parametrosRecebidos.getInt( "envioIdGrupo" );
            mRetornoIdJogador = parametrosRecebidos.getInt( "envioIdJogador" );
         }
      }
      // ...Buscando o valor do idGrupo cadastrado no Banco / Parametros
      if ( mRetornoIdGrupo > 0 ) {
         mIdGrupo = mRetornoIdGrupo;
      }
   }

   private void marcarPonto(View p_view ){

   }

   private void clickMarcarPonto() {
      Button button = findViewById(R.id.marcarPonto);
      button.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
            if(mListaJogadoresPerdedores.size() <2)
               Toast.makeText(getApplicationContext(), "O número de vencedores é menor que o" +
                       " permitido.", Toast.LENGTH_SHORT).show();
            else
            if(mListaJogadoresPerdedores.size() <2)
               Toast.makeText(getApplicationContext(), "O número de perdedores é menor que o " +
                       "permitido.", Toast.LENGTH_SHORT).show();
            else
               try {
                  mDbHelper.insertPoint(
                          mListaJogadoresVencedores.get(0).getIdJogador(),
                          mListaJogadoresVencedores.get(1).getIdJogador(),
                          getValorPontosPartida(),
                          mListaJogadoresPerdedores.get(0).getIdJogador(),
                          mListaJogadoresPerdedores.get(1).getIdJogador(),
                          mRetornoIdGrupo);
                  Toast.makeText(getApplicationContext(), "Ponto marcado!", Toast.LENGTH_SHORT).show();
                  backToInit();
               }
               catch (Exception p_e){
                  p_e.printStackTrace();
               }
         }
      });
   }

   private void backToInit(){
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", mRetornoIdGrupo);
      startActivity( it );
      finish();
   }

   private void iniciandoListas() {
      mListaJogadoresVencedores = new ArrayList<>( mDbHelper.selectJogadoresGrupo(mIdGrupo) );
      mListaJogadoresPerdedores = mListaJogadoresVencedores;
   }

   private void montandoListas(){
      montarLista( true, mListaJogadoresVencedores );
      montarLista( false, mListaJogadoresPerdedores );
   }

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_marcar_ponto);

      mDbHelper = new DbHelper( this );

      getBundle();

      iniciandoListas();

      montandoListas();

      clickMarcarPonto();
   }

   @Override
   public void onBackPressed(){
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", mRetornoIdGrupo);
      startActivity( it );

      super.onBackPressed();
   }
}
