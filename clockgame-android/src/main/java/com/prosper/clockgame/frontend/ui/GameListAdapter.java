package com.prosper.clockgame.frontend.ui;

import java.util.List;
import java.util.Map;

import com.prosper.clockgame.frontend.R;
import com.prosper.clockgame.frontend.common.DefaultHandler;
import com.prosper.clockgame.frontend.common.DefaultResponse;
import com.prosper.clockgame.frontend.common.Global;
import com.prosper.clockgame.frontend.restful.GameRestClient;

import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class GameListAdapter extends BaseAdapter {
	
	private static final int JOINED_GAME_LIST = 0;
	private static final int JOINABLE_GAME_LIST = 1;
	
	private LayoutInflater mInflater;
	
	private Context context;
	
	private List<Map<String, Object>> mData;
	
	private int type;
	
	public GameListAdapter(Context context, List<Map<String, Object>> mData, int type){
		this.context = context;
		this.mInflater = LayoutInflater.from(context);
		this.mData = mData;
		this.type = type;
	}

	public int getCount() {
		return mData.size();
	}

	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			holder = new ViewHolder();  
			convertView = mInflater.inflate(R.layout.vlist, null);
			holder.img = (ImageView)convertView.findViewById(R.id.img);
			holder.title = (TextView)convertView.findViewById(R.id.title);
			holder.info = (TextView)convertView.findViewById(R.id.info);
			holder.viewBtn = (Button)convertView.findViewById(R.id.button_game_info);
			holder.enterBtn = (Button)convertView.findViewById(R.id.button_enter_game);
			convertView.setTag(holder);
		}else {
			holder = (ViewHolder)convertView.getTag();
		}
		
		holder.img.setBackgroundResource((Integer)mData.get(position).get("img"));
		holder.title.setText((String)mData.get(position).get("title"));
		holder.info.setText((String)mData.get(position).get("info"));
		
		final long gameId = (Long) mData.get(position).get("id");
		final long playTime = (Long) mData.get(position).get("playTime");
		
		if (type == JOINED_GAME_LIST) {
			holder.viewBtn.setText(R.string.button_game_info);
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					showGameInfo(gameId);	
				}
			});
			holder.enterBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					enterGame(gameId);	
				}
			});
		} else if (type == JOINABLE_GAME_LIST) {
			holder.viewBtn.setText(R.string.button_game_join);
			holder.viewBtn.setOnClickListener(new View.OnClickListener() {
				public void onClick(View v) {
					joinGame(gameId, playTime);	
				}
			});
			holder.enterBtn.setVisibility(Button.INVISIBLE);
		}
		return convertView;
	}
	
	public void showGameInfo(long gameId) {
		final Intent intent = new Intent(context, GameInfoActivity.class);
		Global global = (Global) context.getApplicationContext();
		global.setGameId(gameId);
		context.startActivity(intent);
	}
	
	public void enterGame(long gameId) {
		final Intent intent = new Intent(context, GameActivity.class);
		intent.putExtra("gameId", gameId);
		context.startActivity(intent);
	}
	
	public void joinGame(final long gameId, final long playTime) {
		Global global = (Global) context.getApplicationContext();
		System.out.println("get in");
		try {
			GameRestClient.join(gameId, global.getUserId(), new DefaultHandler() {
				@Override
				public void doMessage (DefaultResponse response) {
					setAlarm(gameId, playTime);
					
					AlertDialog.Builder builder = new Builder(context); 
					builder.setPositiveButton("确定",null); 
					builder.setIcon(android.R.drawable.ic_dialog_info); 
					if (response.getOpCode() == 0) {
						builder.setMessage("加入成功"); 
					} else if(response.getOpCode() == 102) {
						builder.setMessage("数据已存在"); 
					} else {
						builder.setMessage("加入失败");
					}
					builder.show();
			    }  
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void setAlarm(long gameId, long time) {
		AlarmManager am = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent("android.alarm.demo.action");
		intent.putExtra("id", gameId);
        PendingIntent sender = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        am.set(AlarmManager.RTC_WAKEUP, time, sender);
//        am.cancel(sender);
	}
	
	public final class ViewHolder{
		public ImageView img;
		public TextView title;
		public TextView info;
		public Button viewBtn;
		public Button enterBtn;
	}

}
