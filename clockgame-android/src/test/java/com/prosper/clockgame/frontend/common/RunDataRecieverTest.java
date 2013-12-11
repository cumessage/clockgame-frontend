package com.prosper.clockgame.frontend.common;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import com.prosper.clockgame.frontend.util.RunDataReciever;

public class RunDataRecieverTest {

	public static void main(String[] args) throws IOException {
		File file = new File("D:\\workspace\\others\\doc\\real\\accelerometer.log");
		BufferedReader acceReader = new BufferedReader(new FileReader(file));

		String line = "";
		while ((line = acceReader.readLine()) != null) {
			String[] values = line.split("\t");
			long currentTime = Long.parseLong(values[0]);
			float xValue = Float.parseFloat(values[1]);
			float yValue = Float.parseFloat(values[2]);
			float zValue = Float.parseFloat(values[3]);
			RunDataReciever.getInstance().put(xValue, yValue, currentTime);
		}
		acceReader.close();
	}
}

