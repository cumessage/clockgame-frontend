package com.prosper.clockgame.frontend.common;

import android.util.Log;

public class RunDataReciever {

	private static final String LOG_TAG = "RunDetector";
	
	public static void put(float[] data, long[] time, int size) {
		for(int i = 0; i < size; i += 2) {
			Log.d(LOG_TAG, time[i / 2] + " " + data[i] + " " + data[i + 1]);
		}
	}
}
