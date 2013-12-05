package com.prosper.clockgame.frontend.ui;

import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.actionbarsherlock.app.ActionBar;  
import com.prosper.clockgame.frontend.R;

import android.os.Bundle;
import android.support.v4.view.ViewPager;

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
		TabListener myTabListener = new TabListener(this, viewPager);

		ActionBar.Tab firstTab = actionBar.newTab();
		firstTab.setText(R.string.game_list_tab1);
		myTabListener.addTab(firstTab, JoinedGameListFragment.class, null);
		firstTab.setTabListener(myTabListener);

		ActionBar.Tab secondTab = actionBar.newTab();
		secondTab.setText(R.string.game_list_tab2);
		myTabListener.addTab(secondTab, JoinableGameListFragment.class, null);
		secondTab.setTabListener(myTabListener);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		initActionBar();
		initViewPager();
		addTabs();
	}

}
