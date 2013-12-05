package com.prosper.clockgame.frontend.ui;

import com.actionbarsherlock.app.SherlockActivity;
import com.prosper.clockgame.frontend.R;
import com.prosper.clockgame.frontend.common.DefaultHandler;
import com.prosper.clockgame.frontend.common.DefaultResponse;
import com.prosper.clockgame.frontend.restful.GameRestClient;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GameActivity extends SherlockActivity {
	
	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_game);
        
        Intent intent = getIntent();
		long gameId = intent.getLongExtra("gameId", -1);
		try {
			GameRestClient.getInfo(gameId, new DefaultHandler() {
				@Override
				public void doMessage (DefaultResponse response) {
					updateUI(response);
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
    private void updateUI(DefaultResponse response) {
		TextView gameIdView = (TextView) findViewById(R.id.game_id);
		gameIdView.setText(response.getJson().get("id").asText());
    	System.out.println("get result:" + response.getJson().toString());
	}
    

}
