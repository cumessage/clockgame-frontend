package com.prosper.clockgame.frontend.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
	
	public void onReceive(Context context, Intent intent) {
		System.out.println("got alarm!");
		if ("android.alarm.demo.action".equals(intent.getAction())) {
			long gameId = (Long) intent.getLongExtra("id", -1);

			Intent gameIntent = new Intent(context, GameActivity.class);
			gameIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			gameIntent.putExtra("gameId", gameId);
			context.startActivity(gameIntent);
		}
	}
}
