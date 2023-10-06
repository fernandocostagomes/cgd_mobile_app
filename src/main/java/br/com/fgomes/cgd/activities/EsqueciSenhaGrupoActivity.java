package br.com.fgomes.cgd.activities;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Grupo;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.SMS;

public class EsqueciSenhaGrupoActivity extends Activity
{
   private TextView m_txt = null;
   private CheckBox m_cb;
   EditText m_telefoneGrupo;

	@Override
   protected void onCreate( Bundle savedInstanceState )
   {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_esqueci_senha_grupo);

      m_telefoneGrupo = ( EditText )findViewById( R.id.etTelefone );
      m_telefoneGrupo.addTextChangedListener( new PhoneNumberFormattingTextWatcher() );

		m_txt = (TextView) findViewById(R.id.LembreteDeSenha);
		m_cb = (CheckBox) findViewById(R.id.cbEnviarSms);
	}

	@Override
   public boolean onCreateOptionsMenu( Menu menu )
   {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.esqueci_senha_grupo, menu);
		return true;
	}

	@Override
   public boolean onOptionsItemSelected( MenuItem item )
   {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

   public void evtBtEnviarSenha( View p_view )
   {
      String dadosIncomp = "Dados nao encontrados!";

		EditText nomeGrupo = (EditText) findViewById(R.id.etNomeGrupo);
		String textNomeGrupo = nomeGrupo.getText().toString();

      String textTelefoneGrupo = PhoneNumberUtils.formatNumber( m_telefoneGrupo.getText().toString() ).replaceAll(
               "\\s", "" );
      long intTelefoneGrupo = Long.parseLong( textTelefoneGrupo );

		DbHelper db = new DbHelper(this);

		Grupo retornoNomeGrupo = db.selectUmGrupo(textNomeGrupo);

		String Grupo = retornoNomeGrupo.getNomeGrupo();
      long Telefone = retornoNomeGrupo.getTelefoneGrupo();
		String Senha = retornoNomeGrupo.getSenhaGrupo();

      if ( m_cb.isChecked() )
      {
			SMS sms = new SMS();
         sms.enviarSms( this, textTelefoneGrupo, "Sua senha e: " + Senha );
      }
      else
      {
         if ( ( textNomeGrupo.equals( Grupo ) ) && ( intTelefoneGrupo == Telefone ) )
         {
            m_txt.setText( "Sua senha e: " + Senha );
         }
         else
         {
				m_txt.setText(dadosIncomp);
			}
		}
	}
}
