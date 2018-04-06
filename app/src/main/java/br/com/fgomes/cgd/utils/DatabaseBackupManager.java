package br.com.fgomes.cgd.utils;

import java.io.File;

import android.content.Context;
import android.os.Environment;
import br.com.fgomes.cgd.activities.MainActivity;

/**
 * Classe respons�vel por conter o procedimento de backup/restaura��o do Banco de Dados.
 * 
 * @author leandro.nobrega
 *
 */
public class DatabaseBackupManager
{

   /** Contexto da aplica��o. */
   private Context m_context;

   private final String m_backupFolderName = "BDCGD";

   /** banco de dados da aplica��o. */
   private final File m_appDatabaseFile;

   /** banco de dados de backup. */
   private final File m_backupDatabaseFileInSdCard, m_backupDatabaseFileInLocalFiles;

   /**
    * Construtor.
    * 
    * @param p_context Contexto da aplica��o.
    */
   public DatabaseBackupManager( Context p_context )
   {
      m_context = p_context;
      m_appDatabaseFile = m_context.getDatabasePath( MainActivity.DATABASE_NAME );
      m_backupDatabaseFileInLocalFiles = new File( m_context.getFilesDir().getAbsolutePath() + File.separator
               + MainActivity.DATABASE_NAME );
      m_backupDatabaseFileInSdCard = new File( Environment.getExternalStorageDirectory().getAbsolutePath()
               + File.separator
               + m_backupFolderName + File.separator + MainActivity.DATABASE_NAME );
   }

   /**
    * M�todo respons�vel por informar se � necess�rio fazer a recupera��o do backup do BD.
    * 
    * @return Retorna <b>true</b> caso seja necess�rio fazer a recupera��o do Banco de Dados (backup). <b>false</b> caso
    * contr�rio.
    */
   public boolean hasToRecoverDatabaseBackupFile()
   {
      boolean ret = false;

      if ( !m_appDatabaseFile.exists() )
      {
         if ( m_backupDatabaseFileInLocalFiles.exists() || m_backupDatabaseFileInSdCard.exists() )
            ret = true;
      }
      return ret;

   }

   /**
    * M�todo respons�vel por recuperar o arquivo de Backup e inser�-lo na pasta de Banco de Dados da aplica��o.
    * 
    * @return <b>true</b> caso a opera��o tenha sido bem sucedida.
    */
   public boolean recoverDatabaseBackupFile()
   {
      boolean ret = false;
      if ( m_backupDatabaseFileInLocalFiles.exists() || m_backupDatabaseFileInSdCard.exists() )
      {
         if ( !m_appDatabaseFile.getParentFile().exists() )
         {
            m_appDatabaseFile.getParentFile().mkdir();
         }

         ret = FileUtils.copyFile( m_backupDatabaseFileInLocalFiles, m_appDatabaseFile );
         if ( !ret )
         {
            if ( Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED ) )
            {
               ret = FileUtils.copyFile( m_backupDatabaseFileInSdCard, m_appDatabaseFile );
            }
         }
      }
      return ret;
   }
}
