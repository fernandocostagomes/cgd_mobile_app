package br.com.fgomes.cgd.activities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;
import br.com.fgomes.cgd.R;
import br.com.fgomes.cgd.objects.Grupo;
import br.com.fgomes.cgd.utils.DatabaseBackupManager;
import br.com.fgomes.cgd.utils.DbHelper;
import br.com.fgomes.cgd.utils.FileUtils;
import br.com.fgomes.cgd.utils.ProcessBackupManager;

public class MainActivity extends Activity
{
   /** Nome do Banco de Dados. */
   public static final String DATABASE_NAME = "BDCGD";
   /** Gerenciador de backup do Banco de Dados. */
   private DatabaseBackupManager m_databaseBackupManager;

   private void login( String p_user, String p_pass ){
      DbHelper dbHelper = new DbHelper( this );

      Grupo group = dbHelper.selectUmGrupo( p_user );

      String user_db = group.getNomeGrupo();
      String pwd_db = group.getSenhaGrupo();

      if ( ( p_user.equals( user_db ) ) && ( p_pass.equals( pwd_db ) ) ){
         int idGrupoRetorno = group.getIdGrupo();

         Bundle params = new Bundle();
         params.putInt( "envioIdGrupo", idGrupoRetorno );

         Intent intent = new Intent( MainActivity.this, GrupoInicioActivity.class );
         intent.putExtra( "envioIdGrupo", idGrupoRetorno );

         // Criando parametro no banco com valor de idGrupo
         dbHelper.alterarParametroGrupo( idGrupoRetorno );

         startActivity( intent );
         finish();

         saveUserData(p_user, p_pass);
      }
      else{
         Toast.makeText( getApplicationContext(), "Usuario ou Senha invalido!",
                 Toast.LENGTH_SHORT ).show();
      }
   }

   private void saveUserData(String p_user, String p_pass) {
      // Create a object SharedPreferences from getSharedPreferences("name_file",MODE_PRIVATE) of Context
      SharedPreferences pref;
      pref = getSharedPreferences( "info", MODE_PRIVATE );
      // Using putXXX - with XXX is type data you want to write like: putString, putInt... from Editor object
      Editor editor = pref.edit();
      editor.putString( "login", p_user);
      editor.putString( "pass", p_pass);
      // finally, when you are done saving the values, call the commit() method.
      editor.commit();
      // ........................................................................
   }

   private String checkLoginSave(){
      String result = "";
      SharedPreferences shared = getSharedPreferences( "info", MODE_PRIVATE );
      String string_temp_user = shared.getString( "login", "vazio" );
      String string_temp_pass = shared.getString( "pass", "vazio" );
      if ( !string_temp_user.equals( "vazio" ) )
         login( string_temp_user, string_temp_pass );
      else
         result = string_temp_user;
      return result;
   }

   /**
    * Verifica se o Banco de Dados está no lugar correto.
    * Caso não esteja, pega o backup caso exista ou copia um BD limpo
    * do Resources.raw e cola na pasta de banco de dados.
    * 
    * @return True, se foi executado com sucesso, ou se o arquivo já existe na paste.
    * Falso, caso tenha dado algum erro no processo de cópia do arquivo para a pasta.
    */
   private boolean checkDatabaseFile(){
      File databaseFile = this.getApplicationContext().getDatabasePath( DATABASE_NAME );
      File parentDatabaseFile;
      InputStream is;
      OutputStream os;

      boolean ret = false;
      try{
         if ( !( databaseFile.exists() ) ){
            parentDatabaseFile = new File( databaseFile.getParent() );
            if ( !parentDatabaseFile.exists() ){
               parentDatabaseFile.mkdirs();
            }
            // Verifica se é possível recuperar um backup do BD existente.
            if ( m_databaseBackupManager.hasToRecoverDatabaseBackupFile() ){
               ret = m_databaseBackupManager.recoverDatabaseBackupFile();
            }
            else{
               is = getResources().openRawResource( R.raw.bdcgd );
               os = new FileOutputStream( databaseFile );
               ret = FileUtils.copyFile( is, os );
            }
         }
      }
      catch ( Exception e ){
         return false;
      }
      return ret;
   }

   @Override
   protected void onCreate( Bundle icicle ){
      super.onCreate( icicle );
      setContentView( R.layout.activity_main );

      boolean teste = true;
      if(teste){
         m_databaseBackupManager = new DatabaseBackupManager( getApplicationContext() );
         checkDatabaseFile();
         //installShortCut();
         checkLoginSave();
      }
   }

   @Override
   public boolean onCreateOptionsMenu( Menu p_menu ){
      // Inflate the menu; this adds items to the action bar if it is present.
      getMenuInflater().inflate( R.menu.main, p_menu );
      return true;
   }

   @Override
   public boolean onOptionsItemSelected( MenuItem p_item ){
      int id = p_item.getItemId();
      switch ( id ){
         case R.id.action_backup:
            ProcessBackupManager backupManager = new ProcessBackupManager( this );
            if ( backupManager.makeDatabaseBackup() ){
               AlertDialog alertDialog = new AlertDialog.Builder( MainActivity.this )
                  .setIcon( android.R.drawable.ic_dialog_alert )
                  .setTitle( R.string.app_name ).setMessage( R.string.info_successBackup )
                  .setPositiveButton( android.R.string.ok, (dialog, which) -> dialog.dismiss()).show();
            }
            else{
               AlertDialog alertDialog = new AlertDialog.Builder( MainActivity.this )
                  .setIcon( android.R.drawable.ic_dialog_alert )
                  .setTitle( R.string.app_name ).setMessage( R.string.info_notBackup )
                  .setPositiveButton( android.R.string.ok, (dialog, which) -> dialog.dismiss()).show();
               alertDialog.getWindow().setType( WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG );
            }

         break;

         case R.id.action_out:
               finish();
         break;
      }
      return super.onOptionsItemSelected( p_item );
   }

   // Botao Cadastrar Grupo
   public void evtBtCadastrarGrupo( View p_view ){
      startActivity( new Intent( getApplicationContext(), CadastroGrupoActivity.class ) );
      finish();
   }

   public void evtBtEntrar( View p_view ){
      login( ( ( EditText ) findViewById(R.id.edGrupo)).getText().toString(),
              ( ( EditText )findViewById( R.id.edSenha )).getText().toString());
   }

   // Botao Esqueci
   public void btEsqueciSenhaGrupo( View p_view ){
      startActivity( new Intent( MainActivity.this, EsqueciSenhaGrupoActivity.class ) );
      finish();
   }
}
