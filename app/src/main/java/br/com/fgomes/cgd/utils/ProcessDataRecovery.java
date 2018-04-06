package br.com.fgomes.cgd.utils;

import java.io.File;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;

import android.os.Environment;

/**
 * Classe respons�vel por recuperar os dados do aplicativo (Ex: Banco de dados; Log).
 * 
 * @author Rodrigo.Gurgel / Flavio.Aguiar
 *
 */
public class ProcessDataRecovery
{

   /** Nome da pasta de destino, que ficar� localizada dentro do cart�o de mem�ria. */
   private String m_destinationFolderName = "cgd_recovery/";

   /** Lista de arquivos a serem exportados para uma pasta no cart�o de mem�ria. */
   private List<File> m_filesToBeExported = new ArrayList<File>();

   /**
    * M�todo respons�vel por exportar os arquivos, contidos na lista de arquivos m_filesToBeExported (consulte o m�todo
    * <b>addFileToBeExported(File)</b> )
    * Caso o cart�o de mem�ria N�O esteja montado e com permiss�o de 'leitura e escrita', os arquivos n�o ser�o
    * exportados.
    */
   public boolean exportFilesToSdCard()
   {
      boolean ret = false;
      if ( android.os.Environment.getExternalStorageState().equals( android.os.Environment.MEDIA_MOUNTED ) )
      {
         String destPath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator
                  + m_destinationFolderName;

         File destFile;
         File sourceFile;

         for ( int i = 0; i < m_filesToBeExported.size(); i++ )
         {
            sourceFile = m_filesToBeExported.get( i );
            if ( sourceFile.exists() )
            {
               destFile = new File( destPath + sourceFile.getName() );
               ret = FileUtils.copyFile( sourceFile, destFile );
            }
         }
      }

      return ret;

   }

   /**
    * Retorna o nome da pasta de destino a serem copiados os arquivos.
    * Esta pastar� estar� contida na pasta do cart�o de mem�ria (sdcard/)
    * 
    * @return Retorna o nome da pasta de destino a serem copiados os arquivos.
    */
   public String getDestinationFolderName()
   {
      return this.m_destinationFolderName;
   }

   /**
    * Especifica o nome da pasta de destino a serem copiados os arquivos.
    * Esta pastar� estar� contida na pasta do cart�o de mem�ria (sdcard/)
    * 
    * @param p_destinationFolderName Nome da pasta de destino dos arquivos a serem copiados.
    * N�o pode ser um valor nulo e nem vazio.
    */
   public void setDestinationFolderName( String p_destinationFolderName )
   {
      if ( p_destinationFolderName != null && p_destinationFolderName != "" )
         this.m_destinationFolderName = p_destinationFolderName;
   }

   /**
    * Adiciona um novo arquivo na lista de arquivos a serem exportados.
    * 
    * @param p_fileToBeExported Arquivo a ser adicionado na lista de arquivos a serem exportados.
    * Por exemplo: <b>/data/data/br.com.autotrac.jatmobilecommsvc/databases/jATMobileComm.sqlite</b>
    * 
    * @throws InvalidParameterException se o arquivo n�o existir.
    */
   public void addFileToBeExported( File p_fileToBeExported )
   {
      // Se o arquico n�o for encontrado desconsiderar
      if ( !p_fileToBeExported.exists() )
         return;
      // throw new InvalidParameterException( "The file can not be exported, it does not exist." );

      m_filesToBeExported.add( p_fileToBeExported );
   }

   /**
    * Remove o arquivo da lista de arquivos a serem exportados.
    * 
    * @param p_fileToBeExportedIndex � o �ndice do arquivo a ser removido da lista de arquivos a serem exportados.
    * */
   public void removeFileToBeExported( int p_fileToBeExportedIndex )
   {
      m_filesToBeExported.remove( p_fileToBeExportedIndex );
   }

   /**
    * Retorna o n�mero de elementos contido na lista de arquivos a serem exportados.
    * 
    * @return Retorna o n�mero de elementos contido na lista de arquivos a serem exportados.
    * */
   public int getFilesToBeExportedCount()
   {
      return m_filesToBeExported.size();
   }

   /**
    * Retorna o arquivo contido no �ndice especificado, dentro da lista de arquivos a serem exportados.
    * 
    * @param p_fileToBeExportedIndex � o �ndice do arquivo dentro da lista de arquivos a serem exportados.
    * @return Retorna o arquivo dentro da lista de arquivos a serem exportados.
    * */
   public File getFileToBeExported( int p_fileToBeExportedIndex )
   {
      return m_filesToBeExported.get( p_fileToBeExportedIndex );
   }

   /**
    * Retorna a lista de arquivos a serem exportados.
    * 
    * @return Retorna a lista de arquivos a serem exportados.
    * */
   public List<File> getFilesToBeExported()
   {
      return m_filesToBeExported;
   }
}
