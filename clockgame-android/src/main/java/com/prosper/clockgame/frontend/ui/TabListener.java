package com.prosper.clockgame.frontend.ui;

import java.util.ArrayList;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.actionbarsherlock.app.ActionBar; 
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;

public class TabListener extends FragmentStatePagerAdapter implements ActionBar.TabListener, OnPageChangeListener {
    private final Context _context;
    private final ActionBar _actionBar;
    private final ViewPager _viewPager;
    private final ArrayList<TabInfo> _tabs = new ArrayList<TabInfo>();

    static final class TabInfo {
        private final Class<?> _class;
        private final Bundle _args;

        TabInfo(Class<?> clss, Bundle args) {
            _class = clss;
            _args = args;
        }
    }

    public TabListener(SherlockFragmentActivity activity, ViewPager pager) {
        super(activity.getSupportFragmentManager());
        _context = activity;
        _actionBar = activity.getSupportActionBar();
        _viewPager = pager;
        _viewPager.setAdapter(this);
        _viewPager.setOnPageChangeListener(this);
    }

    /**
     * Adds tabs to the ActionBar.
     *
     * @param tab  Tab which will added
     * @param clss Class which is connected with the tab
     * @param args Extra tab arguments
     */
    public void addTab(ActionBar.Tab tab, Class<?> clss, Bundle args) {
        TabInfo info = new TabInfo(clss, args);
        tab.setTag(info);
        tab.setTabListener(this);
        _tabs.add(info);
        _actionBar.addTab(tab);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return _tabs.size();
    }

    @Override
    public Fragment getItem(int position) {
        TabInfo info = _tabs.get(position);
        return Fragment.instantiate(_context, info._class.getName(), info._args);
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }
    
    public void onPageSelected(int position) { 
    	_actionBar.setSelectedNavigationItem(position);
    }

    public void onPageScrollStateChanged(int state) {
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        Object tag = tab.getTag();
        for (int i = 0; i < _tabs.size(); i++) {
            if (_tabs.get(i) == tag) {
                _viewPager.setCurrentItem(i);
            }
        }
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }
}
