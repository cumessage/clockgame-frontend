package com.prosper.clockgame.frontend.ui;

import com.prosper.clockgame.frontend.util.RunDataReciever;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

public class SensorService extends Service {

	private static final String LOG_TAG = "SensorService";

	private RunDetector runDetector;
	private RunDataReciever runDataReciever;
	private Handler clientHandler;
	private final IBinder mBinder = new LocalBinder();

	public class LocalBinder extends Binder {
		public void gimmeHandler(Handler handler) {
			Log.d(LOG_TAG, "client handler bind");
			clientHandler = handler;
			RunDataReciever.getInstance().setHandler(clientHandler);
		}
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		Log.v(LOG_TAG, "bind");
		return mBinder;
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
		runDetector = new RunDetector(this);
		RunDataReciever.init();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		Log.v(LOG_TAG, "onStart");
		start();
		return START_STICKY;
	}

	private void start() {
		runDetector.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.v(LOG_TAG, "onDestroy");
		runDetector.stop();
	}

}
