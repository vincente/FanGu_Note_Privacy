package com.eyu.privacynote;


public class Constants {
	/** 日志输出的tag标志 *//*
	public static final String TAG = "FANGU";
	*//** 文件的保存路径 *//*
	public static final String SAVEPATH = Environment
			.getExternalStorageDirectory() + "/FanGu_Reading/";*/

	public static final String SAVE_USER = "saveUser";// 保存用户信息的xml文件名

	public static String getDocName(String string) {
		StringBuilder sb = new StringBuilder(string);
		String docName = sb.append(".mp3").toString();
		return docName;

	}

	public static String timeFormat(long millis) {
		millis /= 1000;
		long min = millis / 60;
		long sec = millis % 60;
		if (sec >= 10) {
			return min + ":" + sec;
		} else {
			return min + ":0" + sec;
		}
	}

}
