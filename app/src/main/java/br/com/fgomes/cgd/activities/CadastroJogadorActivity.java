package br.com.fgomes.cgd.activities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.*;
import android.net.*;
import android.os.Bundle;
import android.provider.*;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.telephony.PhoneNumberUtils;
import android.util.*;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Jogador;
import br.com.fgomes.cgd.objects.Parametro;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.EmailValidator;
import br.com.fgomes.cgd.views.ToastSnack;

public class CadastroJogadorActivity extends Activity
{
   private int m_retornoIdGrupo;
   private int m_idGrupo;
   private int m_idParametro = 1;
   private EditText m_nome, m_telefone, m_email;
   private Context m_context;

   private static final String TAG = CadastroJogadorActivity.class.getSimpleName();
   private static final int REQUEST_CODE_PICK_CONTACTS = 1;
   private Uri uriContact;
   private String contactID;     // contacts unique ID

   private void retrieveContactNumber() {
      String contactNumber = null;

      // getting contacts ID
      Cursor cursorID = getContentResolver().query(uriContact,
              new String[]{ContactsContract.Contacts._ID},
              null, null, null);

      if (cursorID.moveToFirst()) {

         contactID = cursorID.getString(cursorID.getColumnIndex(ContactsContract.Contacts._ID));
      }

      cursorID.close();

      Log.d(TAG, "Contact ID: " + contactID);

      // Using the contact ID now we will get contact phone number
      Cursor cursorPhone = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
              new String[]{ContactsContract.CommonDataKinds.Phone.NUMBER},

              ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ? AND " +
                      ContactsContract.CommonDataKinds.Phone.TYPE + " = " +
                      ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE,

              new String[]{contactID},
              null);

      if (cursorPhone.moveToFirst()) {
         contactNumber = cursorPhone.getString(cursorPhone.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
      }

      cursorPhone.close();

      Log.d(TAG, "Contact Phone Number: " + contactNumber);

      m_telefone.setText(contactNumber);
   }

   private void retrieveContactName() {

      String contactName = null;

      // querying contact data store
      Cursor cursor = getContentResolver().query(uriContact, null, null, null, null);

      if (cursor.moveToFirst()) {

         // DISPLAY_NAME = The display name for the contact.
         // HAS_PHONE_NUMBER =   An indicator of whether this contact has at least one phone number.

         contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
      }

      cursor.close();

      Log.d(TAG, "Contact Name: " + contactName);
      m_nome.setText(contactName);

   }

	@Override
   protected void onCreate( Bundle savedInstanceState )
   {
      setContentView(R.layout.activity_cadastro_jogador);

      super.onCreate(savedInstanceState);

      m_context = this;

      Intent iDadosRecebidos = getIntent();

      if ( iDadosRecebidos != null )
      {
         Bundle parametrosRecebidos = iDadosRecebidos.getExtras();
         if ( parametrosRecebidos != null )
         {
            // vindo da activity main
            m_retornoIdGrupo = parametrosRecebidos.getInt( "idGrupo" );
         }
      }

      m_nome = ( EditText )findViewById( R.id.edCadastroNomeJogador );
      m_telefone = ( EditText )findViewById( R.id.edCadastroTelefoneJogador );
      m_telefone.addTextChangedListener( new PhoneNumberFormattingTextWatcher() );
      m_email = ( EditText )findViewById( R.id.edCadastroEmailJogador );

	}

   @Override
   protected void onActivityResult(int requestCode, int resultCode, Intent data) {

      super.onActivityResult(requestCode, resultCode, data);

      if (requestCode == REQUEST_CODE_PICK_CONTACTS && resultCode == RESULT_OK) {
         Log.d(TAG, "Response: " + data.toString());
         uriContact = data.getData();
         retrieveContactNumber();
         retrieveContactName();
      }
   }

	public void evtBtCadastrarJogador( View p_view )
   {
      boolean sucess = false;

      EmailValidator ev = new EmailValidator();
      sucess = ev.validate( m_email.getText().toString() );

      if ( sucess )
      {

         DbHelper dbh = new DbHelper( this );

         // ..........Telefone do Jogador..........
         String StringtelefoneJogador =
                 m_telefone.getText().toString().replaceAll("[^0-9]", "");

         // ..........IdGrupo - Passagem de param.
         Intent intent = getIntent();

         if ( intent != null )
         {
            Bundle params = intent.getExtras();
            if ( params != null )
            {
               m_retornoIdGrupo = params.getInt( "idGrupo" );
            }

            if ( m_retornoIdGrupo > 0 )
            {
               m_idGrupo = m_retornoIdGrupo;
            }
            else
            {
               Parametro param = dbh.selectUmParametro( m_idParametro );
               int valorIdGrupo = param.getValorParametro();
               m_idGrupo = valorIdGrupo;
            }

            // ..........Cadastrando o Jogador..........
            Jogador jogador = new Jogador();

            jogador.setDataJogador( new Date( System.currentTimeMillis() ) );
            jogador.setEmailJogador( m_email.getText().toString() );
            jogador.setIdGrupo( m_idGrupo );
            jogador.setNomeJogador( m_nome.getText().toString() );
            jogador.setTelefoneJogador( Long.parseLong( StringtelefoneJogador ) );

            dbh.insertJogador( jogador );

            ToastSnack.show(getApplicationContext(), "Jogador cadastrado");

            Toast.makeText( getApplicationContext(), "Jogador cadastrado!",
                     Toast.LENGTH_LONG ).show();

            Intent it = new Intent( getApplicationContext(), GrupoInicioActivity.class );
            it.putExtra( "envioIdGrupo", m_idGrupo );
            startActivity( it );

            finish();
         }

      }
      else
      {
         Toast.makeText( getApplicationContext(), "E-mail não validado com sucesso!", Toast.LENGTH_LONG ).show();
      }
	}

   public void evtBtImportarContatoAgenda( View p_view ){
      startActivityForResult(new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI),
              REQUEST_CODE_PICK_CONTACTS);
	}

    public void evtImportarJogadorOutroGrupo(View p_view){
      //Lista de todos os jogadores
       List<Jogador> listPlayers;
       List<String> listPlayersName = new ArrayList<>();
       DbHelper dbh = new DbHelper( this );
       listPlayers = dbh.selectTodosJogadores();

       for(Jogador playerName : listPlayers){
          listPlayersName.add(playerName.getNomeJogador());
       }

       final Spinner spinner = new Spinner( this );
       ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>( this,
               android.R.layout.simple_spinner_item, listPlayersName );
       ArrayAdapter<String> spinnerArrayAdapter = arrayAdapter;
       spinnerArrayAdapter.setDropDownViewResource( android.R.layout.simple_spinner_item );
       spinner.setAdapter( spinnerArrayAdapter );
       new AlertDialog.Builder( this )
               .setView( spinner )
               .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener(){
                  @Override
                  public void onClick( DialogInterface dialog, int which ){
                     String jogador = spinner.getSelectedItem().toString();
                     for(Jogador player : listPlayers){
                        if(player.getNomeJogador().equals(jogador)){
                           m_nome.setText(player.getNomeJogador());
                           m_telefone.setText(String.valueOf(player.getTelefoneJogador()));
                           m_email.setText(player.getEmailJogador());
                        }
                     }
                  }
               } ).show();
    }

   @Override
   public void onBackPressed()
   {
      Intent it = new Intent( this, GrupoInicioActivity.class );
      it.putExtra( "envioIdGrupo", m_retornoIdGrupo );
      startActivity( it );
      super.onBackPressed();
   }
}
