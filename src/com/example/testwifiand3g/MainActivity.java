package com.example.testwifiand3g;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class MainActivity extends ActionBarActivity {

	private int TYPE_MOBILE = 0;
	private int TYPE_WIFI = 1;
	private boolean falg = true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}
	
	public void test(View v){
		//开启数据网络
		setPreferredNetwork(TYPE_MOBILE);
		Log.i("GZ", "start mobile");
	}
	
	public void test2(View v){
		//开启wifi网络
		setPreferredNetwork(TYPE_WIFI);
		Log.i("GZ", "start wifi");
	}
	
	 public void setPreferredNetwork(int networkType) {
		 // 切换网络状态 wifi & GPRS
		  ConnectivityManager connMgr = (ConnectivityManager) MainActivity.this.getSystemService("connectivity");
		  WifiManager wifiMgr = (WifiManager) MainActivity.this.getSystemService("wifi");
		  if (networkType == TYPE_MOBILE) {
			  State gprs = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
			  //开启GPRS网络
			  if(gprs != State.CONNECTED && gprs != State.CONNECTING)
				  setMobileNetEnable();
			  //设为2G/3G网络优先
			  connMgr.setNetworkPreference(ConnectivityManager.TYPE_MOBILE);
			//如果wifi开启，关闭wifi
			  if(wifiMgr.isWifiEnabled())
				 wifiMgr.setWifiEnabled(false);
		  } else if (networkType == TYPE_WIFI) {
			  //开启wifi
			  wifiMgr.setWifiEnabled(true);
			  //设置wifi联网优先
			  connMgr.setNetworkPreference(ConnectivityManager.TYPE_WIFI);
		  }
	}
	
	 /**
	  * 开启系统设置里的移动网络
	  */
	 public final void setMobileNetEnable(){
	  ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	  Object[] arg = null;
	  try {
	   boolean isMobileDataEnable = invokeMethod("getMobileDataEnabled", arg);
	   if(!isMobileDataEnable){
	    invokeBooleanArgMethod("setMobileDataEnabled", true);
	   }
	  } catch (Exception e) {
	   // TODO Auto-generated catch block
	   e.printStackTrace();
	  }
	 }

	 

	public boolean invokeMethod(String methodName,
	            Object[]  arg) throws Exception {
	     
	     ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	        Class ownerClass = mConnectivityManager.getClass();

	        Class[]  argsClass = null;
	        if (arg != null) {
	            argsClass = new Class[1];
	            argsClass[0] = arg.getClass();
	        }

	        Method method = ownerClass.getMethod(methodName, argsClass);
	       
	        Boolean isOpen = (Boolean) method.invoke(mConnectivityManager, arg);

	        return isOpen;
	    }
	    
	     public Object invokeBooleanArgMethod(String methodName,
	                boolean value) throws Exception {
	     
	      ConnectivityManager mConnectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

	            Class ownerClass = mConnectivityManager.getClass();

	            Class[]  argsClass = new Class[1];
	                argsClass[0] = boolean.class;

	            Method method = ownerClass.getMethod(methodName,argsClass);

	            return method.invoke(mConnectivityManager, value);
	        }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
