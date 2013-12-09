package com.prosper.clockgame.frontend.ui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Environment;
import android.util.Log;

public class FootFallDetector implements SensorEventListener {

	private static final String LOG_TAG = "FootFallDetector";

	private static final float FC_EARTH_GRAVITY_DETECTION = 0.25F;
	private static final float FC_FOOT_FALL_DETECTION = 3.5F;
	private static final long SECOND_TO_NANOSECOND = (long) 1e9;
	private static final int ACCELERATION_VALUE_KEEP_SECONDS = 10;


	private float accelerometer[] = new float[3];
	private float gyroscope[] = new float[3];
	
	private final Context context;
	private SensorManager sensorManager;
	private boolean active = false;

	public FootFallDetector(Context context) {
		this.context = context;
	}

	public void start() {
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor gyroscope = sensorManager.getDefaultSensor(Sensor.TYPE_GYROSCOPE);
		Sensor linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, gyroscope, SensorManager.SENSOR_DELAY_FASTEST);
//		sensorManager.registerListener(this, linearAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
//		sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_FASTEST);
//		sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_FASTEST);
		active = true;
		
	}

	public void stop() {
		active = false;
		sensorManager.unregisterListener(this);
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER
				&& event.sensor.getType() != Sensor.TYPE_GYROSCOPE) {
			//				&& event.sensor.getType() != Sensor.TYPE_LINEAR_ACCELEROMETER
			//				&& event.sensor.getType() != Sensor.TYPE_GRAVITY) {
			return;
		}

		String fileName = "";
		String log = "";
		if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
			fileName = "accelerometer.log";
			log = Long.toString(event.timestamp) + "\t" 
					+ Float.toString(event.values[0]) + "\t" + Float.toString(event.values[1]) + "\t" 
					+ Float.toString(event.values[2]) + "\t" + Float.toString(getSum(event.values[0], event.values[1], event.values[2])) + "\n"; 
		}

		if (event.sensor.getType() == Sensor.TYPE_GYROSCOPE) {
			fileName = "gyroscope.log";
			log = Long.toString(event.timestamp) + "\t" 
					+ Float.toString(event.values[0]) + "\t" + Float.toString(event.values[1]) + "\t" 
					+ Float.toString(event.values[2]) + "\n"; 
		}

		//		Log.d(LOG_TAG, Arrays.toString(event.values) + " | " + total);
		//		Log.d(LOG_TAG, Integer.toString(event.sensor.getType()) + " : " + Arrays.toString(accelerometer));

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

		//		Acceleration acceleration = new Acceleration();
		//		acceleration.timestamp = event.timestamp;

		//		Acceleration prevValue = values.isEmpty() ? null : values.getFirst();
		//		if (prevValue == null) {
		//			for (int i = 0; i < 3; i++) {
		//				acceleration.averagedValues[i] = event.values[i];
		//				acceleration.lowPassFilteredValues[i] = event.values[i];
		//			}
		//		} else {
		//			lowPassFilter(acceleration.averagedValues, event.values, event.timestamp, prevValue.averagedValues,
		//					prevValue.timestamp, FC_EARTH_GRAVITY_DETECTION);
		//			lowPassFilter(acceleration.lowPassFilteredValues, event.values, event.timestamp,
		//					prevValue.lowPassFilteredValues, prevValue.timestamp, FC_FOOT_FALL_DETECTION);
		//		}
		//		values.addFirst(acceleration);
		//		removeValuesOlderThan(event.timestamp - ACCELERATION_VALUE_KEEP_SECONDS * SECOND_TO_NANOSECOND);
	}

	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	public int getCurrentCadence() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public float getSum(float x, float y, float z) {
		return x*x + y*y + z*z;
	}

	private void lowPassFilter(float[] result, float[] currentValue, long currentTime, float[] prevValue,
			long prevTime, float cutoffFequency) {
		long deltaTime = currentTime - prevTime;
		float alpha = (float) (cutoffFequency * 3.14 * 2 * deltaTime / SECOND_TO_NANOSECOND);
		if (alpha > 1) {
			alpha = 1;
		}
		for (int i = 0; i < 3; i++) {
			result[i] = prevValue[i] + alpha * (currentValue[i] - prevValue[i]);
		}
	}

	//	private void removeValuesOlderThan(long timestamp) {
	//		while (!values.isEmpty()) {
	//			if (values.getLast().timestamp < timestamp) {
	//				values.removeLast();
	//			} else {
	//				return;
	//			}
	//		}
	//	}


}
