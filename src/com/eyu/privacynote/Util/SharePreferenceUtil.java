package com.eyu.privacynote.Util;

import com.eyu.privacynote.R;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {
	private SharedPreferences sp;
	private SharedPreferences.Editor editor;

	@SuppressWarnings("static-access")
	public SharePreferenceUtil(Context context, String file) {
		sp = context.getSharedPreferences(file, context.MODE_PRIVATE);
		editor = sp.edit();
	}

	// 当前版本
	public void setVension(int version) {
		editor.putInt("version", version);
		editor.commit();
	}

	public int getVersion() {
		return sp.getInt("version", 0);
	}

	// 用户密码
	public void setUserPwd(String userpwd) {
		editor.putString("userpwd", userpwd);
		editor.commit();
	}

	public String getUserPwd() {
		return sp.getString("userpwd", "0000");
	}

	// tips提示
	public void setTips(boolean isShow) {
		editor.putBoolean("tips", isShow);
		editor.commit();
	}

	public boolean getTips() {
		return sp.getBoolean("tips", true);
	}

	// 密保问题
	public void setQuestion(String question) {
		editor.putString("question", question);
		editor.commit();
	}

	public String getQuestion() {
		return sp.getString("question", null);
	}

	// 密保答案
	public void setAnswer(String answer) {
		editor.putString("answer", answer);
		editor.commit();
	}

	public String getAnswer() {
		return sp.getString("answer", "");
	}

	// menu头部的图片资源id
	public void setMenuTitlePic(int picid) {
		editor.putInt("picid", picid);
		editor.commit();
	}

	public int getMenuTitlePic() {
		return sp.getInt("picid", R.drawable.menu_title_pic01);
	}

	// 天气代码
	public void setWeatherCode(String weathercode) {
		editor.putString("weathercode", weathercode);
		editor.commit();
	}

	public String getWeatherCode() {
		return sp.getString("weathercode", "101280601");
	}

	// 省份spinner位置
	public void setProvincePos(int provincepos) {
		editor.putInt("provincepos", provincepos);
		editor.commit();
	}

	public int getProvincePos() {
		return sp.getInt("provincepos", 0);
	}

	// 城市spinner位置
	public void setCityPos(int citypos) {
		editor.putInt("citypos", citypos);
		editor.commit();
	}

	public int getCityPos() {
		return sp.getInt("citypos", 0);
	}
	
	
	// 城市名称
	public void setCityName(String cityname) {
		editor.putString("cityname", cityname);
		editor.commit();
	}

	public String getCityName() {
		return sp.getString("cityname", "深圳");
	}
	
	
	// 温度
	public void setTemp(String temp) {
		editor.putString("temp", temp);
		editor.commit();
	}

	public String getTemp() {
		return sp.getString("temp", "27℃/30℃");
	}
	
	
	// 天气详情
	public void setTempDetail(String tempdetail) {
		editor.putString("tempdetail", tempdetail);
		editor.commit();
	}

	public String getTempDetail() {
		return sp.getString("tempdetail", "多云");
	}

}
