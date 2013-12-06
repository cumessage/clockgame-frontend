package com.prosper.clockgame.frontend.ui;

import java.util.Arrays;
import java.util.LinkedList;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

public class FootFallDetector implements SensorEventListener {

	private static final String LOG_TAG = "FootFallDetector";

	private static final float FC_EARTH_GRAVITY_DETECTION = 0.25F;
	private static final float FC_FOOT_FALL_DETECTION = 3.5F;
	private static final long SECOND_TO_NANOSECOND = (long) 1e9;
	private static final int ACCELERATION_VALUE_KEEP_SECONDS = 10;
	
	
	private float geomagnetic[] = new float[3];
	private float accelerometer[] = new float[3];

	private final Context context;
	private SensorManager sensorManager;
	private boolean active = false;
	private final LinkedList<Acceleration> values = new LinkedList<Acceleration>();

	public FootFallDetector(Context context) {
		this.context = context;
	}

	public void start() {
		sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
		Sensor accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		Sensor linearAccelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
		Sensor gravity = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY);
		Sensor magnetic = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, linearAccelerometer, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, gravity, SensorManager.SENSOR_DELAY_FASTEST);
		sensorManager.registerListener(this, magnetic, SensorManager.SENSOR_DELAY_FASTEST);
		active = true;
	}

	public void stop() {
		active = false;
		sensorManager.unregisterListener(this);
	}

	public void onSensorChanged(SensorEvent event) {
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER
				&& event.sensor.getType() != Sensor.TYPE_MAGNETIC_FIELD) {
//				&& event.sensor.getType() != Sensor.TYPE_LINEAR_ACCELEROMETER
//				&& event.sensor.getType() != Sensor.TYPE_GRAVITY) {
			return;
		}
		
		if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
			accelerometer[0] = event.values[0];
			accelerometer[1] = event.values[1];
			accelerometer[2] = event.values[2];
		}
		
		if (event.sensor.getType() != Sensor.TYPE_MAGNETIC_FIELD) {
			geomagnetic[0] = event.values[0];
			geomagnetic[1] = event.values[1];
			geomagnetic[2] = event.values[2];
		}
		
		float[] rotationMatrix = new float[9];
		float[] rotation = new float[3];
		SensorManager.getRotationMatrix(rotationMatrix, null, accelerometer, geomagnetic);
		SensorManager.getOrientation(rotationMatrix, rotation);  
		
		rotation[0] = (float) Math.toDegrees(rotation[0]);  
        rotation[1] = (float) Math.toDegrees(rotation[1]);  
        rotation[2] = (float) Math.toDegrees(rotation[2]);  
		
		//		Log.d(LOG_TAG, Arrays.toString(event.values) + " | " + total);
		Log.d(LOG_TAG, Integer.toString(event.sensor.getType()) + " : " + Arrays.toString(accelerometer));
		Log.d(LOG_TAG, Integer.toString(event.sensor.getType()) + " : " + Arrays.toString(rotation));

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

	private void removeValuesOlderThan(long timestamp) {
		while (!values.isEmpty()) {
			if (values.getLast().timestamp < timestamp) {
				values.removeLast();
			} else {
				return;
			}
		}
	}

	private class Acceleration {
		public long timestamp;
		public float[] lowPassFilteredValues = new float[3];
		public float[] averagedValues = new float[3];

		@Override
		public String toString() {
			return String.format("Time,average,filtered,:,%d,%.2f,%.2f,%.2f,%.2f,%.2f,%.2f", timestamp,
					averagedValues[0], averagedValues[1], averagedValues[2], lowPassFilteredValues[0],
					lowPassFilteredValues[1], lowPassFilteredValues[2]);
		}
	}





}
