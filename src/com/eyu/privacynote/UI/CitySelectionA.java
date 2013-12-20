package com.eyu.privacynote.UI;

import java.util.ArrayList;
import java.util.List;
import com.eyu.privacynote.Constants;
import com.eyu.privacynote.R;
import com.eyu.privacynote.DAO.DBManager;
import com.eyu.privacynote.DAO.WeatherDBHelper;
import com.eyu.privacynote.R.string;
import com.eyu.privacynote.Util.SharePreferenceUtil;
import com.umeng.analytics.MobclickAgent;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;

public class CitySelectionA extends Activity {

	private Button btn_title_left, btn_title_right;
	private TextView txt_title_name, txt_title_left, txt_title_right;
	private Spinner province; // 省份spinner
	private Spinner city; // 城市spinner

	private List<String> proset = new ArrayList<String>();// 省份集合
	private List<String> citset = new ArrayList<String>();// 城市集合

	private String pro_id;

	WeatherDBHelper helper;
	private SharePreferenceUtil util;
	int province_pos,city_pos;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cityselection);
		util = new SharePreferenceUtil(this, Constants.SAVE_USER);
		initViews();
		initSpinner();
		handleEvents();
		

	}

	private void initViews() {
		btn_title_left = (Button) findViewById(R.id.btn_title_left);
		btn_title_right = (Button) findViewById(R.id.btn_title_right);

		txt_title_left = (TextView) findViewById(R.id.txt_title_left);
		txt_title_right = (TextView) findViewById(R.id.txt_title_right);
		txt_title_name = (TextView) findViewById(R.id.txt_title_name);
	}

	private void handleEvents() {
		txt_title_name.setText(string.titlebar_txt_title_city);
		txt_title_left.setVisibility(View.INVISIBLE);
		btn_title_right.setVisibility(View.GONE);
		btn_title_left.setVisibility(View.GONE);
		txt_title_right.setOnClickListener(new OnClickEvent());
	}

	class OnClickEvent implements OnClickListener {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.txt_title_right:
				intent.setClass(CitySelectionA.this, SettingsA.class);
				util.setProvincePos(province_pos);
				util.setCityPos(city_pos);
				startActivity(intent);
				CitySelectionA.this.finish();
				break;
			}
		}
	}

	private void initSpinner() {
		helper = new WeatherDBHelper(getApplicationContext());
		DBManager manager = new DBManager(getApplicationContext());
		manager.copyDatabase();

		province = (Spinner) findViewById(R.id.provinces);
		province.setSelection( util.getProvincePos(),true);
		ArrayAdapter<String> pro_adapter = new ArrayAdapter<String>(
				CitySelectionA.this, android.R.layout.simple_spinner_item,
				getProSet());
		pro_adapter.setDropDownViewResource(R.layout.spinner_item);
		
		province.setAdapter(pro_adapter);
		province.setSelection( util.getProvincePos());
		
		province.setOnItemSelectedListener(new SelectProvince());

		city = (Spinner) findViewById(R.id.city);
		city.setOnItemSelectedListener(new SelectCity());
	}

	// 选择改变状态
	class SelectProvince implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			// 获得省份ID
			if (position < 9) {
				pro_id = "0" + (position + 1) + "";
			} else {
				pro_id = (position + 1) + "";
			}
			
			province_pos = position;
			city.setAdapter(getAdapter());
			city.setSelection(util.getCityPos());

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}

	// 城市 选择改变状态
	class SelectCity implements OnItemSelectedListener {
		public void onItemSelected(AdapterView<?> parent, View view,
				int position, long id) {
			// TODO Auto-generated method stub
			// 选择提示
			city_pos = position;
			util.setWeatherCode(getCityNum(position)+"");

		}

		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}
	}

	/**
	 * 返回 省份集合
	 */
	public List<String> getProSet() {
		// 打开数据库
		String sql = "select * from province_table";
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String pro = cursor.getString(cursor
					.getColumnIndexOrThrow("PROVINCE"));
			proset.add(pro);
		}
		cursor.close();
		helper.close();
		return proset;
	}

	/**
	 * 返回 城市集合
	 */
	public List<String> getCitSet(String pro_id) {
		// 清空城市集合
		citset.clear();
		// 打开数据库
		String sql = "select * from city_table where PROVINCE_ID = " + "'"
				+ pro_id + "'" + ";";
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql, null);
		while (cursor.moveToNext()) {
			String pro = cursor.getString(cursor.getColumnIndexOrThrow("CITY"));
			citset.add(pro);
		}
		cursor.close();
		helper.close();
		return citset;
	}

	/**
	 * 返回适配器
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ArrayAdapter<String> getAdapter() {
		ArrayAdapter<String> city_adapter = new ArrayAdapter(
				CitySelectionA.this, android.R.layout.simple_spinner_item,
				getCitSet(pro_id));
		city_adapter.setDropDownViewResource(R.layout.spinner_item);
		return city_adapter;
	}

	/**
	 * 返回城市编号 以便调用天气预报api
	 */
	public long getCityNum(int position) {

		String sql = "select * from city_table where PROVINCE_ID = " + "'"
				+ pro_id + "'" + ";";
		Cursor cursor = helper.getReadableDatabase().rawQuery(sql, null);
		cursor.moveToPosition(position);
		long citynum = Long.parseLong(cursor.getString(cursor
				.getColumnIndexOrThrow("WEATHER_ID")));
		cursor.close();
		helper.close();
		return citynum;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		Intent intent = new Intent(getApplicationContext(), SettingsA.class);
		startActivity(intent);
		CitySelectionA.this.finish();
		return super.onKeyDown(keyCode, event);
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
