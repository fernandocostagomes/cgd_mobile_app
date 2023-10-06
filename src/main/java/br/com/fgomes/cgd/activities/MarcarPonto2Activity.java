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
import br.com.fgomes.cgd.views.CheckBoxCGD;
public class MarcarPonto2Activity extends Activity
{
   private int mRetornoIdGrupo, mRetornoIdJogador, mIdGrupo;
   private DbHelper mDbHelper;
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

   private void montarLista( ){
      List<LinearLayout> linearLayoutList = new ArrayList<>();
      linearLayoutList.add( findViewById( R.id.ll_won ) );
      linearLayoutList.add( findViewById( R.id.ll_lose ) );
      for(LinearLayout linearLayout : linearLayoutList) {
         linearLayout.removeAllViews();
         LinkedHashMap<String, String> alphabet = new LinkedHashMap<>();
         linearLayout.removeAllViews();
         for (Jogador jogador : mDbHelper.selectJogadoresGrupo( mIdGrupo )) {
            if (jogador.getIdJogador() != mRetornoIdJogador) {
               alphabet.put(String.valueOf(jogador.getIdJogador()), jogador.getNomeJogador());
            }
         }
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

   private void clickMarcarPonto() {
      Button button = findViewById(R.id.marcarPonto);
      button.setOnClickListener(v -> {
         try {
            mDbHelper.insertPoint(
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_won)).get(0),
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_won)).get(1),
                    getValorPontosPartida(),
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_lose)).get(0),
                    mCheckBoxCGD.getIdsJogadoresSelecionados(findViewById(R.id.ll_lose)).get(1),
                    mRetornoIdGrupo);
               Toast.makeText(getApplicationContext(), "Ponto marcado!", Toast.LENGTH_SHORT).show();
               backToInit();
            }
            catch (Exception p_e){
               p_e.printStackTrace();
            }
      });
   }

   private void backToInit(){
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", mRetornoIdGrupo);
      startActivity( it );
      finish();
   }

   @Override
   protected void onCreate( Bundle savedInstanceState )
   {
      super.onCreate( savedInstanceState );
      setContentView( R.layout.activity_marcar_ponto);

      mDbHelper = new DbHelper( this );

      mCheckBoxCGD = CheckBoxCGD.getInstance(this);

      getBundle();

      montarLista();

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