package com.prosper.clockgame.frontend.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.prosper.clockgame.frontend.util.RunDataReciever;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.util.Log;

public class RunDetector implements SensorEventListener {

	private static final String LOG_TAG = "RunDetector";

	private boolean active = false;

	private final Context context;
	private SensorManager sensorManager;

	public RunDetector(Context context) {
		this.context = context;
	}

	public void start() {
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		active = true;
	}

	public void stop() {
		active = false;
		sensorManager.unregisterListener(this);
	}

	public void onSensorChanged(SensorEvent event) {
//		printToScream(event);
//		logToFile(event);
		writeToBuffer(event);
	}
	
	public void writeToBuffer(SensorEvent event) {
		RunDataReciever.getInstance().put(event.values[0], event.values[1], event.timestamp);
	}
	
	public void logToFile(SensorEvent event) {
		String fileName = "";
		String log = "";
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			fileName = "accelerometer.log";
			log = Long.toString(event.timestamp) + "\t" 
					+ Float.toString(event.values[0]) + "\t" + Float.toString(event.values[1]) + "\t" 
					+ Float.toString(event.values[2]) + "\t" 
					+ Float.toString(getSum(event.values[0], event.values[1], event.values[2])) + "\n"; 
			Log.d(LOG_TAG, Integer.toString(event.sensor.getType()) + " : " + Arrays.toString(event.values));
		}

		if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			fileName = "gyroscope.log";
			log = Long.toString(event.timestamp) + "\t" 
					+ Float.toString(event.values[0]) + "\t" + Float.toString(event.values[1]) + "\t" 
					+ Float.toString(event.values[2]) + "\n"; 
		}


		if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
			File sdCardDir = Environment.getExternalStorageDirectory();
			File saveFile = new File(sdCardDir, fileName);
			FileOutputStream outStream;
			try {
				outStream = new FileOutputStream(saveFile, true);
				outStream.write(log.getBytes());
				outStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			throw new RuntimeException("sd is not mounted");
		}
	}
	
	public void printToScream(SensorEvent event) {
		Log.d(LOG_TAG, Integer.toString(event.sensor.getType()) + " : " + Arrays.toString(event.values));
	}
	
	private float getSum(float x, float y, float z) {
		return x*x + y*y + z*z;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
