package com.prosper.clockgame.frontend.ui;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class SensorService extends Service {

	private static final String LOG_TAG = "SensorService";

	private FootFallDetector footFallDetector;
	private Handler clientHandler;
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public void gimmeHandler(Handler handler) {
			clientHandler = handler;
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		Log.v(LOG_TAG, "bind");
		return null;
	}

	@Override
	public boolean onUnbind(Intent intent) {
		Log.v(LOG_TAG, "onUnbind'd");
		return true;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.v(LOG_TAG, "onCreate");
		footFallDetector = new FootFallDetector(this);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.v(LOG_TAG, "onStart");
		start();
		return START_STICKY;
	}

	private void start() {
		footFallDetector.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(LOG_TAG, "onDestroy");
		footFallDetector.stop();
	}

}
