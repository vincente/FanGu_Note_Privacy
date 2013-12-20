package com.eyu.privacynote.WelcomeUI;

import java.io.IOException;
import org.apache.http.HttpResponse;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.R;
import com.eyu.privacynote.DAO.DBManager;
import com.eyu.privacynote.DAO.WeatherDBHelper;
import com.eyu.privacynote.UI.LoginA;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class WelcomeA extends Activity {
	boolean isFirstInstall;
	int lastVersion, currentVersion;
	SharePreferenceUtil util;
	PackageInfo info;
	WeatherDBHelper helper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.welcome);

		helper = new WeatherDBHelper(getApplicationContext());
		DBManager manager = new DBManager(getApplicationContext());
		manager.copyDatabase();

		util = new SharePreferenceUtil(getApplicationContext(),
				Constants.SAVE_USER);
		lastVersion = util.getVersion();

		try {
			info = getPackageManager().getPackageInfo(this.getPackageName(), 0);
			currentVersion = info.versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		new Thread(new Runnable() {

			@Override
			public void run() {
				if(isNetworkAvailable()){
					String weatherUrl = "http://www.weather.com.cn/data/cityinfo/"
							+ util.getWeatherCode() + ".html";
					String weatherJson = queryStringForGet(weatherUrl);

					try {
						JSONObject jsonObject = new JSONObject(weatherJson);

						JSONObject weatherObject = jsonObject
								.getJSONObject("weatherinfo");
						Message message = new Message();
						message.obj = weatherObject;
						handler.sendMessage(message);

					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				// TODO Auto-generated method stub
				
			}
		}).start();

		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				if (lastVersion < currentVersion) {
					Intent intent = new Intent(WelcomeA.this,
							WhatsnewPagesA.class);
					startActivity(intent);
					WelcomeA.this.finish();
					// util.setVension(currentVersion);
				} else {
					Intent intent = new Intent(WelcomeA.this, LoginA.class);
					startActivity(intent);
					WelcomeA.this.finish();
				}
			}
		}, 2000);
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			JSONObject object = (JSONObject) msg.obj;
			try {
				util.setCityName(object.getString("city"));
				util.setTemp(object.getString("temp2") + "/"
						+ object.getString("temp1"));
				util.setTempDetail(object.getString("weather"));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};

	private String queryStringForGet(String url) {
		HttpGet request = new HttpGet(url);

		String result = null;

		try {
			HttpResponse response = new DefaultHttpClient().execute(request);
			if (response.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);
				return result;
			}
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	/**
	 * 判断手机网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	private boolean isNetworkAvailable() {
		ConnectivityManager mgr = (ConnectivityManager) getApplicationContext()
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo[] info = mgr.getAllNetworkInfo();
		if (info != null) {
			for (int i = 0; i < info.length; i++) {
				if (info[i].getState() == NetworkInfo.State.CONNECTED) {
					return true;
				}
			}
		}
		return false;
	}
	

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		MobclickAgent.onResume(this);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		MobclickAgent.onPause(this);
	}

}
