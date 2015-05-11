package com.example.testwifiand3g;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class AutoSMS extends BroadcastReceiver {

	// �㲥��Ϣ����
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context content, Intent intent) {
		// ���ն��Ź㲥����������
		
		//���жϹ㲥������
		if (SMS_RECEIVED_ACTION.equals(intent.getAction())) {
			//��ȡ����
			Bundle bundle = intent.getExtras();
			if (bundle!= null) {
				//ȡpdus����,ת��ΪObject[]
				Object[] pdus = (Object[]) bundle.get("pdus");
				  //��������
                SmsMessage[] message = new SmsMessage[pdus.length];
                for (int i = 0; i < message.length; i++) {
					byte[] pdu = (byte[])pdus[i];
					message[i] = SmsMessage.createFromPdu(pdu);
				}
                //���������ݺ�����������
                for (SmsMessage msg : message) {
					//��ȡ��������
                	String contentStr = msg.getMessageBody();
                	String senderStr = msg.getOriginatingAddress();
                	//�����ж�Ҫ��ȡ�ĵ绰����
                	if("100800".equals(senderStr)){
                		//��ӡ
                		Log.d("GZ", "�յ����ص��������ݣ�"+contentStr);
                		//��ֹ�㲥
                		abortBroadcast(); 
                	}else {
						Log.d("GZ", "�������ն������ݣ�"+contentStr);
					}
				}
			
			}
			
			//�жϹ��˺���
//			Stirng phone = intent.getb
			//��ȡ��������
			
		}

	}

}
