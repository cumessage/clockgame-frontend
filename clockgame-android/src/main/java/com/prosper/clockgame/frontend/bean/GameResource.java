package com.prosper.clockgame.frontend.bean;

import android.util.SparseArray;

public class GameResource {
	
	private SparseArray<Game> gameMap;
	
	private static GameResource gameResource = new GameResource();
	
	public static GameResource getInstance() {
		return gameResource; 
	}
	
	private GameResource() {
		gameMap = new SparseArray<Game>();
	}
	
	public void put(Integer id, Game game) {
		gameMap.put(id, game);
	}
	
	public Game get(Integer id) {
		return gameMap.get(id);
	}

}
