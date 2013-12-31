package com.prosper.clockgame.frontend.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;

import com.actionbarsherlock.app.SherlockListFragment;
import com.fasterxml.jackson.databind.JsonNode;
import com.prosper.clockgame.frontend.R; 
import com.prosper.clockgame.frontend.common.DefaultHandler;
import com.prosper.clockgame.frontend.common.DefaultResponse;
import com.prosper.clockgame.frontend.common.Global;
import com.prosper.clockgame.frontend.restful.UserRestClient;

public class JoinableGameListFragment extends SherlockListFragment {
    private View fragmentView;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	Global global = (Global) getActivity().getApplication();
		long userId = global.getUserId();
		fragmentView = inflater.inflate(R.layout.blank_layout, container, false);
		try {
			UserRestClient.getUserGameListJoinable(userId, new DefaultHandler() {
				@Override
				public void doMessage (DefaultResponse response) {
					updateUI(response);
				}
			});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		if (fragmentView == null) {
			return null;
		}
		return fragmentView;
	}

	private void updateUI(DefaultResponse response) {
//		SimpleAdapter adapter = new SimpleAdapter(
//				getActivity(), getData(response), R.layout.vlist,
//				new String[]{"title","info","img"},
//				new int[]{R.id.title, R.id.info, R.id.img}
//				);
		GameListAdapter adapter = new GameListAdapter(getActivity(), getData(response), 1);
		setListAdapter(adapter);
	}
	
	private List<Map<String, Object>> getData(DefaultResponse response) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		JsonNode jsonNode = response.getJson();
		JsonNode gameList = jsonNode.get("gameList");
		
		for (final JsonNode game: gameList) {
			SimpleDateFormat sdf= new SimpleDateFormat("MM-dd HH:mm");
			String time = sdf.format(game.get("playTime").asLong());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", game.get("id").asLong());
			map.put("playTime", game.get("playTime").asLong());
			map.put("title", time);
			map.put("info", "创建者: " + game.get("creatorName").asText());
			map.put("img", R.drawable.ic_launcher);
			list.add(map);
		}
		return list;
	}

}
