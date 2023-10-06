package br.com.fgomes.cgd.utils;

import java.io.File;

import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Environment;
import br.com.fgomes.cgd.R;

/**
 * Classe responsavel por conter o procedimento de exportacao de arquivos.
 * 
 * @author Leonardo Soares
 * 
 */
public class ProcessExportManager
{
   /** Nome do pacote main do servico */
   public static final String PATH_PACKAGE_SVC = "br.com.fgomes.cgd";

   /** Nome do banco de dados da aplicacao */
   public static final String DATABASE_NAME = "BDCGD";

   /** Caminho para o SD Card. */
   private static final String SD_CARD_PATH = Environment.getExternalStorageDirectory().getPath() + "/";

   /**
    * Metodo responsavel por fazer a exportacao dos arquivos de dados.
    * @return <b>true</b> caso a operacao tenha sido bem sucedida, <b>false</b> caso contrario.
    */
   public boolean makeDataExports( Context p_context ){
      boolean makeDataExportsSucess = false;
      /** Variavel que busca e define o estado do Slot SdCard */
      String SD_CARD_STATE = Environment.getExternalStorageState();
      /** Comparativo entre o estado do SdCard e a condicao de esta montado. */
      if ( !SD_CARD_STATE.equals( android.os.Environment.MEDIA_MOUNTED ) ){
         /** Estados possiveis e mais comuns a serem tratados do SdCard para a nao gravacao de exportacao de logs. */
         if ( SD_CARD_STATE.equals( android.os.Environment.MEDIA_NOFS ) ){
            SD_CARD_STATE = "Arquivos nao exportados, Cartao de memoria em branco ou nao particionado!";
         }
         else
            if ( SD_CARD_STATE.equals( android.os.Environment.MEDIA_MOUNTED_READ_ONLY ) ){
               SD_CARD_STATE = "Arquivos nao exportados, Cartao de memoria somente leitura, nao possivel gerar Logs.";
            }
            else
               if ( SD_CARD_STATE.equals( android.os.Environment.MEDIA_REMOVED ) ){
                  SD_CARD_STATE = "Arquivos nao exportados, sem cartao de memoria!";
               }
               else
                  if ( SD_CARD_STATE.equals( android.os.Environment.MEDIA_SHARED ) ){
                     SD_CARD_STATE = "Arquivos nao exportados, Cartao de memoria com recurso compartilhamento ativado!";
                  }
                  else
                     if ( SD_CARD_STATE.equals( android.os.Environment.MEDIA_BAD_REMOVAL ) ){
                        SD_CARD_STATE = "Arquivos nao exportados, Cartao de memoria removido com o dispositivo ligado!";
                     }
                     else{
                        SD_CARD_STATE = "Arquivos nao exportados, Erro desconhecido!";
                     }
         /*
          * Mensagem de arquivos n�o exportados com sucesso
          */
         AlertDialog alertDialog = new AlertDialog.Builder( p_context )
                  .setIcon( android.R.drawable.ic_dialog_alert )
                  .setTitle( R.string.app_name ).setMessage( SD_CARD_STATE )
                  .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener(){
                     @Override
                     public void onClick( DialogInterface dialog, int which )
                     {
                        dialog.dismiss();
                     }
                  } ).show();
      }
      else{
         ProcessDataRecovery dataRecovery = new ProcessDataRecovery();

         /*
          * Adicionar banco de dados do serviço.
          */
         try{
            //
            dataRecovery.addFileToBeExported( new File( new ContextWrapper( p_context )
                     .createPackageContext( PATH_PACKAGE_SVC, Context.CONTEXT_IGNORE_SECURITY )
                     .getDatabasePath( DATABASE_NAME ).getAbsolutePath() ) );
         }
         catch ( NameNotFoundException e1 ){
            e1.printStackTrace();
         }
         makeDataExportsSucess = dataRecovery.exportFilesToSdCard();
         if ( !makeDataExportsSucess ){
            /*
             * Mensagem de arquivos não exportados com sucesso
             */
            AlertDialog alertDialog = new AlertDialog.Builder( p_context )
                     .setIcon( android.R.drawable.ic_dialog_alert )
                     .setTitle( R.string.app_name )
                     .setMessage( "Logs não exportados, erro desconhecido no Cartão de memória." )
                     .setPositiveButton( android.R.string.ok, new DialogInterface.OnClickListener(){
                        @Override
                        public void onClick( DialogInterface dialog, int which ){
                           dialog.dismiss();
                        }
                     } ).show();
         }
      }
      return makeDataExportsSucess;
   }
}
