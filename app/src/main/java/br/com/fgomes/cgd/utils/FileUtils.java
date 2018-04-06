package br.com.fgomes.cgd.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.channels.FileChannel;

import android.os.Environment;

/**
 * Classe responsavel por fornecer operacoes com arquivos.
 * 
 */
public class FileUtils
{
   public boolean m_status = false;

   /**
    * Realiza a cópia de um arquivo.
    * 
    * @param p_sourceFile Arquivo de origem. Caso esse arquivo não exista, retornará <b>false</b>.
    * @param p_destFile Arquivo de destino. Caso o diretório do arquivo de destino não exista,
    * será criado. Caso não tenha êxito nessa criação, retornará <b>false</b>.
    * @return Retorna <b>true</b> caso a operação tenha sido bem sucedida, e <b>false</b> caso contrário.
    */
   public static boolean copyFile( File p_sourceFile, File p_destFile )
   {
      FileInputStream fIn = null;
      FileOutputStream fOut = null;
      FileChannel source = null;
      FileChannel destination = null;
      long transfered;
      long bytes;

      try
      {
         if ( !p_sourceFile.exists() )
            return false;

         if ( !p_destFile.getParentFile().exists() )
         {
            // "Path: " + outFile.getAbsolutePath() + " does not exist, creating it."
            if ( !p_destFile.getParentFile().mkdirs() )
            {
               // "ERROR: wasn't possible to create folders for dest path!";
               return false;
            }
         }

         if ( !p_destFile.exists() )
         {
            p_destFile.createNewFile();
            p_destFile.setWritable( true, false );
         }

         fIn = new FileInputStream( p_sourceFile );
         source = fIn.getChannel();
         fOut = new FileOutputStream( p_destFile );
         destination = fOut.getChannel();
         transfered = 0;
         bytes = source.size();
         while ( transfered < bytes )
         {
            transfered += destination.transferFrom( source, 0, source.size() );
            destination.position( transfered );
         }
      }
      catch ( IOException ex )
      {
         return false;
      }
      finally
      {
         try
         {
            if ( source != null )
            {
               source.close();
            }
            else
               if ( fIn != null )
               {
                  fIn.close();
               }
            if ( destination != null )
            {
               destination.close();
            }
            else
               if ( fOut != null )
               {
                  fOut.close();
               }
         }
         catch ( IOException ex )
         {
            return false;
         }
      }

      return true;
   }

   /**
    * Realiza a cópia de um arquivo.
    * 
    * @param p_sourceFile Arquivo de origem.
    * @param p_destFile Arquivo de destino.
    * @return Retorna <b>true</b> caso a operação tenha sido bem sucedida, e <b>false</b> caso contrário.
    */
   public static boolean copyFile( InputStream p_sourceFile, OutputStream p_destFile )
   {
      boolean ret = false;
      try
      {
         byte[] buffer = new byte[ 4096 ];
         int length;
         while ( ( length = p_sourceFile.read( buffer ) ) > 0 )
         {
            p_destFile.write( buffer, 0, length );
         }
         p_destFile.flush();
         p_destFile.close();
         p_sourceFile.close();
         ret = true;
      }
      catch ( Exception e )
      {
         ret = false;
      }
      return ret;
   }

   public boolean GetDataBase()
   {
      String path = "/data/data/br.com.fgomes.cgd/databases/";

      String file = "BDCGD";
      boolean getDatabase = false;
      File arquivo = new File( path + file );

      if ( Environment.getExternalStorageState().equals( Environment.MEDIA_MOUNTED ) || ( arquivo.exists() ) )
      {
         return getDatabase;
      }

      return getDatabase;

   }

   public boolean getDirFromSDCard()
   {
      boolean createDirectory = false;
      if ( Environment.getExternalStorageState().equals(
               Environment.MEDIA_MOUNTED ) )
      {
         File sdcard = Environment.getExternalStorageDirectory()
                  .getAbsoluteFile();
         File dir = new File( sdcard, "CGD" + File.separator + "Backup" );
         if ( !dir.exists() )
         {
            createDirectory = dir.mkdirs();
            return createDirectory;
         }
      }
      return createDirectory;

   }
}
