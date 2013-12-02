package com.prosper.clockgame.frontend.ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.app.ActionBar;  
import com.actionbarsherlock.view.Menu;
import com.fasterxml.jackson.databind.JsonNode;
import com.prosper.clockgame.frontend.R;
import com.prosper.clockgame.frontend.common.DefaultHandler;
import com.prosper.clockgame.frontend.common.DefaultResponse;
import com.prosper.clockgame.frontend.common.Global;
import com.prosper.clockgame.frontend.restful.UserRestClient;

import android.app.Activity;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class GameListActivity extends SherlockFragmentActivity {
	
    private ActionBar actionBar;  
    private ViewPager viewPager;
	
    private void initActionBar() {
        actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        actionBar.setDisplayShowTitleEnabled(false);  
        actionBar.setDisplayShowHomeEnabled(false);  
    }
    
    private void initViewPager() {
        viewPager = new ViewPager(this);
        viewPager.setId(R.id.pager);
        setContentView(viewPager);
    }
    
    private void addTabs() {
        MyTabListener myTabListener = new MyTabListener(this, viewPager);

        ActionBar.Tab firstTab = actionBar.newTab();
        firstTab.setText("First");
        myTabListener.addTab(firstTab, FirstFragment.class, null);
        firstTab.setTabListener(myTabListener);

        ActionBar.Tab secondTab = actionBar.newTab();
        secondTab.setText("Second");
        myTabListener.addTab(secondTab, SecondFragment.class, null);
        secondTab.setTabListener(myTabListener);
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		initViewPager();
		addTabs();
		
//		Global global = (Global) getApplication();
//		long userId = global.getUserId();
//		
//		try {
//			UserRestClient.getUserGameListJoined(userId, new DefaultHandler() {
//				@Override
//				public void doMessage (DefaultResponse response) {
////					updateUI(response);
//				}
//			});
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}

		// Create the text view
//		TextView textView = new TextView(this);
//		textView.setTextSize(40);
//		textView.setText("正在加载 id:" + Long.toString(userId) + " game 列表");

		// Set the text view as the activity layout
//		setContentView(textView);
	}
	
//	private void updateUI(DefaultResponse response) {
//		SimpleAdapter adapter = new SimpleAdapter(
//				this, getData(response), R.layout.vlist,
//				new String[]{"title","info","img"},
//				new int[]{R.id.title, R.id.info, R.id.img}
//			);
//		setListAdapter(adapter);
//	}

}
