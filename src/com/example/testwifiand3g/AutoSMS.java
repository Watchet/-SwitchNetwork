package com.example.testwifiand3g;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

public class AutoSMS extends BroadcastReceiver {

	// 广播消息类型
	public static final String SMS_RECEIVED_ACTION = "android.provider.Telephony.SMS_RECEIVED";

	@Override
	public void onReceive(Context content, Intent intent) {
		// 接收短信广播，进行拦截
		
		//先判断广播的类型
		if (SMS_RECEIVED_ACTION.equals(intent.getAction())) {
			//读取参数
			Bundle bundle = intent.getExtras();
			if (bundle!= null) {
				//取pdus内容,转换为Object[]
				Object[] pdus = (Object[]) bundle.get("pdus");
				  //解析短信
                SmsMessage[] message = new SmsMessage[pdus.length];
                for (int i = 0; i < message.length; i++) {
					byte[] pdu = (byte[])pdus[i];
					message[i] = SmsMessage.createFromPdu(pdu);
				}
                //解析完内容后分析具体参数
                for (SmsMessage msg : message) {
					//读取短信内容
                	String contentStr = msg.getMessageBody();
                	String senderStr = msg.getOriginatingAddress();
                	//根据判断要截取的电话号码
                	if("100800".equals(senderStr)){
                		//打印
                		Log.d("GZ", "收到拦截的内容内容："+contentStr);
                		//终止广播
                		abortBroadcast(); 
                	}else {
						Log.d("GZ", "正常接收短信内容："+contentStr);
					}
				}
			
			}
			
			//判断过滤号码
//			Stirng phone = intent.getb
			//读取短信内容
			
		}

	}

}
