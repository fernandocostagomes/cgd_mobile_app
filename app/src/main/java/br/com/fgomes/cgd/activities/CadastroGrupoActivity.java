package br.com.fgomes.cgd.activities;

import java.sql.Date;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Grupo;
import br.com.fgomes.cgd.utils.DbHelper;

public class CadastroGrupoActivity extends Activity
{
   private EditText m_telefone;
   private EditText nome;
   private EditText senha;
   private EditText contraSenha;

	@Override
   protected void onCreate( Bundle savedInstanceState )
   {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cadastro_grupo);

      // .................Funcao para comparar a Senha e a ContraSenha...................
		final EditText compararSenha 		= ( EditText ) findViewById(R.id.edCadastroSenha);
		final EditText compararContraSenha 	= ( EditText ) findViewById(R.id.edCadastroSenhaConfirma);	
      m_telefone = ( EditText )findViewById( R.id.edCadastrarTelefoneContatoGrupo );
      m_telefone.addTextChangedListener( new PhoneNumberFormattingTextWatcher() );

      // ..........Nome do grupo..........
      nome = ( EditText )findViewById( R.id.edCadastroNomeGrupo );

      // ..........Senha do grupo..........
      senha = ( EditText )findViewById( R.id.edCadastroSenha );

      // ..........Contra Senha do grupo...
      contraSenha = ( EditText )findViewById( R.id.edCadastroSenhaConfirma );
		
      compararContraSenha.addTextChangedListener( new TextWatcher()
      {
         @Override
         public void onTextChanged( CharSequence s, int start, int before, int count )
         {
            // here is your code
            String valueContraSenha = s.toString();
            String valueSenha = compararSenha.getText().toString();
            if ( !valueSenha.substring( 0, valueContraSenha.length() ).equals( valueContraSenha ) )
            {
               compararContraSenha.setBackgroundColor( Color.RED );
               Toast.makeText( getApplicationContext(), "Valores diferentes", Toast.LENGTH_SHORT ).show();
            }
         }
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
                  int after )
         {
         }
			@Override
         public void afterTextChanged( Editable s )
         {
         }
        });
	}

   // Botao entrar
   public void evtBtCadastrarGrupo( View p_view )
   {
		DbHelper dbh = new DbHelper(this);

		String senhaGrupo = senha.getText().toString();
		String contraSenhaGrupo = contraSenha.getText().toString();
      String stringTelefone = PhoneNumberUtils.formatNumber( m_telefone.getText().toString() ).replaceAll( "\\s", "" );

		// ..........Comparar a Senha e contraSenha..........
      if ( senhaGrupo.equals( contraSenhaGrupo ) )
      {
         Grupo valor = new Grupo();
         valor.setNomeGrupo( nome.getText().toString() );
         valor.setSenhaGrupo( senha.getText().toString() );
         valor.setDataGrupo( new Date( System.currentTimeMillis() ) );
         valor.setTelefoneGrupo( Long.parseLong( stringTelefone ) );

			dbh.insertGrupo(valor);
         Toast.makeText( getApplicationContext(), "Grupo cadastro, por favor faca o Login!", Toast.LENGTH_LONG ).show();
			startActivity(new Intent(getApplicationContext(),
					MainActivity.class));
			finish();
      }
      else
      {
         Toast.makeText( getApplicationContext(), "Senha e confirmacao de senha diferentes!", Toast.LENGTH_SHORT )
                  .show();
		}
   }

   @Override
   public void onBackPressed()
   {
      super.onBackPressed();
      startActivity( new Intent( getApplicationContext(), MainActivity.class ) );
   }
}