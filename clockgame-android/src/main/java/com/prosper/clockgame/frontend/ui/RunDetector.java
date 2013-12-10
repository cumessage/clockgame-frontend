package com.prosper.clockgame.frontend.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import com.prosper.clockgame.frontend.common.RunDataReciever;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.util.Log;

public class RunDetector implements SensorEventListener {

	private static final String LOG_TAG = "RunDetector";
	
	private static final int BUFFER_TIME = 1000000000;
	private static final int BUFFER_SIZE = 1000;

	private boolean active = false;
	private Buffer buffer;
	
	
	private static class Buffer {
		private float[] dataBuffer;
		private int size;
		private int maxSize;
		private long[] timeBuffer;

		Buffer(int maxSize) {
			this.maxSize = maxSize;
			reset();
		}
		
		void reset() {
			dataBuffer = new float[maxSize];
			timeBuffer = new long[maxSize / 2];
			this.size = 0;
		}
		
		boolean isWriteable() {
			return size < maxSize;
		}
		
		void put(float x, float y, long time) {
			dataBuffer[size] = x;
			dataBuffer[size + 1] = y;
			timeBuffer[size / 2] = time;
			size = size + 2;
		}
	}
	
	private long lastUpdate = -1;

	private final Context context;
	private SensorManager sensorManager;

	public RunDetector(Context context) {
		this.context = context;
	}

	public void start() {
		buffer = new Buffer(BUFFER_SIZE);
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
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			return;
		}
		
		if (lastUpdate == -1 || (event.timestamp - lastUpdate) < BUFFER_TIME) {
			if (buffer.isWriteable()) {
				buffer.put(event.values[0], event.values[1], event.timestamp);
				return;
			}
		}
		
		RunDataReciever.put(buffer.dataBuffer, buffer.timeBuffer, buffer.size);
		lastUpdate = event.timestamp;
		
		buffer.reset();
		buffer.put(event.values[0], event.values[1], event.timestamp);
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
	
	private float getSum(float x, float y, float z) {
		return x*x + y*y + z*z;
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

}
