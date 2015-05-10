package cn.com.Tool.Logger;

import android.util.Log;

public class Logger {
	private static final String TAG = "Logger";

	public static void i(String info) {
		Log.i(TAG, info);
	}

	public static void e(String info) {
		Log.e(TAG, info);
	}

	public static void w(String info) {
		Log.w(TAG, info);
	}

	public static void d(String info) {
		Log.d(TAG, info);
	}

}
