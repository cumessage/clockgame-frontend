package com.prosper.clockgame.frontend.ui;

import java.io.InputStream;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;

import com.actionbarsherlock.app.SherlockActivity;
import com.prosper.clockgame.frontend.R;
import com.prosper.clockgame.frontend.bean.Game;
import com.prosper.clockgame.frontend.bean.GameResource;

public class BootActivity extends SherlockActivity {

	@Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_boot);
        
        InputStream gamesInputStream = getResources().openRawResource(R.raw.active_games);
        JSONArray jsonArray = null;
        try {
			jsonArray = new JSONArray(gamesInputStream.toString());
			
			for(int i = 0; i < jsonArray.length(); i ++) {
	        	String gameFile = jsonArray.get(i).toString();
	        	InputStream gameInputStream = getResources().openRawResource(R.raw.rungame);
	        	JSONObject jsonObject = new JSONObject(gameInputStream.toString());
	        	
	        	Game game = new Game(jsonObject);
	        	GameResource.getInstance().put(game.getId(), game);
	        }
		} catch (JSONException e) {
			e.printStackTrace();
		}
    }
}
