package com.prosper.clockgame.frontend.common;

import android.app.Application;

public class Global extends Application {

	private int userId;
	
	@Override
	public void onCreate() {
		userId = -1;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
}
