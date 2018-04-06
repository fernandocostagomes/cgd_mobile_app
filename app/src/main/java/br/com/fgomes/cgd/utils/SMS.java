package br.com.fgomes.cgd.utils;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.telephony.gsm.SmsManager;
import android.util.Log;

@SuppressWarnings("deprecation")
public class SMS {
	
	public void enviarSms(Context context, String destino, String mensagem){
		
		try{

			SmsManager smsM = SmsManager.getDefault();
			PendingIntent pIntent = PendingIntent.getBroadcast(context, 0, new Intent(), 0);
			smsM.sendTextMessage(destino, null, mensagem, pIntent, null);			
		}	catch (Exception e){
			Log.e("", "Erro ao enviar o SMS: " + e.getMessage(), e);
		}		
	}

}
