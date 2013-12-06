package com.prosper.clockgame.frontend.ui;

import com.actionbarsherlock.app.SherlockActivity;
import com.prosper.clockgame.frontend.R;
import com.prosper.clockgame.frontend.common.DefaultHandler;
import com.prosper.clockgame.frontend.common.DefaultResponse;
import com.prosper.clockgame.frontend.restful.GameRestClient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.widget.TextView;

public class GameActivity extends SherlockActivity {

	public final static String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
	
	private Intent serviceIntent;

	private final ServiceConnection serviceConnection = new ServiceConnection() {

		public void onServiceConnected(ComponentName name, IBinder binder) {
			((SensorService.LocalBinder) binder).gimmeHandler(updateCadenceDisplayHandler);
		}

		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub

		}
	};

	private final Handler updateCadenceDisplayHandler = new Handler() {
		@Override
		public void handleMessage(Message message) {
			int cadence = message.arg1;
//			if (cadence > 0) {
//				mCurrentCadence.setText(String.valueOf(cadence));
//			} else {
//				mCurrentCadence.setText(DISPLAY_NO_DATA);
//			}
		}
	};

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
		
		serviceIntent = new Intent(GameActivity.this, SensorService.class);
        startService(serviceIntent);
	}

	private void updateUI(DefaultResponse response) {
		TextView gameIdView = (TextView) findViewById(R.id.game_id);
		gameIdView.setText(response.getJson().get("id").asText());
		System.out.println("get result:" + response.getJson().toString());
	}
	
	@Override
    protected void onStop() {
            super.onStop();
            unbindService(serviceConnection);
    }

    @Override
    protected void onStart() {
            super.onStart();
            bindService(serviceIntent, serviceConnection, Context.BIND_AUTO_CREATE);
    }


}
