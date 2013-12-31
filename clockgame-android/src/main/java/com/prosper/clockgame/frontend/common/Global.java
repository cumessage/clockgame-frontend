package com.prosper.clockgame.frontend.common;

import com.prosper.clockgame.frontend.bean.GameResource;

import android.app.Application;

public class Global extends Application {

	private int userId;
	
	private long gameId;
	
	private GameResource gameResource;
	
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

	public GameResource getGameResource() {
		return gameResource;
	}

	public void setGameResource(GameResource gameResource) {
		this.gameResource = gameResource;
	}
	
	
	
}
