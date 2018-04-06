package br.com.fgomes.cgd.utils;

import java.io.File;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;

/**
 * Classe respons�vel por conter o procedimento de backup do Banco de Dados.
 * 
 * @author Leonardo Soares
 * 
 */
public class ProcessBackupManager
{
   /** Nome do banco de dados da aplica��o */
   public static final String DATABASE_NAME = "BDCGD";

   /** Nome do pacote main do servi�o */
   public static final String PATH_PACKAGE_SVC = "br.com.fgomes.cgd";

   /** Contexto da aplica��o. */
   private Context m_context;

   private final String m_backupFolderName = "BDCGD";

   /** banco de dados da aplica��o. */
   private File m_appDatabaseFile;

   /** banco de dados de backup. */
   private final File m_backupDatabaseFileInSdCard, m_backupDatabaseFileInLocalFiles;

   /**
    * Construtor.
    * 
    * @param p_context Contexto da aplica��o.
    */
   public ProcessBackupManager( Context p_context )
   {
      m_context = p_context;

      try
      {
         m_appDatabaseFile = new File( new ContextWrapper( p_context )
                  .createPackageContext( PATH_PACKAGE_SVC, Context.CONTEXT_IGNORE_SECURITY )
                  .getDatabasePath( DATABASE_NAME ).getAbsolutePath() );
      }
      catch ( NameNotFoundException e )
      {
         e.printStackTrace();
      }

      m_backupDatabaseFileInLocalFiles = new File( m_context.getFilesDir().getAbsolutePath() + File.separator
               + DATABASE_NAME );
      m_backupDatabaseFileInSdCard = new File(
               Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + m_backupFolderName
                        + File.separator + DATABASE_NAME );
   }

   /**
    * M�todo respons�vel por fazer o Backup do Banco de Dados.
    * 
    * @return <b>true</b> caso a opera��o tenha sido bem sucedida, <b>false</b> caso contr�rio.
    */
   public boolean makeDatabaseBackup()
   {
      boolean ret = false;

      try
      {
         if ( m_appDatabaseFile.exists() )
         {
            if ( Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED ) )
            {
               if ( !m_backupDatabaseFileInSdCard.getParentFile().exists() )
                  m_backupDatabaseFileInSdCard.getParentFile().mkdir();

               FileUtils.copyFile( m_appDatabaseFile, m_backupDatabaseFileInSdCard );
            }

            FileUtils.copyFile( m_appDatabaseFile, m_backupDatabaseFileInLocalFiles );

            ret = true;
         }
      }
      catch ( Exception e )
      {
         return false;
      }

      return ret;
   }
}
