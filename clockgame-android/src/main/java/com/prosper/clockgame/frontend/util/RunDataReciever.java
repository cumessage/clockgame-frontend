package com.prosper.clockgame.frontend.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class RunDataReciever {

	private static final String LOG_TAG = "RunDataReciever";

	private static final long BUFFER_TIME = 5000000000L;
	private static final int BUFFER_SIZE = 1000;
	private static final long SECOND_TO_NANOSECOND = (long) 1e9;

	private static RunDataReciever runDataReciever = new RunDataReciever();;

	private BlockingQueue<Buffer> computerQueue = new LinkedBlockingQueue<Buffer>();
	private BlockingQueue<Buffer> receiveQueue = new LinkedBlockingQueue<Buffer>();

	private Integer counter = 0;
	private Handler handler;

	private long lastConsume = -1;

	private class Buffer {
		private float[] dataBuffer;
		private int size;
		private int maxSize;
		private long[] timeBuffer;
		private int current;

		Buffer(int maxSize) {
			this.maxSize = maxSize;
			dataBuffer = new float[maxSize];
			timeBuffer = new long[maxSize];
			reset();
		}

		void reset() {
			this.size = 0;
			this.current = 0;
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

		public float[] getData() {
			return dataBuffer;
		}

		public long[] getTime() {
			return timeBuffer;
		}
		
		public int getSize() {
			return size;
		}

		String getNext() {
			if (current > size) {
				return null;
			}
			String s = timeBuffer[current / 2] + " " + dataBuffer[current] + " " + dataBuffer[current + 1] + "\n";
			current = current + 2;
			return s;
		}

		void resetGetNext() {
			current = 0;
		}

		public String toString() {
			return "get " + size;
		}

	}

	private class Computer implements Runnable {
		private static final float Y_MIDDLE = 5;
		private static final float Y_THRESHOLD_LOW = -5;
		private static final float Y_THRESHOLD_HIGH = 15;
		private static final long LOOP_TIME_THRESHHOLD_HIGH = 200000000000000L;
		private static final long LOOP_TIME_THRESHHOLD_LOW = 0;
		
		public void run() {
//			Log.d(LOG_TAG, "computer thread start");
			while(true) {
				Buffer buffer = null;
				try {
					buffer = computerQueue.take();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
//				Log.d(LOG_TAG, "consume: " + buffer.toString());
				int currentCount = count(buffer);
				
				buffer.reset();
				receiveQueue.add(buffer);

				counter = counter + currentCount;
				System.out.println("counter:" + counter);
				if (handler != null) {
//					Log.d(LOG_TAG, "Update count display.");
					Message message = Message.obtain();
					message.arg1 = counter;
					handler.sendMessage(message);
				}
			}
		}
		
		private int count(Buffer buffer) {
			int count = 0;
			
			int size = buffer.getSize() / 2;

			long[] time = buffer.getTime();
			float[] data = buffer.getData();
			float[] yData = new float[size];

			boolean start = true;
			long preTime = 0;
			float preYValue = 0; 
			
			float yCutoffFequency = 6f;
			
			for(int i = 0; i < buffer.getSize(); i = i + 2) {
//				if (start) {
//					preTime = time[i];
//					preYValue = data[i + 1];
//					start = false;
//				} else {
//					preYValue = lowPassFilter(data[i + 1], time[i], preYValue, preTime, yCutoffFequency); 
//					preTime = time[i];
//				}
//				
//				yData[i/2] = preYValue;
				yData[i/2] = data[i + 1];
			}
			
			long loopStartTime = -1;
			float yLastPeak = -1;
			boolean isSetYLastPeak = false;
			float yLastTrough = -1;
			boolean isSetYLastTrough = false;
			
			for(int i = 0; i < size; i++) {
				float yCurrent = yData[i];
				long currentTime = time[i];

				if (loopStartTime != -1 && ((currentTime - loopStartTime) > LOOP_TIME_THRESHHOLD_HIGH 
						|| (currentTime - loopStartTime) < LOOP_TIME_THRESHHOLD_LOW)) {
					loopStartTime = -1;
					yLastPeak = -1;
					isSetYLastPeak = false;
					yLastTrough = -1;
					isSetYLastTrough = false;
				}
				
				if (yCurrent > Y_THRESHOLD_HIGH) {
					if (yCurrent > yLastPeak) {
						yLastPeak = yCurrent;
						isSetYLastPeak = true;
					}
					
					if (loopStartTime == -1) {
						loopStartTime = currentTime;
					}
				} else if (yCurrent < Y_THRESHOLD_LOW){
					if (yCurrent < yLastTrough) {
						yLastTrough = yCurrent;
						isSetYLastTrough = true;
					}
					
					if (loopStartTime == -1) {
						loopStartTime = currentTime;
					}
				} else {
					if (isSetYLastPeak && isSetYLastTrough) {
						if (yLastPeak > Y_THRESHOLD_HIGH && yLastTrough < Y_THRESHOLD_LOW) {
							loopStartTime = -1;
							yLastPeak = -1;
							isSetYLastPeak = false;
							yLastTrough = -1;
							isSetYLastTrough = false;
							count ++;
						}
					}
					continue;
				}
			}
			return count;
		}
		
		private float lowPassFilter(float currentValue, long currentTime, 
				float prevValue, long prevTime, float cutoffFequency) {
			long deltaTime = currentTime - prevTime;
			float alpha = (float) (cutoffFequency * 3.14 * 2 * deltaTime / SECOND_TO_NANOSECOND);
			if (alpha > 1) {
				alpha = 1;
			}
			return prevValue + alpha * (currentValue - prevValue);
		}
	}
	
	public static void init() {
	}

	public static RunDataReciever getInstance() {
		if (runDataReciever == null) {
			throw new RuntimeException("RunDataReciever is not init");
		}
		return runDataReciever;
	}

	private RunDataReciever() {
		try {
			receiveQueue.put(new Buffer(BUFFER_SIZE));
			receiveQueue.put(new Buffer(BUFFER_SIZE));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		new Thread(new Computer()).start();
	}

	private Buffer getCurrrentBuffer(long time) {
		if (lastConsume == -1) {
			lastConsume = time;
		}

		Buffer buffer = receiveQueue.peek();
		if ((time - lastConsume) < BUFFER_TIME ) {
			if (buffer.isWriteable()) {
				return buffer;
			}
		}
		consumeBuffer(time);
		if (receiveQueue.size() > 0) {
			return receiveQueue.peek();
		} else {
			buffer = new Buffer(BUFFER_SIZE);
			receiveQueue.add(buffer);
			return buffer;
		}
	}

	public void put(float xdata, float yData, long time) {
		Buffer buffer = getCurrrentBuffer(time);
		buffer.put(xdata, yData, time);
	}

	public void consumeBuffer(long time) {
		Buffer buffer = null;
		try {
			buffer = receiveQueue.take();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
//		Log.d(LOG_TAG, buffer.toString());
		computerQueue.add(buffer);
		lastConsume = time; 
	}

	public void setHandler(Handler handler) {
		this.handler = handler;
	}

}
