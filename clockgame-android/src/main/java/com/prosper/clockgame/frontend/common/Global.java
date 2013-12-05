package com.prosper.clockgame.frontend.common;

import android.app.Application;

public class Global extends Application {

	private int userId;
	
	private long gameId;
	
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

	public long getGameId() {
		return gameId;
	}

	public void setGameId(long gameId) {
		this.gameId = gameId;
	}
	
	
	
}
